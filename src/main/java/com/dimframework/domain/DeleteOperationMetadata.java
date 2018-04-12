package com.dimframework.domain;

import java.io.Serializable;

public class DeleteOperationMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1236302024311530661L;

	public DeleteOperationMetadata(DimensionMetadata dimensionMetadata, String schemaName, Long processId,
			String selectSQL, String updateSQL) {
		this.schemaName = schemaName;
		this.processId = processId;
		this.selectSQL = selectSQL;
		this.updateSQL = updateSQL;
		this.dimensionMetadata = dimensionMetadata;
	}

	private final String schemaName;
	private final Long processId;
	private final String selectSQL;
	private final String updateSQL;
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
}
