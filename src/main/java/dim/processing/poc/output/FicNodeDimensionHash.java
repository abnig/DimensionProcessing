package dim.processing.poc.output;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import dim.processing.poc.input.FicNode;
import dim.processing.poc.input.FicNodeHash;

public class FicNodeDimensionHash extends FicNodeHash implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8056454979772063145L;

	private Integer nodeSK;
	private Date effectiveStartDate;
	private Timestamp effectiveEndDate;
	private String isActiveFlag;

	public FicNodeDimensionHash() {
	}

	public FicNodeDimensionHash(FicNode ficNode) {
		this.setEffectiveDate(ficNode.getEffectiveDate());
		this.setNodeDescText(ficNode.getNodeDescText());
		this.setNodeId(ficNode.getNodeId());
		this.setNodeName(ficNode.getNodeName());
		this.setNodeOwnId(ficNode.getNodeOwnId());
		this.setNodeStatusCode(ficNode.getNodeStatusCode());
		this.setNodeTypeId(ficNode.getNodeTypeId());
	}
	
	public FicNodeDimensionHash(FicNodeHash ficNodeHash) {
		super(ficNodeHash);
		
	}

	public Integer getNodeSK() {
		return nodeSK;
	}

	public void setNodeSK(Integer nodeSK) {
		this.nodeSK = nodeSK;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Timestamp getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Timestamp effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public String getIsActiveFlag() {
		return isActiveFlag;
	}

	public void setIsActiveFlag(String isActiveFlag) {
		this.isActiveFlag = isActiveFlag;
	}

}
