package dim.processing.poc.input;

import java.io.Serializable;

public class NodeSK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7553264420860624726L;
	
	private Integer nodeSK;

	public NodeSK(int nodeSK) {
		this.nodeSK = nodeSK;
	}

	public Integer getNodeSK() {
		return nodeSK;
	}

	public void setNodeSK(Integer nodeSK) {
		this.nodeSK = nodeSK;
	}
	
}
