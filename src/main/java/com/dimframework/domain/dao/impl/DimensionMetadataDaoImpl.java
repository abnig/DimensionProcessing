package com.dimframework.domain.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.InsertOperationMetadata;
import com.dimframework.domain.dao.DimensionMetadataDao;
import com.dimframework.domain.pojo.PopulateHashBatchJobMetadata;
import com.dimframework.rowmapper.DimensionMetadataRowMapper;

@Repository("dimensionMetadataDaoImpl")
public class DimensionMetadataDaoImpl implements DimensionMetadataDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcMySQLTemplate;
	
	private Logger logger = Logger.getLogger(DimensionMetadataDaoImpl.class);

	@Override
	public Optional<List<DimensionMetadata>> getByDomainName(String domainName) {
		StringBuilder sql = new StringBuilder("SELECT MetadataId, SourceTable, SourceTableHash, DimTable, CommitInterval, SourceTablePKColumns, SourceTableDataColumns, DomainName, concurrencyLimit, dataSourceBeanName, dataSelectionStrategy, ActiveFlag, primaryKeyHashColumn, dataFieldsHashColumn FROM DIM_METADATA WHERE DomainName = :domainName and ActiveFlag = 'Y'");
		Map<String, Object> map = new HashMap<>();
		map.put("domainName", domainName);
		List<DimensionMetadata> list = this.namedJdbcMySQLTemplate.query(sql.toString(), map, new DimensionMetadataRowMapper());
		return Optional.ofNullable(list);
	}

	@Override
	public void truncateTable(String tableName) {
		StringBuilder sql = new StringBuilder("TRUNCATE TABLE " + tableName);
		this.namedJdbcMySQLTemplate.getJdbcOperations().execute(sql.toString());
	}

	@Override
	public void executeLoadIntoHash(PopulateHashBatchJobMetadata  hashFileMetadata) {
		StringBuilder sql = new StringBuilder("LOAD DATA LOCAL INFILE '").append(hashFileMetadata.getFileName()).append("'");
		sql.append(" INTO TABLE ").append(hashFileMetadata.getSchemaName()).append(".").append(hashFileMetadata.getDimensionMetadata().getSourceTableHash());
		sql.append(" FIELDS TERMINATED BY '").append(hashFileMetadata.getDimensionMetadata().getFieldDelimiter()).append("'"); 
		sql.append(" LINES TERMINATED BY '").append(hashFileMetadata.getDimensionMetadata().getRecordTerminator()).append("' (");
		sql.append(hashFileMetadata.getDimensionMetadata().getSourceTablePKColumns()).append(", ");
		sql.append(hashFileMetadata.getDimensionMetadata().getSourceTableDataColumns()).append(", ");
		sql.append(hashFileMetadata.getDimensionMetadata().getPrimaryKeyHashColumn()).append(", ");
		sql.append(hashFileMetadata.getDimensionMetadata().getDataFieldsHashColumn()).append(");");
		String sqlString = sql.toString();
		logger.debug("SQL Command to load into " + hashFileMetadata.getDimensionMetadata().getSourceTableHash() + " table is: " + sqlString);
		this.namedJdbcMySQLTemplate.getJdbcOperations().execute(sqlString);
	}

	@Override
	public void executeLoadIntoDimensionTable(InsertOperationMetadata insertOperationMetadata) {
		logger.debug("SQL Command to load into " + insertOperationMetadata.getDimensionMetadata().getDimTable() + " table is: " + insertOperationMetadata.getInsertSQL());
		this.namedJdbcMySQLTemplate.getJdbcOperations().execute(insertOperationMetadata.getInsertSQL());
		
	}

}
