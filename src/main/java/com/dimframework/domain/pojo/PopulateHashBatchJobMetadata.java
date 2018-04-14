package com.dimframework.domain.pojo;

import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.DimensionProcessLog;

public class PopulateHashBatchJobMetadata {

	public PopulateHashBatchJobMetadata(DimensionMetadata dimensionMetadata, String selectSQL, String insertSQL, DimensionProcessLog runLog, String fileName, Long processId, String schemaName) {
		this.dimensionMetadata = dimensionMetadata;
		this.selectSQL = selectSQL;
		this.insertSQL = insertSQL;
		this.dimensionProcessLog = runLog;
		this.processId = processId;
		this.fileName= fileName;
		this.schemaName = schemaName;
	}
	
	private final DimensionMetadata dimensionMetadata;
	
	private final Long processId;
	
	private final String selectSQL;
	
	private final String insertSQL;
	
	private final String fileName;
	
	private final DimensionProcessLog dimensionProcessLog;
	
	private final String schemaName;

	public String getSelectSQL() {
		return selectSQL;
	}

	public String getInsertSQL() {
		return insertSQL;
	}

	public DimensionMetadata getDimensionMetadata() {
		return new DimensionMetadata(this.dimensionMetadata);
	}
	
	public DimensionProcessLog getDimensionProcessLog() {
		return dimensionProcessLog;
	}
	
	public String getFileName() {
		return fileName;
	}

	public Long getProcessId() {
		return processId;
	}

	public String getSchemaName() {
		return schemaName;
	}
	
}
