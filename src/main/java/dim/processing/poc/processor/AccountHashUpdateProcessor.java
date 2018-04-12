package dim.processing.poc.processor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import dim.processing.poc.input.AccountHash;
import dim.processing.poc.output.AccountDimensionHash;

@Component("accountHashUpdateProcessor")
@Scope(value = "step")
public class AccountHashUpdateProcessor implements ItemProcessor<AccountHash, List<AccountDimensionHash>>{

	@Override
	public List<AccountDimensionHash> process(AccountHash accountHash) throws Exception {
		List<AccountDimensionHash> list = new ArrayList<>();
		AccountDimensionHash ficNodeDimensionHash = new AccountDimensionHash(accountHash);
		ficNodeDimensionHash.setCRT_TS(new Date().toString());
		ficNodeDimensionHash.setCRT_USER_ID("SYS");
		ficNodeDimensionHash.setUPD_TS(new Date().toString());
		ficNodeDimensionHash.setUPD_USER_ID("SYS");
		ficNodeDimensionHash.setEFF_END_DT(new Timestamp(new Date().getTime()));
		ficNodeDimensionHash.setIS_ACTV_FL("N");
		list.add(ficNodeDimensionHash);
		ficNodeDimensionHash = new AccountDimensionHash(accountHash);
		ficNodeDimensionHash.setCRT_TS(new Date().toString());
		ficNodeDimensionHash.setCRT_USER_ID("SYS");
		ficNodeDimensionHash.setUPD_TS(new Date().toString());
		ficNodeDimensionHash.setUPD_USER_ID("SYS");
		ficNodeDimensionHash.setEFF_STRT_DT(new Date());
		ficNodeDimensionHash.setEFF_END_DT(Timestamp.from(LocalDate.of(2999, Month.DECEMBER, 31).atStartOfDay().atZone(ZoneId.of("-05:00")).toInstant()));
		ficNodeDimensionHash.setIS_ACTV_FL("Y");
		list.add(ficNodeDimensionHash);
		return list;
	}

}
