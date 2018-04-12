package com.dimframework.domain.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OutboundFileData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3299069405982232319L;

	private final Long runId;
	private final String absoluteFileName;
	private final Integer metaDataId;
	private final List<List<String>> fileData;
	// needed to be passed to VerticaStreamCopyParameters
	private final String delimiter;
	private final String recordTerminator;
	private final Integer totalNumOfRecords;
	private final Long jobInstanceId;
	private final Date orderDate;

	public Integer getTotalNumOfRecords() {
		return totalNumOfRecords;
	}

	public OutboundFileData(final String absoluteFileName, final Integer metaDataId, final List<List<String>> fileData,
			final Long runId,
			final String delimiter, final Integer totalNumberOfRecords, final Long jobInstanceId, final Date orderDate, final String recordTerminator) {
		this.absoluteFileName = absoluteFileName;
		this.metaDataId = metaDataId;
		this.fileData = fileData;
		this.runId = runId;
		this.delimiter = delimiter;
		this.totalNumOfRecords = totalNumberOfRecords;
		this.jobInstanceId = jobInstanceId;
		this.orderDate = orderDate;
		this.recordTerminator = recordTerminator;
	}

	public String getAbsoluteFileName() {
		return absoluteFileName;
	}

	public Integer getMetaDataId() {
		return metaDataId;
	}

	public List<List<String>> getFileData() {
		return fileData;
	}

	public Long getRunId() {
		return runId;
	}

	public String getDelimiter() {
		return delimiter;
	}
	
	public Long getJobInstanceId() {
		return jobInstanceId;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	
	public String getRecordTerminator() {
		return recordTerminator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((absoluteFileName == null) ? 0 : absoluteFileName.hashCode());
		result = prime * result + ((metaDataId == null) ? 0 : metaDataId.hashCode());
		result = prime * result + ((runId == null) ? 0 : runId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OutboundFileData other = (OutboundFileData) obj;
		if (absoluteFileName == null) {
			if (other.absoluteFileName != null)
				return false;
		} else if (!absoluteFileName.equals(other.absoluteFileName))
			return false;
		if (metaDataId == null) {
			if (other.metaDataId != null)
				return false;
		} else if (!metaDataId.equals(other.metaDataId))
			return false;
		if (runId == null) {
			if (other.runId != null)
				return false;
		} else if (!runId.equals(other.runId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OutboundFileData [runId=" + runId + ", absoluteFileName=" + absoluteFileName + ", metaDataId="
				+ metaDataId + "]";
	}

}
