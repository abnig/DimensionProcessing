package dim.processing.poc.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dim.processing.poc.input.FicNode;

public class FicNodeRowMapper implements RowMapper<FicNode>{

	@Override
	public FicNode mapRow(ResultSet rs, int arg1) throws SQLException {
		FicNode ficNode = new FicNode();
		ficNode.setCreationDate(rs.getDate("T_CREATION_DATE"));
		ficNode.setCreatorId(rs.getString("T_CREATOR_ID"));
		ficNode.setEffectiveDate(rs.getDate("EFF_DT"));
		ficNode.setLastWriteDate(rs.getDate("T_LAST_WRITE"));
		ficNode.setLastWriteId(rs.getString("T_LAST_USER_ID"));
		ficNode.setNodeDescText(rs.getString("NODE_DESC_TX"));
		ficNode.setNodeId(rs.getString("NODE_ID"));
		ficNode.setNodeName(rs.getString("NODE_NM"));
		ficNode.setNodeOwnId(rs.getString("NODE_OWN_ID"));
		ficNode.setNodeStatusCode(rs.getString("NODE_STAT_CD"));
		ficNode.setNodeTypeId(rs.getString("NODE_TYP_ID"));
		return ficNode;
	}

}
