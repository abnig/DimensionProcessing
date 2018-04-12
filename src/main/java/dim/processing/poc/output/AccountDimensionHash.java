package dim.processing.poc.output;

import java.sql.Timestamp;
import java.util.Date;

import dim.processing.poc.input.Account;
import dim.processing.poc.input.AccountHash;

public class AccountDimensionHash extends AccountHash {
	
	
	private Integer ACC_SK;
	private Date EFF_STRT_DT;
	private Timestamp EFF_END_DT;
	private String IS_ACTV_FL;
	private String CRT_USER_ID;
	private String UPD_USER_ID;
	
	public AccountDimensionHash() {
	}
	
	public AccountDimensionHash(Account account) {
		this(new AccountHash(account));
	}
	
	public AccountDimensionHash(AccountHash accountHash) {
		super(accountHash);
		
	}
	
	public Integer getACC_SK() {
		return ACC_SK;
	}

	public Date getEFF_STRT_DT() {
		return EFF_STRT_DT;
	}

	public Timestamp getEFF_END_DT() {
		return EFF_END_DT;
	}

	public String getIS_ACTV_FL() {
		return IS_ACTV_FL;
	}

	public void setACC_SK(Integer aCC_SK) {
		ACC_SK = aCC_SK;
	}

	public void setEFF_STRT_DT(Date eFF_STRT_DT) {
		EFF_STRT_DT = eFF_STRT_DT;
	}

	public void setEFF_END_DT(Timestamp eFF_END_DT) {
		EFF_END_DT = eFF_END_DT;
	}

	public void setIS_ACTV_FL(String iS_ACTV_FL) {
		IS_ACTV_FL = iS_ACTV_FL;
	}

	public String getCRT_USER_ID() {
		return CRT_USER_ID;
	}

	public String getUPD_USER_ID() {
		return UPD_USER_ID;
	}

	public void setCRT_USER_ID(String cRT_USER_ID) {
		CRT_USER_ID = cRT_USER_ID;
	}

	public void setUPD_USER_ID(String uPD_USER_ID) {
		UPD_USER_ID = uPD_USER_ID;
	}
	
}
