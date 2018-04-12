package com.dimframework.domain.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import org.jboss.logging.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.dimframework.domain.DeleteOperationMetadata;
import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.DimensionProcessLog;
import com.dimframework.domain.HashFileMetadata;
import com.dimframework.domain.UpdateOperationMetadata;
import com.dimframework.domain.enums.EDWReplicationStatus;
import com.dimframework.domain.pojo.PopulateHashBatchJobMetadata;
import com.dimframework.domain.service.BeanRegistryService;
import com.dimframework.domain.service.DeleteOperationService;
import com.dimframework.domain.service.DimensionMetadataConsumerService;
import com.dimframework.domain.service.DimensionMetadataService;
import com.dimframework.domain.service.DimensionProcessLogDaoService;

@Component("dimensionMetadataConsumerServiceImpl")
public class DimensionMetadataConsumerServiceImpl implements DimensionMetadataConsumerService {

	private static Logger logger = Logger.getLogger(DimensionMetadataConsumerServiceImpl.class);
	
	@Resource
	private BlockingQueue<DimensionMetadata> dimensionMetadataBlockingQueue;

	@Resource
	private BlockingQueue<HashFileMetadata> hashFileMetadataBlockingQueue;
	
	@Resource
	private BlockingQueue<DeleteOperationMetadata> deleteOperationMetadataBlockingQueue;

	@Autowired
	private DimensionMetadataService dimensionMetadataDaoServiceImpl;
	
	@Autowired
	private DimensionProcessLogDaoService DimensionProcessLogDaoServiceImpl;
	
	@Autowired
	private DeleteOperationService deleteOperationServiceImpl;
	
	@Autowired
	private BeanRegistryService beanRegistryServiceImpl;
	
	@Autowired
	@Qualifier("schemaName")
	private String schemaName;

	@Override
	public HashFileMetadata processDimensionMetadata() throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		DimensionMetadata dimensionMetadata = this.dimensionMetadataBlockingQueue.take();
		DimensionProcessLog runLog = new DimensionProcessLog(null, new Timestamp(new Date().getTime()), null, dimensionMetadata.getMetadataId(), EDWReplicationStatus.INIT.toString(), "N/A");
		DimensionProcessLog dimensionProcessLog = this.DimensionProcessLogDaoServiceImpl.create(runLog);
		logger.info("Created runID " +  dimensionProcessLog.getProcessId() + " for metadataId: " + dimensionProcessLog.getMetadataId());
		this.dimensionMetadataDaoServiceImpl.truncateTable(dimensionMetadata.getSourceTableHash());
		logger.info("After truncating table " + dimensionMetadata.getSourceTableHash());
		PopulateHashBatchJobMetadata populateHashBatchJobMetadata = this.dimensionMetadataDaoServiceImpl.generatePopulateHashBatchJobMetadata(dimensionMetadata, dimensionProcessLog);
		String beanName = this.beanRegistryServiceImpl.getPrototypePopulateHashBatchJobMetadata(populateHashBatchJobMetadata);
		
		logger.debug("Created bean name = " + beanName + " for  " + populateHashBatchJobMetadata.toString());
		Job job = this.beanRegistryServiceImpl.instantiatePopulateHashJob(beanName);
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString("runid", new Date().toGMTString());
		this.beanRegistryServiceImpl.run(job, jobParametersBuilder.toJobParameters());
		HashFileMetadata hashFileMetaData = new HashFileMetadata(dimensionMetadata, populateHashBatchJobMetadata.getFullyQualifiedFileName(), schemaName, dimensionProcessLog.getProcessId());
		hashFileMetadataBlockingQueue.offer(hashFileMetaData);
		return hashFileMetaData;
	}
	
	@Override
	public void processHashFileMetadata() throws InterruptedException, IOException {
		HashFileMetadata hm = hashFileMetadataBlockingQueue.take();
		this.dimensionMetadataDaoServiceImpl.executeLoadIntoHash(hm);
		logger.info("Loaded data successfully in table " + hm.getDimensionMetadata().getSourceTableHash());
		// remove files
		Path path = Paths.get(hm.getFileName());
		Files.delete(path);
		DeleteOperationMetadata d = dimensionMetadataDaoServiceImpl.generateDeleteOperationBatchJobMetadata(hm.getDimensionMetadata(), hm.getProcessId());
		this. deleteOperationMetadataBlockingQueue.offer(d);
		// update status of process record to Hash values POPULATED
	}

	@Override
	public void processDeleteOperation() throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		DeleteOperationMetadata deleteOperationMetadata = deleteOperationMetadataBlockingQueue.take();
		Job deleteJob = deleteOperationServiceImpl.instantiateDeleteOperationBatchJob(deleteOperationMetadata);
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addDate("effectiveEndDate", deleteOperationMetadata.getDimensionMetadata().getEffectiveEndDate());
		this.beanRegistryServiceImpl.run(deleteJob, jobParametersBuilder.toJobParameters());
		UpdateOperationMetadata u = dimensionMetadataDaoServiceImpl.generateUpdateOperationBatchJobMetadata(deleteOperationMetadata.getDimensionMetadata(), deleteOperationMetadata.getProcessId());
	}
	
	

}
