package com.dimframework.domain;

import java.io.Serializable;
import java.util.Date;

import com.dimframework.domain.pojo.DataSelectionStrategy;

public class DimensionMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2227022242156655219L;

	private final Integer metadataId;
	private final String sourceTable;
	private final String sourceTableHash;
	private final String dimTable;
	private final Integer commitInterval;
	private final String sourceTablePKColumns;
	private final String sourceTableDataColumns;
	private final String domainName;
	private final Integer concurrencyLimit;
	private final String dataSourceBeanName;
	private final DataSelectionStrategy dataSelectionStrategy;
	private final String activeFlag;
	private final String primaryKeyHashColumn;
	private final String dataFieldsHashColumn;
	private final Date effectiveStartDate;
	private final Date effectiveEndDate;
	private final String fieldDelimiter;
	private final String recordTerminator;
	private final String hashDataFilesBasePath;
	
	public DimensionMetadata(DimensionMetadata dimensionMetadata){
		this.metadataId = dimensionMetadata.getMetadataId();
		this.sourceTable = dimensionMetadata.getSourceTable();
		this.sourceTableHash = dimensionMetadata.getSourceTableHash();
		this.dimTable = dimensionMetadata.getDimTable();
		this.commitInterval = dimensionMetadata.getCommitInterval();
		this.sourceTablePKColumns = dimensionMetadata.getSourceTablePKColumns();
		this.sourceTableDataColumns = dimensionMetadata.getSourceTableDataColumns();
		this.domainName = dimensionMetadata.getDomainName();
		this.concurrencyLimit =dimensionMetadata.getConcurrencyLimit();
		this.dataSourceBeanName = dimensionMetadata.getDataSourceBeanName();
		this.dataSelectionStrategy = DataSelectionStrategy.valueOf(dimensionMetadata.getDataSelectionStrategy().name());
		this.activeFlag = dimensionMetadata.getActiveFlag();
		this.primaryKeyHashColumn = dimensionMetadata.getPrimaryKeyHashColumn();
		this.dataFieldsHashColumn = dimensionMetadata.getDataFieldsHashColumn();
		this.effectiveStartDate = dimensionMetadata.getEffectiveStartDate();
		this.effectiveEndDate = dimensionMetadata.getEffectiveEndDate();
		this.fieldDelimiter = dimensionMetadata.getFieldDelimiter();
		this.recordTerminator= dimensionMetadata.getRecordTerminator();
		this.hashDataFilesBasePath = dimensionMetadata.getHashDataFilesBasePath();
		
	}
	
	public DimensionMetadata(DimensionMetadata dimensionMetadata, Date effectiveStartDate, Date effectiveEndDate, String fieldDelimiter, 
			String recordTerminator, String hashDataFilesBasePath){
		this.metadataId = dimensionMetadata.getMetadataId();
		this.sourceTable = dimensionMetadata.getSourceTable();
		this.sourceTableHash = dimensionMetadata.getSourceTableHash();
		this.dimTable = dimensionMetadata.getDimTable();
		this.commitInterval = dimensionMetadata.getCommitInterval();
		this.sourceTablePKColumns = dimensionMetadata.getSourceTablePKColumns();
		this.sourceTableDataColumns = dimensionMetadata.getSourceTableDataColumns();
		this.domainName = dimensionMetadata.getDomainName();
		this.concurrencyLimit =dimensionMetadata.getConcurrencyLimit();
		this.dataSourceBeanName = dimensionMetadata.getDataSourceBeanName();
		this.dataSelectionStrategy = DataSelectionStrategy.valueOf(dimensionMetadata.getDataSelectionStrategy().name());
		this.activeFlag = dimensionMetadata.getActiveFlag();
		this.primaryKeyHashColumn = dimensionMetadata.getPrimaryKeyHashColumn();
		this.dataFieldsHashColumn = dimensionMetadata.getDataFieldsHashColumn();
		this.effectiveStartDate = effectiveStartDate;
		this.effectiveEndDate = effectiveEndDate;
		this.fieldDelimiter =fieldDelimiter;
		this.recordTerminator= recordTerminator;
		this.hashDataFilesBasePath = hashDataFilesBasePath;
	}
	

	public DimensionMetadata(Integer metadataId, String sourceTable, String sourceTableHash, String dimTable,
			Integer commitInterval, String sourceTablePKColumns, String sourceTableDataColumns, String domainName,
			Integer concurrencyLimit, String dataSourceBeanName, String dataSelectionStrategy, String activeFlag, String primaryKeyHashColumn, String dataFieldsHashColumn, Date effectiveStartDate, Date effectiveEndDate, String fieldDelimiter, 
			String recordTerminator, String hashDataFilesBasePath) {
		super();
		this.metadataId = metadataId;
		this.sourceTable = sourceTable;
		this.sourceTableHash = sourceTableHash;
		this.dimTable = dimTable;
		this.commitInterval = commitInterval;
		this.sourceTablePKColumns = sourceTablePKColumns;
		this.sourceTableDataColumns = sourceTableDataColumns;
		this.domainName = domainName;
		this.concurrencyLimit = concurrencyLimit;
		this.dataSourceBeanName = dataSourceBeanName;
		this.dataSelectionStrategy = DataSelectionStrategy.valueOf(dataSelectionStrategy);
		this.activeFlag = activeFlag;
		this.primaryKeyHashColumn = primaryKeyHashColumn;
		this.dataFieldsHashColumn = dataFieldsHashColumn;
		this.effectiveStartDate = effectiveStartDate;
		this.effectiveEndDate = effectiveEndDate;
		this.fieldDelimiter = fieldDelimiter;
		this.recordTerminator= recordTerminator;
		this.hashDataFilesBasePath = hashDataFilesBasePath;
	}

	public Integer getMetadataId() {
		return metadataId;
	}

	public String getSourceTable() {
		return sourceTable;
	}

	public String getSourceTableHash() {
		return sourceTableHash;
	}

	public String getDimTable() {
		return dimTable;
	}

	public Integer getCommitInterval() {
		return commitInterval;
	}

	public String getSourceTablePKColumns() {
		return sourceTablePKColumns;
	}

	public String getSourceTableDataColumns() {
		return sourceTableDataColumns;
	}

	public String getDomainName() {
		return domainName;
	}

	public Integer getConcurrencyLimit() {
		return concurrencyLimit;
	}

	public String getDataSourceBeanName() {
		return dataSourceBeanName;
	}
	
	public DataSelectionStrategy getDataSelectionStrategy() {
		return dataSelectionStrategy;
	}
	
	public String getActiveFlag() {
		return activeFlag;
	}
	
	public String getPrimaryKeyHashColumn() {
		return primaryKeyHashColumn;
	}

	public String getDataFieldsHashColumn() {
		return dataFieldsHashColumn;
	}
	
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}
	
	public String getFieldDelimiter() {
		return fieldDelimiter;
	}

	public String getRecordTerminator() {
		return recordTerminator;
	}

	public String getHashDataFilesBasePath() {
		return hashDataFilesBasePath;
	}
	
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	@Override
	public String toString() {
		return "DimensionMetadata [metadataId=" + metadataId + ", sourceTable=" + sourceTable + ", sourceTableHash="
				+ sourceTableHash + ", dimTable=" + dimTable + ", commitInterval=" + commitInterval
				+ ", sourceTablePKColumns=" + sourceTablePKColumns + ", sourceTableDataColumns="
				+ sourceTableDataColumns + ", domainName=" + domainName + ", concurrencyLimit=" + concurrencyLimit
				+ ", dataSourceBeanName=" + dataSourceBeanName + "]";
	}

}
