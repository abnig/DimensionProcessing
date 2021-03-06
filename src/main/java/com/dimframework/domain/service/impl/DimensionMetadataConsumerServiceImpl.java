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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.dimframework.domain.DeleteOperationMetadata;
import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.DimensionProcessLog;
import com.dimframework.domain.InsertOperationMetadata;
import com.dimframework.domain.UpdateOperationMetadata;
import com.dimframework.domain.enums.EDWReplicationStatus;
import com.dimframework.domain.pojo.PopulateHashBatchJobMetadata;
import com.dimframework.domain.service.BeanRegistryService;
import com.dimframework.domain.service.DeleteOperationService;
import com.dimframework.domain.service.DimensionMetadataConsumerService;
import com.dimframework.domain.service.DimensionMetadataService;
import com.dimframework.domain.service.DimensionProcessLogDaoService;
import com.dimframework.domain.service.InsertOperationService;
import com.dimframework.domain.service.UpdateOperationService;

@Component("dimensionMetadataConsumerServiceImpl")
public class DimensionMetadataConsumerServiceImpl implements DimensionMetadataConsumerService {

	private static Logger logger = Logger.getLogger(DimensionMetadataConsumerServiceImpl.class);
	
	@Resource
	private BlockingQueue<DimensionMetadata> dimensionMetadataBlockingQueue;

	@Resource
	private BlockingQueue<DeleteOperationMetadata> deleteOperationMetadataBlockingQueue;
	
	@Resource
	private BlockingQueue<InsertOperationMetadata> insertOperationMetadataBlockingQueue;
	
	@Resource
	private BlockingQueue<UpdateOperationMetadata> updateOperationMetadataBlockingQueue;

	@Autowired
	private DimensionMetadataService dimensionMetadataDaoServiceImpl;
	
	@Autowired
	private DimensionProcessLogDaoService DimensionProcessLogDaoServiceImpl;
	
	@Autowired
	private DeleteOperationService deleteOperationServiceImpl;
	
	@Autowired
	private InsertOperationService insertOperationServiceImpl;
	
	@Autowired
	private UpdateOperationService updateOperationServiceImpl;  
	
	@Autowired
	private BeanRegistryService beanRegistryServiceImpl;
	
	@Autowired
	@Qualifier("schemaName")
	private String schemaName;

	@Override
	public void processDimensionMetadata() throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
		DimensionMetadata dimensionMetadata = this.dimensionMetadataBlockingQueue.take();
		DimensionProcessLog runLog = new DimensionProcessLog(null, new Timestamp(new Date().getTime()), null, dimensionMetadata.getMetadataId(), EDWReplicationStatus.INIT.toString(), "N/A");
		DimensionProcessLog dimensionProcessLog = this.DimensionProcessLogDaoServiceImpl.create(runLog);
		logger.info("Created runID " +  dimensionProcessLog.getProcessId() + " for metadataId: " + dimensionProcessLog.getMetadataId());
		this.dimensionMetadataDaoServiceImpl.truncateTable(dimensionMetadata.getSourceTableHash());
		PopulateHashBatchJobMetadata populateHashBatchJobMetadata = this.dimensionMetadataDaoServiceImpl.generatePopulateHashBatchJobMetadata(dimensionMetadata, dimensionProcessLog);
		String beanName = this.beanRegistryServiceImpl.getPrototypePopulateHashBatchJobMetadata(populateHashBatchJobMetadata);
		
		Job job = this.beanRegistryServiceImpl.instantiatePopulateHashJob(beanName);
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addLong("runid", new Date().getTime());
		this.beanRegistryServiceImpl.run(job, jobParametersBuilder.toJobParameters());
		logger.debug("Loaded data successfully in table " + dimensionMetadata.getSourceTableHash());
		// remove files
		Path path = Paths.get(populateHashBatchJobMetadata.getFileName());
		if(Files.exists(path))
		  Files.delete(path);
		DeleteOperationMetadata d = dimensionMetadataDaoServiceImpl.generateDeleteOperationBatchJobMetadata(dimensionMetadata, populateHashBatchJobMetadata.getProcessId());
		this. deleteOperationMetadataBlockingQueue.offer(d);

	}

	@Override
	public void processDeleteOperation() throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		DeleteOperationMetadata deleteOperationMetadata = deleteOperationMetadataBlockingQueue.take();
		Job deleteJob = deleteOperationServiceImpl.instantiateDeleteOperationBatchJob(deleteOperationMetadata);
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addDate("effectiveEndDate", deleteOperationMetadata.getDimensionMetadata().getEffectiveEndDate());
		this.beanRegistryServiceImpl.run(deleteJob, jobParametersBuilder.toJobParameters());
	    UpdateOperationMetadata u = dimensionMetadataDaoServiceImpl.generateUpdateOperationBatchJobMetadata(deleteOperationMetadata.getDimensionMetadata(), deleteOperationMetadata.getProcessId());
		this.updateOperationMetadataBlockingQueue.offer(u);
	}
	
	@Override
	public void processUpdateOperation() throws BeansException, Exception {
		UpdateOperationMetadata updateOperationMetadata = this.updateOperationMetadataBlockingQueue.take();
		Job updateJob = updateOperationServiceImpl.instantiateUpdateOperationBatchJob(updateOperationMetadata);
		// create InsertOperationMetadata object
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addDate("effectiveStartDate", updateOperationMetadata.getDimensionMetadata().getEffectiveStartDate());
		this.beanRegistryServiceImpl.run(updateJob, jobParametersBuilder.toJobParameters());
		// remove files
		Path path = Paths.get(updateOperationMetadata.getFileName());
		if(Files.exists(path))
			Files.delete(path);
		
		InsertOperationMetadata insertOperationMetadata = this.insertOperationServiceImpl.generateInsertOperationBatchJobMetadata(updateOperationMetadata.getDimensionMetadata(), updateOperationMetadata.getProcessId());
		this.insertOperationMetadataBlockingQueue.offer(insertOperationMetadata);
	}
	
	@Override
	public void processInsertOperation() throws InterruptedException, JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
		InsertOperationMetadata insertOperationMetadata = insertOperationMetadataBlockingQueue.take();
		Job insertJob = insertOperationServiceImpl.instantiateInsertOperationBatchJob(insertOperationMetadata);
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addDate("effectiveStartDate", insertOperationMetadata.getDimensionMetadata().getEffectiveStartDate());
		this.beanRegistryServiceImpl.run(insertJob, jobParametersBuilder.toJobParameters());
		// remove files
		Path path = Paths.get(insertOperationMetadata.getFileName());
		if(Files.exists(path))
			Files.delete(path);
	}

}
