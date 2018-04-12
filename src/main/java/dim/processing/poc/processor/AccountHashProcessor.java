package dim.processing.poc.processor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import dim.processing.poc.input.AccountHash;
import dim.processing.poc.output.AccountDimensionHash;

public class AccountHashProcessor implements ItemProcessor<AccountHash, AccountDimensionHash> {

	@Override
	public AccountDimensionHash process(AccountHash accountHash) throws Exception {
		AccountDimensionHash accountDimensionHash = new AccountDimensionHash(accountHash);
		accountDimensionHash.setCRT_TS(new Date().toString());
		accountDimensionHash.setCRT_USER_ID("SYS");
		accountDimensionHash.setUPD_TS(new Date().toString());
		accountDimensionHash.setUPD_USER_ID("SYS");
		accountDimensionHash.setEFF_STRT_DT(new Date());
		accountDimensionHash.setEFF_END_DT(Timestamp.from(LocalDate.of(2999, Month.DECEMBER, 31).atStartOfDay().atZone(ZoneId.of("-05:00")).toInstant()));
		accountDimensionHash.setIS_ACTV_FL("Y");
		return accountDimensionHash;
	}

}
