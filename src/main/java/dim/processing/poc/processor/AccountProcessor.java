package dim.processing.poc.processor;

import org.springframework.batch.item.ItemProcessor;

import dim.processing.poc.input.Account;
import dim.processing.poc.input.AccountHash;

public class AccountProcessor implements ItemProcessor<Account, AccountHash> {

	@Override
	public AccountHash process(Account account) throws Exception {
		AccountHash accountHash = new AccountHash(account);
		return accountHash;
	}

}
