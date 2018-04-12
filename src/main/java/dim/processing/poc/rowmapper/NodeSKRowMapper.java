package dim.processing.poc.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dim.processing.poc.input.NodeSK;

public class NodeSKRowMapper implements RowMapper<NodeSK> {

	@Override
	public NodeSK mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new NodeSK(rs.getInt(1));
	}

}
