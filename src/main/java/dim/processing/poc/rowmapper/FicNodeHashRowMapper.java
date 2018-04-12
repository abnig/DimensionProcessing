package dim.processing.poc.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import dim.processing.poc.input.FicNode;
import dim.processing.poc.input.FicNodeHash;

public class FicNodeHashRowMapper extends FicNodeRowMapper {

	@Override
	public FicNodeHash mapRow(ResultSet rs, int rowNum) throws SQLException {
		FicNode ficNode = super.mapRow(rs, rowNum);
		FicNodeHash ficNodeHash = new FicNodeHash(ficNode);
		ficNodeHash.setHashPK(rs.getString("HASH_PK"));
		ficNodeHash.setHashColumns(rs.getString("HASH_COL"));
		return ficNodeHash;
	}

}
