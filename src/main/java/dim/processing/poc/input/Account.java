package dim.processing.poc.input;

public class Account {

	private String OPER_AGMT_CD;
	private String OPER_AGMT_ID;
	private String UNQ_REL_ID;
	private String SRC_REL_ID;
	private String SRC_REL_CD;
	private String SRC_DESC_TX;
	private String SRC_REL_CTRY_NM;
	private String SRC_REL_CTRY_CD;
	private String REL_NM;
	private String REL_NM_TTL_NM;
	private String REL_SUR_NM;
	private String REL_FRST_NM;
	private String REL_MID_NM;
	private String REL_NM_SFX_NM;
	private String REL_XTRA_NM;
	private String REL_TYPE_CD;
	private String REL_TYPE_CD_DESC_TX;
	private String REL_ID_TYPE_CD;
	private String SRC_SPEC_DATA_AVL_FL;
	private String STAT_CD;
	private String OPN_DT;
	private String CLS_DT;
	private String MUNT_CD;
	private String MUNT_SHNM;
	private String MUNT_LONG_NM;
	private String LOB_SEG_CD;
	private String CTRCT_LE_CD;
	private String DSPL_CD;
	private String SRC_CAT_CD;
	private String CRT_TS;
	private String UPD_TS;
	
	public synchronized String hashCodeColumns() {
		String result = org.apache.commons.codec.digest.DigestUtils.sha256Hex(toString()); 
		return result;
	}	
	
	
	@Override
	public String toString() {
		return "Account [OPER_AGMT_CD=" + OPER_AGMT_CD + ", OPER_AGMT_ID=" + OPER_AGMT_ID + ", UNQ_REL_ID=" + UNQ_REL_ID
				+ ", SRC_DESC_TX=" + SRC_DESC_TX + ", SRC_REL_CTRY_NM=" + SRC_REL_CTRY_NM + ", SRC_REL_CTRY_CD="
				+ SRC_REL_CTRY_CD + ", REL_NM=" + REL_NM + ", REL_NM_TTL_NM=" + REL_NM_TTL_NM + ", REL_SUR_NM="
				+ REL_SUR_NM + ", REL_FRST_NM=" + REL_FRST_NM + ", REL_MID_NM=" + REL_MID_NM + ", REL_NM_SFX_NM="
				+ REL_NM_SFX_NM + ", REL_XTRA_NM=" + REL_XTRA_NM + ", REL_TYPE_CD=" + REL_TYPE_CD
				+ ", REL_TYPE_CD_DESC_TX=" + REL_TYPE_CD_DESC_TX + ", REL_ID_TYPE_CD=" + REL_ID_TYPE_CD
				+ ", SRC_SPEC_DATA_AVL_FL=" + SRC_SPEC_DATA_AVL_FL + ", STAT_CD=" + STAT_CD + ", OPN_DT=" + OPN_DT
				+ ", CLS_DT=" + CLS_DT + ", MUNT_CD=" + MUNT_CD + ", MUNT_SHNM=" + MUNT_SHNM + ", MUNT_LONG_NM="
				+ MUNT_LONG_NM + ", LOB_SEG_CD=" + LOB_SEG_CD + ", CTRCT_LE_CD=" + CTRCT_LE_CD + ", DSPL_CD=" + DSPL_CD
				+ ", SRC_CAT_CD=" + SRC_CAT_CD + "]";
	}

	public synchronized String hashCodePK() {
		String result = org.apache.commons.codec.digest.DigestUtils.sha256Hex(OPER_AGMT_CD.concat(OPER_AGMT_ID)); 
		return result;
	}


	@Override
	public synchronized boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (OPER_AGMT_CD == null) {
			if (other.OPER_AGMT_CD != null)
				return false;
		} else if (!OPER_AGMT_CD.equals(other.OPER_AGMT_CD))
			return false;
		if (OPER_AGMT_ID == null) {
			if (other.OPER_AGMT_ID != null)
				return false;
		} else if (!OPER_AGMT_ID.equals(other.OPER_AGMT_ID))
			return false;
		return true;
	}


	public String getOPER_AGMT_CD() {
		return OPER_AGMT_CD;
	}


	public String getOPER_AGMT_ID() {
		return OPER_AGMT_ID;
	}


	public String getUNQ_REL_ID() {
		return UNQ_REL_ID;
	}


	public String getSRC_REL_ID() {
		return SRC_REL_ID;
	}


	public String getSRC_REL_CD() {
		return SRC_REL_CD;
	}


	public String getSRC_DESC_TX() {
		return SRC_DESC_TX;
	}


	public String getSRC_REL_CTRY_NM() {
		return SRC_REL_CTRY_NM;
	}


	public String getSRC_REL_CTRY_CD() {
		return SRC_REL_CTRY_CD;
	}


	public String getREL_NM() {
		return REL_NM;
	}


	public String getREL_NM_TTL_NM() {
		return REL_NM_TTL_NM;
	}


	public String getREL_SUR_NM() {
		return REL_SUR_NM;
	}


	public String getREL_FRST_NM() {
		return REL_FRST_NM;
	}


	public String getREL_MID_NM() {
		return REL_MID_NM;
	}


	public String getREL_NM_SFX_NM() {
		return REL_NM_SFX_NM;
	}


	public String getREL_XTRA_NM() {
		return REL_XTRA_NM;
	}


	public String getREL_TYPE_CD() {
		return REL_TYPE_CD;
	}


	public String getREL_TYPE_CD_DESC_TX() {
		return REL_TYPE_CD_DESC_TX;
	}


	public String getREL_ID_TYPE_CD() {
		return REL_ID_TYPE_CD;
	}


	public String getSRC_SPEC_DATA_AVL_FL() {
		return SRC_SPEC_DATA_AVL_FL;
	}


	public String getSTAT_CD() {
		return STAT_CD;
	}


	public String getOPN_DT() {
		return OPN_DT;
	}


	public String getCLS_DT() {
		return CLS_DT;
	}


	public String getMUNT_CD() {
		return MUNT_CD;
	}


	public String getMUNT_SHNM() {
		return MUNT_SHNM;
	}


	public String getMUNT_LONG_NM() {
		return MUNT_LONG_NM;
	}


	public String getLOB_SEG_CD() {
		return LOB_SEG_CD;
	}


	public String getCTRCT_LE_CD() {
		return CTRCT_LE_CD;
	}


	public String getDSPL_CD() {
		return DSPL_CD;
	}


	public String getSRC_CAT_CD() {
		return SRC_CAT_CD;
	}


	public String getCRT_TS() {
		return CRT_TS;
	}


	public String getUPD_TS() {
		return UPD_TS;
	}


	public void setOPER_AGMT_CD(String oPER_AGMT_CD) {
		OPER_AGMT_CD = oPER_AGMT_CD;
	}


	public void setOPER_AGMT_ID(String oPER_AGMT_ID) {
		OPER_AGMT_ID = oPER_AGMT_ID;
	}


	public void setUNQ_REL_ID(String uNQ_REL_ID) {
		UNQ_REL_ID = uNQ_REL_ID;
	}


	public void setSRC_REL_ID(String sRC_REL_ID) {
		SRC_REL_ID = sRC_REL_ID;
	}


	public void setSRC_REL_CD(String sRC_REL_CD) {
		SRC_REL_CD = sRC_REL_CD;
	}


	public void setSRC_DESC_TX(String sRC_DESC_TX) {
		SRC_DESC_TX = sRC_DESC_TX;
	}


	public void setSRC_REL_CTRY_NM(String sRC_REL_CTRY_NM) {
		SRC_REL_CTRY_NM = sRC_REL_CTRY_NM;
	}


	public void setSRC_REL_CTRY_CD(String sRC_REL_CTRY_CD) {
		SRC_REL_CTRY_CD = sRC_REL_CTRY_CD;
	}


	public void setREL_NM(String rEL_NM) {
		REL_NM = rEL_NM;
	}


	public void setREL_NM_TTL_NM(String rEL_NM_TTL_NM) {
		REL_NM_TTL_NM = rEL_NM_TTL_NM;
	}


	public void setREL_SUR_NM(String rEL_SUR_NM) {
		REL_SUR_NM = rEL_SUR_NM;
	}


	public void setREL_FRST_NM(String rEL_FRST_NM) {
		REL_FRST_NM = rEL_FRST_NM;
	}


	public void setREL_MID_NM(String rEL_MID_NM) {
		REL_MID_NM = rEL_MID_NM;
	}


	public void setREL_NM_SFX_NM(String rEL_NM_SFX_NM) {
		REL_NM_SFX_NM = rEL_NM_SFX_NM;
	}


	public void setREL_XTRA_NM(String rEL_XTRA_NM) {
		REL_XTRA_NM = rEL_XTRA_NM;
	}


	public void setREL_TYPE_CD(String rEL_TYPE_CD) {
		REL_TYPE_CD = rEL_TYPE_CD;
	}


	public void setREL_TYPE_CD_DESC_TX(String rEL_TYPE_CD_DESC_TX) {
		REL_TYPE_CD_DESC_TX = rEL_TYPE_CD_DESC_TX;
	}


	public void setREL_ID_TYPE_CD(String rEL_ID_TYPE_CD) {
		REL_ID_TYPE_CD = rEL_ID_TYPE_CD;
	}


	public void setSRC_SPEC_DATA_AVL_FL(String sRC_SPEC_DATA_AVL_FL) {
		SRC_SPEC_DATA_AVL_FL = sRC_SPEC_DATA_AVL_FL;
	}


	public void setSTAT_CD(String sTAT_CD) {
		STAT_CD = sTAT_CD;
	}


	public void setOPN_DT(String oPN_DT) {
		OPN_DT = oPN_DT;
	}


	public void setCLS_DT(String cLS_DT) {
		CLS_DT = cLS_DT;
	}


	public void setMUNT_CD(String mUNT_CD) {
		MUNT_CD = mUNT_CD;
	}


	public void setMUNT_SHNM(String mUNT_SHNM) {
		MUNT_SHNM = mUNT_SHNM;
	}


	public void setMUNT_LONG_NM(String mUNT_LONG_NM) {
		MUNT_LONG_NM = mUNT_LONG_NM;
	}


	public void setLOB_SEG_CD(String lOB_SEG_CD) {
		LOB_SEG_CD = lOB_SEG_CD;
	}


	public void setCTRCT_LE_CD(String cTRCT_LE_CD) {
		CTRCT_LE_CD = cTRCT_LE_CD;
	}


	public void setDSPL_CD(String dSPL_CD) {
		DSPL_CD = dSPL_CD;
	}


	public void setSRC_CAT_CD(String sRC_CAT_CD) {
		SRC_CAT_CD = sRC_CAT_CD;
	}


	public void setCRT_TS(String cRT_TS) {
		CRT_TS = cRT_TS;
	}


	public void setUPD_TS(String uPD_TS) {
		UPD_TS = uPD_TS;
	}
	
	

}
