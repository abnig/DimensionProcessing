package com.dimframework.domain.service;

import org.springframework.batch.core.Job;

import com.dimframework.domain.DeleteOperationMetadata;

public interface DeleteOperationService {
	
	public Job instantiateDeleteOperationBatchJob(DeleteOperationMetadata o);

}
