package com.dimframework.domain.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.springframework.batch.item.database.Order;


public final class OutboundFileMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4856895523643353916L;

	private Integer metadataId;
	private String selectSQL;
	private String delimiter;
	private String recordTerminator;
	private String absoluteDataFileName;
	private Date lastSuccessfulReplicationTimeStamp;
	private Long jobInstanceId;
	private String sourceEntity;
	private String targetEntity;
	private String TargetSchema;
	private Date orderDate;
	private Map<String, Object> parameterMap;
	private String selectClause;
	private String fromClause;
	private String whereClause;
	private Map<String, Order> sortKeys;
	private Long runId;
	private Integer concurrencyLimit;
	private Integer commitInterval;
	private String dataSource;

	public OutboundFileMetadata(OutboundFileMetadata param) {
		this.metadataId = param.getMetadataId();
		this.selectSQL = param.getSelectSQL();
		this.delimiter = param.getDelimiter();
		this.absoluteDataFileName = param.getAbsoluteDataFileName();
		this.lastSuccessfulReplicationTimeStamp = param.getLastSuccessfulReplicationTimeStamp();
		this.jobInstanceId = param.getJobInstanceId();
		this.sourceEntity = param.getSourceEntity();
		this.targetEntity = param.getTargetEntity();
		this.TargetSchema = param.getTargetSchema();
		this.orderDate = param.getOrderDate();
		this.parameterMap = param.getParameterMap();
		this.selectClause = param.getSelectClause();
		this.fromClause = param.getFromClause();
		this.whereClause = param.getWhereClause();
		this.sortKeys = param.getSortKeys();
		this.runId = param.getRunId();
		this.commitInterval = param.getCommitInterval();
		this.concurrencyLimit = param.getConcurrencyLimit();
		this.dataSource = param.getDataSource();
		this.recordTerminator = param.getRecordTerminator();
	}

	public OutboundFileMetadata() {
	}

	public void setConcurrencyLimit(Integer concurrencyLimit) {
		this.concurrencyLimit = concurrencyLimit;
	}

	public void setCommitInterval(Integer commitInterval) {
		this.commitInterval = commitInterval;
	}

	public Integer getConcurrencyLimit() {
		return concurrencyLimit;
	}

	public Integer getCommitInterval() {
		return commitInterval;
	}

	public Date getLastSuccessfulReplicationTimeStamp() {
		return lastSuccessfulReplicationTimeStamp;
	}

	public Integer getMetadataId() {
		return metadataId;
	}

	public String getSelectSQL() {
		return selectSQL;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public String getAbsoluteDataFileName() {
		return absoluteDataFileName;
	}

	public Long getJobInstanceId() {
		return jobInstanceId;
	}

	public String getSourceEntity() {
		return sourceEntity;
	}

	public String getTargetEntity() {
		return targetEntity;
	}

	public String getTargetSchema() {
		return TargetSchema;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	public String getSelectClause() {
		return selectClause;
	}

	public String getFromClause() {
		return fromClause;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public Map<String, Order> getSortKeys() {
		return sortKeys;
	}

	public Long getRunId() {
		return runId;
	}

	public void setMetadataId(Integer metadataId) {
		this.metadataId = metadataId;
	}

	public void setSelectSQL(String selectSQL) {
		this.selectSQL = selectSQL;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public void setAbsoluteDataFileName(String absoluteDataFileName) {
		this.absoluteDataFileName = absoluteDataFileName;
	}

	public void setLastSuccessfulReplicationTimeStamp(Date lastSuccessfulReplicationTimeStamp) {
		this.lastSuccessfulReplicationTimeStamp = lastSuccessfulReplicationTimeStamp;
	}

	public void setJobInstanceId(Long jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	public void setSourceEntity(String sourceEntity) {
		this.sourceEntity = sourceEntity;
	}

	public void setTargetEntity(String targetEntity) {
		this.targetEntity = targetEntity;
	}

	public void setTargetSchema(String targetSchema) {
		TargetSchema = targetSchema;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public void setSelectClause(String selectClause) {
		this.selectClause = selectClause;
	}

	public void setFromClause(String fromClause) {
		this.fromClause = fromClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public void setSortKeys(Map<String, Order> sortKeys) {
		this.sortKeys = sortKeys;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getRecordTerminator() {
		return recordTerminator;
	}

	public void setRecordTerminator(String recordTerminator) {
		this.recordTerminator = recordTerminator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((absoluteDataFileName == null) ? 0 : absoluteDataFileName.hashCode());
		result = prime * result + ((delimiter == null) ? 0 : delimiter.hashCode());
		result = prime * result
				+ ((lastSuccessfulReplicationTimeStamp == null) ? 0 : lastSuccessfulReplicationTimeStamp.hashCode());
		result = prime * result + ((metadataId == null) ? 0 : metadataId.hashCode());
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
		OutboundFileMetadata other = (OutboundFileMetadata) obj;
		if (absoluteDataFileName == null) {
			if (other.absoluteDataFileName != null)
				return false;
		} else if (!absoluteDataFileName.equals(other.absoluteDataFileName))
			return false;
		if (delimiter == null) {
			if (other.delimiter != null)
				return false;
		} else if (!delimiter.equals(other.delimiter))
			return false;
		if (lastSuccessfulReplicationTimeStamp == null) {
			if (other.lastSuccessfulReplicationTimeStamp != null)
				return false;
		} else if (!lastSuccessfulReplicationTimeStamp.equals(other.lastSuccessfulReplicationTimeStamp))
			return false;
		if (metadataId == null) {
			if (other.metadataId != null)
				return false;
		} else if (!metadataId.equals(other.metadataId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OutboundFileMetadata [selectSQL=" + selectSQL + ", absoluteDataFileName=" + absoluteDataFileName
				+ ", jobInstanceId=" + jobInstanceId + ", targetEntity=" + targetEntity + ", TargetSchema="
				+ TargetSchema + ", orderDate=" + orderDate + ", selectClause=" + selectClause + ", fromClause="
				+ fromClause + ", whereClause=" + whereClause + ", sortKeys=" + sortKeys + ", runId=" + runId
				+ ", concurrencyLimit=" + concurrencyLimit + ", commitInterval=" + commitInterval + "]";
	}

}
