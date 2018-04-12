package com.dimframework.domain.service;

import org.springframework.batch.core.Job;

import com.dimframework.domain.UpdateOperationMetadata;

public interface UpdateOperationService {

	public Job instantiateUpdateOperationBatchJob(UpdateOperationMetadata updateOperationMetadata);
}
