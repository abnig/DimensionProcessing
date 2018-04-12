package dim.processing.poc.input;

import java.io.Serializable;
import java.util.Date;

public class FicNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4192078002104987491L;

	private String nodeId;
	private String nodeTypeId;
	private String nodeName;
	private String nodeDescText;
	private String nodeOwnId;
	private String nodeStatusCode;
	private Date effectiveDate;

	private String creatorId;
	private String lastWriteId;
	private Date creationDate;
	private Date lastWriteDate;

	@Override
	public String toString() {
		return "FicNode [nodeTypeId=" + nodeTypeId + ", nodeName=" + nodeName + ", nodeDescText=" + nodeDescText
				+ ", nodeOwnId=" + nodeOwnId + ", nodeStatusCode=" + nodeStatusCode + ", effectiveDate=" + effectiveDate
				+ "]";
	}

	public int hashCodeColumns() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((toString() == null) ? 0 : toString().hashCode());
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
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
		FicNode other = (FicNode) obj;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		return true;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeTypeId() {
		return nodeTypeId;
	}

	public void setNodeTypeId(String nodeTypeId) {
		this.nodeTypeId = nodeTypeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeDescText() {
		return nodeDescText;
	}

	public void setNodeDescText(String nodeDescText) {
		this.nodeDescText = nodeDescText;
	}

	public String getNodeOwnId() {
		return nodeOwnId;
	}

	public void setNodeOwnId(String nodeOwnId) {
		this.nodeOwnId = nodeOwnId;
	}

	public String getNodeStatusCode() {
		return nodeStatusCode;
	}

	public void setNodeStatusCode(String nodeStatusCode) {
		this.nodeStatusCode = nodeStatusCode;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getLastWriteId() {
		return lastWriteId;
	}

	public void setLastWriteId(String lastWriteId) {
		this.lastWriteId = lastWriteId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastWriteDate() {
		return lastWriteDate;
	}

	public void setLastWriteDate(Date lastWriteDate) {
		this.lastWriteDate = lastWriteDate;
	}

}
