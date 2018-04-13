package com.dimframework.domain.service;

import org.springframework.batch.core.Job;

import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.InsertOperationMetadata;

public interface InsertOperationService {
	
	public Job instantiateInsertOperationBatchJob(InsertOperationMetadata insertOperationMetadata);

	public InsertOperationMetadata generateInsertOperationBatchJobMetadata(DimensionMetadata dimensionMetadata,
			Long processId);

}
