package com.dimframework.domain;

import java.io.Serializable;

public class InsertOperationMetadata implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3434158548040307022L;

	public InsertOperationMetadata(DimensionMetadata dimensionMetadata, String schemaName, Long processId, String fileName,
			String selectSQL, String insertSQL) {
		this.schemaName = schemaName;
		this.processId = processId;
		this.selectSQL = selectSQL;
		this.insertSQL = insertSQL;
		this.dimensionMetadata = dimensionMetadata;
		this.fileName = fileName;
	}

	private final String fileName;
	private final String schemaName;
	private final Long processId;
	private final String selectSQL;
	private final String insertSQL;
	private final DimensionMetadata dimensionMetadata;

	public String getSchemaName() {
		return schemaName;
	}

	public Long getProcessId() {
		return processId;
	}

	public String getSelectSQL() {
		return selectSQL;
	}

	public DimensionMetadata getDimensionMetadata() {
		return dimensionMetadata;
	}

	public String getInsertSQL() {
		return insertSQL;
	}

	public String getFileName() {
		return fileName;
	}
	
}
