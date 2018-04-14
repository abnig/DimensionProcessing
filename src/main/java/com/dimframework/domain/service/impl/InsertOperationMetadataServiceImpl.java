package com.dimframework.domain.service.impl;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.stereotype.Service;

import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.InsertOperationMetadata;
import com.dimframework.domain.service.InsertOperationService;

@Service("insertOperationServiceImpl")
public class InsertOperationMetadataServiceImpl extends AbstractInsertOperationMetadataService implements InsertOperationService, InitializingBean {
	
	private Logger logger = Logger.getLogger(InsertOperationMetadataServiceImpl.class);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
		beanDefinitionRegistry = (BeanDefinitionRegistry) autowireCapableBeanFactory;
		this.jobBuilderFactory = new JobBuilderFactory(this.jobRepository.getObject());
		this.stepBuilderFactory = new StepBuilderFactory(this.jobRepository.getObject(), this.transactionManager);
	}
	
	@Override
	public Job instantiateInsertOperationBatchJob(InsertOperationMetadata insertOperationMetadata) {
		String name = "InsertOperation_" + insertOperationMetadata.getDimensionMetadata().getSourceTable() + "_Job_" + insertOperationMetadata.getProcessId();
		try {
			return jobBuilderFactory.get(name).repository(jobRepository.getObject()).incrementer(new RunIdIncrementer())
					.flow(super.createInsertOperationStep(insertOperationMetadata)).next(createLoadIntoDimensionStep(insertOperationMetadata)).end().build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public InsertOperationMetadata generateInsertOperationBatchJobMetadata(DimensionMetadata dimensionMetadata,
			Long processId) {
		
		StringBuilder selectSQL = new StringBuilder("SELECT ").append(dimensionMetadata.getSourceTablePKColumns()).append(" , ").append(dimensionMetadata.getSourceTableDataColumns()).append(" , ");
		selectSQL.append("'").append(dimensionMetadata.getEffectiveStartDate().toString()).append("' , '").append(dimensionMetadata.getEffectiveEndDate().toString()).append("' , 'Y' , ");
		selectSQL.append(dimensionMetadata.getPrimaryKeyHashColumn()).append(" , ").append(dimensionMetadata.getDataFieldsHashColumn());
		selectSQL.append(" FROM ").append(this.schemaName).append(".").append(dimensionMetadata.getSourceTableHash()).append(" NH ");
		selectSQL.append(" WHERE NOT EXISTS (SELECT 1 FROM ").append(this.schemaName).append(".").append(dimensionMetadata.getDimTable()).append(" DH ");
		selectSQL.append(" WHERE NH.").append(dimensionMetadata.getPrimaryKeyHashColumn()).append(" = DH.").append(dimensionMetadata.getPrimaryKeyHashColumn()).append(" AND DH.IS_ACTV_FL = 'Y');");
		logger.debug("SELECT Query to create file for insert operation: " + selectSQL.toString());
		
		String fileName = new StringBuilder(dimensionMetadata.getHashDataFilesBasePath()).append(processId.toString()).append(dimensionMetadata.getDimTable()).append("_insert").toString();
		StringBuilder insertSQL = new StringBuilder(" LOAD DATA LOCAL INFILE ").append("'").append(fileName).append("'").append(" INTO TABLE ").append(this.schemaName).append(".").append(dimensionMetadata.getDimTable());
		insertSQL.append(" FIELDS TERMINATED BY '").append(dimensionMetadata.getFieldDelimiter()).append("'");
		insertSQL.append(" LINES TERMINATED BY '").append(dimensionMetadata.getRecordTerminator()).append("'").append(" ( ");
		insertSQL.append(dimensionMetadata.getSourceTablePKColumns()).append(" , ").append(dimensionMetadata.getSourceTableDataColumns()).append(" , EFF_STRT_DT, EFF_END_DT, IS_ACTV_FL, ");
		insertSQL.append(dimensionMetadata.getPrimaryKeyHashColumn()).append(" , ").append(dimensionMetadata.getDataFieldsHashColumn()).append(" );");
		logger.debug("LOAD Query to create file for insert operation: " + insertSQL.toString());
		
		InsertOperationMetadata insertOperationMetadata = new InsertOperationMetadata(dimensionMetadata, schemaName, processId, fileName, selectSQL.toString(), insertSQL.toString());
		return insertOperationMetadata;
	}

}
