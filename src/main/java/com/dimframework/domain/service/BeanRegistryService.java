package com.dimframework.domain.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import com.dimframework.domain.pojo.OutboundFileMetadata;
import com.dimframework.domain.pojo.PopulateHashBatchJobMetadata;

public interface BeanRegistryService {
	Step createStep(OutboundFileMetadata o) throws Exception;
	Object getBeanByName(String o);
	String getPrototypePopulateHashBatchJobMetadata(PopulateHashBatchJobMetadata populateHashBatchJobMetadata);
	String instantiateFlatFileItemWriter(OutboundFileMetadata o);
	String instantiateJdbcPagingItemReader(OutboundFileMetadata o) throws Exception;
	String instantiateJdbcCursorItemReader(OutboundFileMetadata o) throws Exception;
	
	Job instantiatePopulateHashJob(String populateHashBatchJobMetadataBeanName);
	
	String instantiateSqlPagingQueryProviderFactoryBean(OutboundFileMetadata o) throws Exception;
	void unregisterBeansFromContext(Long runId);
	
	void addToMap(Long runId, String beanName);
	
	void removeFromMap(Long runId, String beanName);
	
	JobExecution run(Job fileCreationJob, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException;

}
