package com.dimframework.domain.service;

import java.util.Date;
import java.util.List;

import com.dimframework.domain.DeleteOperationMetadata;
import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.DimensionProcessLog;
import com.dimframework.domain.InsertOperationMetadata;
import com.dimframework.domain.UpdateOperationMetadata;
import com.dimframework.domain.pojo.PopulateHashBatchJobMetadata;

public interface DimensionMetadataService {

	List<DimensionMetadata> getByDomainName(String domainName, Date effectiveStartDate, Date effectiveEndDate);

	void truncateTable(String tableName);

	PopulateHashBatchJobMetadata generatePopulateHashBatchJobMetadata(DimensionMetadata dimensionMetadata, DimensionProcessLog runLog);
	
	String concatenatePKHashAndDataColumnHash(String rowData);
	
	void executeLoadIntoHash(PopulateHashBatchJobMetadata  hashFileMetadata);
	
	void executeLoadIntoDimensionTable(InsertOperationMetadata insertOperationMetadata);
	
	DeleteOperationMetadata generateDeleteOperationBatchJobMetadata(DimensionMetadata dimensionMetadata, Long processId);

	UpdateOperationMetadata generateUpdateOperationBatchJobMetadata(DimensionMetadata dimensionMetadata,
			Long processId);

	void executeLoadIntoDimensionTable(UpdateOperationMetadata updateOperationMetadata);
	
}
