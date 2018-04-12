package dim.processing.poc.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import dim.processing.poc.input.Account;
import dim.processing.poc.input.AccountHash;

public class AccountHashRowMapper extends AccountRowMapper {

	@Override
	public AccountHash mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = super.mapRow(rs, rowNum);
		AccountHash accountHash = new AccountHash(account);
		accountHash.setHashPK(rs.getString("HASH_PK"));
		accountHash.setHashColumns(rs.getString("HASH_COL"));
		return accountHash;
	}


}
