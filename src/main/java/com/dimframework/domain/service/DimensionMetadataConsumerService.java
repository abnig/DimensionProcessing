package com.dimframework.domain.service;

import java.io.IOException;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import com.dimframework.domain.HashFileMetadata;

public interface DimensionMetadataConsumerService {
	
	HashFileMetadata processDimensionMetadata() throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException;
	
	void processHashFileMetadata() throws InterruptedException, IOException;
	
	void processDeleteOperation() throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException;
	
	void processInsertOperation() throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException;

	void processUpdateOperation();

}
