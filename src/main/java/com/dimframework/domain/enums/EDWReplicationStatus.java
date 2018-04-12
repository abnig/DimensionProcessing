package com.dimframework.domain.enums;

public enum EDWReplicationStatus {
	INIT("INIT"),
	VERTICA_SUCCESS("VERTICA_SUCCESS"), 
	NO_DATA_TO_PROCESS("NO_DATA_TO_PROCESS"), 
	FILE_GEN_SUCCESS("FILE_GEN_SUCCESS"), 
	SELECT_SUCCESSFUL("SELECT_SUCCESSFUL"), 
	SELECT_FAILURE("SELECT_FAILURE"), 
	FILE_GEN_FAILURE("FILE_GEN_FAILURE"), 
	VERTICA_FAILURE("VERTICA_FAILURE"), 
	VERTICA_REJECT("VERTICA_REJECT"), 
	METADATA_ERROR("METADATA_ERROR");


	private final String status;

	private EDWReplicationStatus(String status) {
		this.status = status;
	}

	public String status() {
		return status;
	}

}
