package dim.processing.poc.input;

public class FicNodeHash extends FicNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3598044888586965685L;
	
	
	public FicNodeHash() {
		super();
	}
	
	public FicNodeHash(FicNode ficNode) {
		super.setCreationDate(ficNode.getCreationDate());
		super.setCreatorId(ficNode.getCreatorId());
		super.setEffectiveDate(ficNode.getEffectiveDate());
		super.setLastWriteDate(ficNode.getLastWriteDate());
		super.setLastWriteId(ficNode.getLastWriteId());
		super.setNodeDescText(ficNode.getNodeDescText());
		super.setNodeId(ficNode.getNodeId());
		super.setNodeName(ficNode.getNodeName());
		super.setNodeOwnId(ficNode.getNodeOwnId());
		super.setNodeStatusCode(ficNode.getNodeStatusCode());
		super.setNodeTypeId(ficNode.getNodeTypeId());
		this.setHashPK(new Integer(ficNode.hashCode()).toString());
		this.setHashColumns(new Integer(ficNode.hashCodeColumns()).toString());
	}
	
	public FicNodeHash(FicNodeHash ficNodeHash) {
		super.setCreationDate(ficNodeHash.getCreationDate());
		super.setCreatorId(ficNodeHash.getCreatorId());
		super.setEffectiveDate(ficNodeHash.getEffectiveDate());
		super.setLastWriteDate(ficNodeHash.getLastWriteDate());
		super.setLastWriteId(ficNodeHash.getLastWriteId());
		super.setNodeDescText(ficNodeHash.getNodeDescText());
		super.setNodeId(ficNodeHash.getNodeId());
		super.setNodeName(ficNodeHash.getNodeName());
		super.setNodeOwnId(ficNodeHash.getNodeOwnId());
		super.setNodeStatusCode(ficNodeHash.getNodeStatusCode());
		super.setNodeTypeId(ficNodeHash.getNodeTypeId());
		this.setHashPK(ficNodeHash.getHashPK());
		this.setHashColumns(ficNodeHash.getHashColumns());
	}

	private String hashPK;
	
	private String hashColumns;

	public String getHashPK() {
		return hashPK;
	}

	public void setHashPK(String hashPK) {
		this.hashPK = hashPK;
	}

	public String getHashColumns() {
		return hashColumns;
	}

	public void setHashColumns(String hashColumns) {
		this.hashColumns = hashColumns;
	}
	
}
