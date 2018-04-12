package dim.processing.poc.input;

public class AccountHash extends Account {

	private String hashPK;

	private String hashColumns;

	public AccountHash() {
		super();
	}

	public AccountHash(AccountHash accountHash) {

		super.setOPER_AGMT_CD(accountHash.getOPER_AGMT_CD());
		super.setOPER_AGMT_ID(accountHash.getOPER_AGMT_ID());
		super.setUNQ_REL_ID(accountHash.getUNQ_REL_ID());
		super.setSRC_REL_ID(accountHash.getSRC_REL_ID());
		super.setSRC_REL_CD(accountHash.getSRC_REL_CD());
		super.setSRC_DESC_TX(accountHash.getSRC_DESC_TX());
		super.setSRC_REL_CTRY_NM(accountHash.getSRC_REL_CTRY_NM());
		super.setSRC_REL_CTRY_CD(accountHash.getSRC_REL_CTRY_CD());
		super.setREL_NM(accountHash.getREL_NM());
		super.setREL_NM_TTL_NM(accountHash.getREL_NM_TTL_NM());
		super.setREL_SUR_NM(accountHash.getREL_SUR_NM());
		super.setREL_FRST_NM(accountHash.getREL_FRST_NM());
		super.setREL_MID_NM(accountHash.getREL_MID_NM());
		super.setREL_NM_SFX_NM(accountHash.getREL_NM_SFX_NM());
		super.setREL_XTRA_NM(accountHash.getREL_XTRA_NM());
		super.setREL_TYPE_CD(accountHash.getREL_TYPE_CD());
		super.setREL_TYPE_CD_DESC_TX(accountHash.getREL_TYPE_CD_DESC_TX());
		super.setREL_ID_TYPE_CD(accountHash.getREL_ID_TYPE_CD());
		super.setSRC_SPEC_DATA_AVL_FL(accountHash.getSRC_SPEC_DATA_AVL_FL());
		super.setSTAT_CD(accountHash.getSTAT_CD());
		super.setOPN_DT(accountHash.getOPN_DT());
		super.setCLS_DT(accountHash.getCLS_DT());
		super.setMUNT_CD(accountHash.getMUNT_CD());
		super.setMUNT_SHNM(accountHash.getMUNT_SHNM());
		super.setMUNT_LONG_NM(accountHash.getMUNT_LONG_NM());
		super.setLOB_SEG_CD(accountHash.getLOB_SEG_CD());
		super.setCTRCT_LE_CD(accountHash.getCTRCT_LE_CD());
		super.setDSPL_CD(accountHash.getDSPL_CD());
		super.setSRC_CAT_CD(accountHash.getSRC_CAT_CD());
		super.setCRT_TS(accountHash.getCRT_TS());
		super.setUPD_TS(accountHash.getUPD_TS());
		this.setHashPK(accountHash.hashCodePK());
		this.setHashColumns(accountHash.hashCodeColumns());

	}

	public AccountHash(Account account) {
		super.setOPER_AGMT_CD(account.getOPER_AGMT_CD());
		super.setOPER_AGMT_ID(account.getOPER_AGMT_ID());
		super.setUNQ_REL_ID(account.getUNQ_REL_ID());
		super.setSRC_REL_ID(account.getSRC_REL_ID());
		super.setSRC_REL_CD(account.getSRC_REL_CD());
		super.setSRC_DESC_TX(account.getSRC_DESC_TX());
		super.setSRC_REL_CTRY_NM(account.getSRC_REL_CTRY_NM());
		super.setSRC_REL_CTRY_CD(account.getSRC_REL_CTRY_CD());

		super.setREL_NM(account.getREL_NM());

		super.setREL_NM_TTL_NM(account.getREL_NM_TTL_NM());

		super.setREL_SUR_NM(account.getREL_SUR_NM());

		super.setREL_FRST_NM(account.getREL_FRST_NM());

		super.setREL_MID_NM(account.getREL_MID_NM());

		super.setREL_NM_SFX_NM(account.getREL_NM_SFX_NM());

		super.setREL_XTRA_NM(account.getREL_XTRA_NM());

		super.setREL_TYPE_CD(account.getREL_TYPE_CD());

		super.setREL_TYPE_CD_DESC_TX(account.getREL_TYPE_CD_DESC_TX());

		super.setREL_ID_TYPE_CD(account.getREL_ID_TYPE_CD());

		super.setSRC_SPEC_DATA_AVL_FL(account.getSRC_SPEC_DATA_AVL_FL());

		super.setSTAT_CD(account.getSTAT_CD());

		super.setOPN_DT(account.getOPN_DT());

		super.setCLS_DT(account.getCLS_DT());

		super.setMUNT_CD(account.getMUNT_CD());

		super.setMUNT_SHNM(account.getMUNT_SHNM());

		super.setMUNT_LONG_NM(account.getMUNT_LONG_NM());

		super.setLOB_SEG_CD(account.getLOB_SEG_CD());

		super.setCTRCT_LE_CD(account.getCTRCT_LE_CD());

		super.setDSPL_CD(account.getDSPL_CD());

		super.setSRC_CAT_CD(account.getSRC_CAT_CD());

		super.setCRT_TS(account.getCRT_TS());

		super.setUPD_TS(account.getUPD_TS());

		this.setHashPK(account.hashCodePK());

		this.setHashColumns(account.hashCodeColumns());

	}

	public String getHashPK() {
		return hashPK;
	}

	public String getHashColumns() {
		return hashColumns;
	}

	public void setHashPK(String hashPK) {
		this.hashPK = hashPK;
	}

	public void setHashColumns(String hashColumns) {
		this.hashColumns = hashColumns;
	}
}