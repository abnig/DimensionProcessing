package com.dimframework.domain.dao;

import java.util.List;
import java.util.Optional;

import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.InsertOperationMetadata;
import com.dimframework.domain.UpdateOperationMetadata;
import com.dimframework.domain.pojo.PopulateHashBatchJobMetadata;

public interface DimensionMetadataDao {
	
	Optional<List<DimensionMetadata>> getByDomainName(String domainName);
	
	void truncateTable(String tableName);
	
	void executeLoadIntoHash(PopulateHashBatchJobMetadata  hashFileMetadata);
	
	void executeLoadIntoDimensionTable(InsertOperationMetadata insertOperationMetadata);

	void executeLoadIntoDimensionTable(UpdateOperationMetadata updateOperationMetadata);

}
