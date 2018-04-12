package dim.processing.poc.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dim.processing.poc.input.Account;

public class AccountRowMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = new Account();
		account.setOPER_AGMT_CD(rs.getString("OPER_AGMT_CD"));
		account.setOPER_AGMT_ID(rs.getString("OPER_AGMT_ID"));
		account.setUNQ_REL_ID(rs.getString("UNQ_REL_ID"));
		account.setSRC_REL_ID(rs.getString("SRC_REL_ID"));
		account.setSRC_REL_CD(rs.getString("SRC_REL_CD"));
		account.setSRC_DESC_TX(rs.getString("SRC_DESC_TX"));
		account.setSRC_REL_CTRY_NM(rs.getString("SRC_REL_CTRY_NM"));
		account.setSRC_REL_CTRY_CD(rs.getString("SRC_REL_CTRY_CD"));

		account.setREL_NM(rs.getString("REL_NM"));

		account.setREL_NM_TTL_NM(rs.getString("REL_NM_TTL_NM"));

		account.setREL_SUR_NM(rs.getString("REL_SUR_NM"));

		account.setREL_FRST_NM(rs.getString("REL_FRST_NM"));

		account.setREL_MID_NM(rs.getString("REL_MID_NM"));

		account.setREL_NM_SFX_NM(rs.getString("REL_NM_SFX_NM"));

		account.setREL_XTRA_NM(rs.getString("REL_XTRA_NM"));

		account.setREL_TYPE_CD(rs.getString("REL_TYPE_CD"));

		account.setREL_TYPE_CD_DESC_TX(rs.getString("REL_TYPE_CD_DESC_TX"));

		account.setREL_ID_TYPE_CD(rs.getString("REL_ID_TYPE_CD"));

		account.setSRC_SPEC_DATA_AVL_FL(rs.getString("SRC_SPEC_DATA_AVL_FL"));

		account.setSTAT_CD(rs.getString("STAT_CD"));

		account.setOPN_DT(rs.getString("OPN_DT"));

		account.setCLS_DT(rs.getString("CLS_DT"));

		account.setMUNT_CD(rs.getString("MUNT_CD"));

		account.setMUNT_SHNM(rs.getString("MUNT_SHNM"));

		account.setMUNT_LONG_NM(rs.getString("MUNT_LONG_NM"));

		account.setLOB_SEG_CD(rs.getString("LOB_SEG_CD"));

		account.setCTRCT_LE_CD(rs.getString("CTRCT_LE_CD"));

		account.setDSPL_CD(rs.getString("DSPL_CD"));

		account.setSRC_CAT_CD(rs.getString("SRC_CAT_CD"));

		account.setCRT_TS(rs.getString("CRT_TS"));

		account.setUPD_TS(rs.getString("UPD_TS"));
		return account;
	}

}
