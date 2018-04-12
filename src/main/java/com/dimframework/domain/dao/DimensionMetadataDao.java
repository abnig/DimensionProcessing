package com.dimframework.domain.dao;

import java.util.List;
import java.util.Optional;

import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.HashFileMetadata;

public interface DimensionMetadataDao {
	
	Optional<List<DimensionMetadata>> getByDomainName(String domainName);
	
	void truncateTable(String tableName);
	
	void executeLoadIntoHash(HashFileMetadata hashFileMetadata);

}
