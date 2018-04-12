package com.dimframework.domain.pojo;

import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.DimensionProcessLog;

public class PopulateHashBatchJobMetadata {

	public PopulateHashBatchJobMetadata(DimensionMetadata dimensionMetadata, String selectSQL, String insertSQL, DimensionProcessLog runLog, String fullyQualifiedFileName) {
		this.dimensionMetadata = dimensionMetadata;
		this.selectSQL = selectSQL;
		this.insertSQL = insertSQL;
		this.dimensionProcessLog = runLog;
		this.fullyQualifiedFileName= fullyQualifiedFileName;
	}
	
	private final DimensionMetadata dimensionMetadata;
	
	private final String selectSQL;
	
	private final String insertSQL;
	
	private final String fullyQualifiedFileName;
	
	private final DimensionProcessLog dimensionProcessLog;

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
	
	public String getFullyQualifiedFileName() {
		return fullyQualifiedFileName;
	}
	
}
