package dim.processing.poc.writer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import dim.processing.poc.output.AccountDimensionHash;

@Component("accountDimensionHashUpdateWriter")
@Scope(value = "step")
public class AccountDimensionHashUpdateWriter implements ItemWriter<List<AccountDimensionHash>> {
	
	@Autowired
	private JdbcBatchItemWriter<AccountDimensionHash> updateDimensionHashJdbcItemWriter;

	@Autowired
	private JdbcBatchItemWriter<AccountDimensionHash> accountDimensionHashJdbcItemWriter;

	@Override
	public void write(List<? extends List<AccountDimensionHash>> items) throws Exception {
		for (List<AccountDimensionHash> dim : items) {
			for (AccountDimensionHash dimHash : dim) {
				if ("N".equals(dimHash.getIS_ACTV_FL())) {
					List<AccountDimensionHash> updateItem = new ArrayList<AccountDimensionHash>();
					updateItem.add(dimHash);
					updateDimensionHashJdbcItemWriter.write(updateItem);
				} else {
					List<AccountDimensionHash> updateItem = new ArrayList<AccountDimensionHash>();
					updateItem.add(dimHash);
					accountDimensionHashJdbcItemWriter.write(updateItem);
				}

			}
		}
		
	}



}
