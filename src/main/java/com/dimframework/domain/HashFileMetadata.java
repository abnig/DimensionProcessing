package com.dimframework.domain;

import java.io.Serializable;

public class HashFileMetadata implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7672206732578797210L;
	
	private final String fileName; 
	
	private final String schemaName;
	
	private final Long processId;
	
	private final DimensionMetadata dimensionMetadata;

	/*
	LOAD DATA LOCAL INFILE '/dimension-processing/hash-datafiles/test.dat' INTO TABLE dim.employee_hash 
	FIELDS TERMINATED BY '|'
	LINES TERMINATED BY '~'
	(emp_ID,
	name,
	salary,
	HASH_PK,
	HASH_COL);
*/
	
	public HashFileMetadata(DimensionMetadata dimensionMetadata, String fileName, String schemaName, Long processId) {
		this.fileName = fileName;
		this.schemaName = schemaName;
		this.processId = processId;
		this.dimensionMetadata = dimensionMetadata;
	}

	public String getFileName() {
		return fileName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public Long getProcessId() {
		return processId;
	}

	public DimensionMetadata getDimensionMetadata() {
		return dimensionMetadata;
	}
	
}
