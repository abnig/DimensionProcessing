package com.dimframework.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class DimensionProcessLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8145157718823758296L;

	private final Long processId;

	private final Timestamp runStartTimestamp;

	private final Timestamp runEndTimestamp;

	private final Integer metadataId;

	private final String endStatus;

	private final String failureReason;

	public DimensionProcessLog(Long processId, Timestamp runStartTimestamp, Timestamp runEndTimestamp, Integer metadataId,
			String endStatus, String failureReason) {
		super();
		this.processId = processId;
		this.runStartTimestamp = runStartTimestamp;
		this.runEndTimestamp = runEndTimestamp;
		this.metadataId = metadataId;
		this.endStatus = endStatus;
		this.failureReason = failureReason;
	}

	public Long getProcessId() {
		return processId;
	}

	public Timestamp getRunStartTimestamp() {
		return runStartTimestamp;
	}

	public Timestamp getRunEndTimestamp() {
		return runEndTimestamp;
	}

	public Integer getMetadataId() {
		return metadataId;
	}

	public String getEndStatus() {
		return endStatus;
	}

	public String getFailureReason() {
		return failureReason;
	}

}
