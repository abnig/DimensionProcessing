package com.dimframework.domain;

import java.io.Serializable;

public class UpdateOperationMetadata implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3434158548040307022L;

	public UpdateOperationMetadata(DimensionMetadata dimensionMetadata, String schemaName, Long processId,
			String selectSQL, String updateSQL, String insertSQL) {
		this.schemaName = schemaName;
		this.processId = processId;
		this.selectSQL = selectSQL;
		this.updateSQL = updateSQL;
		this.insertSQL = insertSQL;
		this.dimensionMetadata = dimensionMetadata;
	}

	private final String schemaName;
	private final Long processId;
	private final String selectSQL;
	private final String updateSQL;
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

	public String getUpdateSQL() {
		return updateSQL;
	}

	public DimensionMetadata getDimensionMetadata() {
		return dimensionMetadata;
	}

	public String getInsertSQL() {
		return insertSQL;
	}
	
}
