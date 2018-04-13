package com.dimframework.domain.service.impl;

import org.springframework.batch.core.Job;
import org.springframework.stereotype.Service;

import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.InsertOperationMetadata;
import com.dimframework.domain.service.InsertOperationService;

@Service("insertOperationServiceImpl")
public class InsertOperationMetadataService implements InsertOperationService {

	@Override
	public Job instantiateInsertOperationBatchJob(InsertOperationMetadata insertOperationMetadata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InsertOperationMetadata generateInsertOperationBatchJobMetadata(DimensionMetadata dimensionMetadata,
			Long processId) {
		// TODO Auto-generated method stub
		return null;
	}

}
