package dim.processing.poc.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dim.processing.poc.output.FicNodeDimensionHash;

public class FicNodeBaseDimensionRowMapper implements RowMapper<FicNodeDimensionHash>{

	@Override
	public FicNodeDimensionHash mapRow(ResultSet rs, int arg1) throws SQLException {
		FicNodeDimensionHash ficNode = new FicNodeDimensionHash();
		ficNode.setNodeSK(rs.getInt("nodeSK"));
		ficNode.setCreationDate(rs.getDate("creationDate"));
		ficNode.setCreatorId(rs.getString("creatorId"));
		ficNode.setEffectiveDate(rs.getDate("effectiveDate"));
		ficNode.setLastWriteDate(rs.getDate("lastWriteDate"));
		ficNode.setLastWriteId(rs.getString("lastWriteId"));
		ficNode.setNodeDescText(rs.getString("nodeDescText"));
		ficNode.setNodeId(rs.getString("nodeId"));
		ficNode.setNodeName(rs.getString("nodeName"));
		ficNode.setNodeOwnId(rs.getString("nodeOwnId"));
		ficNode.setNodeStatusCode(rs.getString("nodeStatusCode"));
		ficNode.setNodeTypeId(rs.getString("nodeTypeId"));
		ficNode.setEffectiveEndDate(rs.getTimestamp("effectiveEndDate"));
		ficNode.setEffectiveStartDate(rs.getDate("effectiveStartDate"));
		ficNode.setIsActiveFlag(rs.getString("isActiveFlag"));
		ficNode.setHashColumns(rs.getString("hashColumns"));
		ficNode.setHashPK(rs.getString("hashPK"));
		return ficNode;
	}

}

