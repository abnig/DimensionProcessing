package dim.processing.poc.writer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import dim.processing.poc.output.FicNodeDimensionHash;

@Component("ficNodeDimensionHashUpdateWriter")
@Scope(value = "step")
public class FicNodeDimensionHashUpdateWriter implements ItemWriter<List<FicNodeDimensionHash>> {

	@Autowired
	private JdbcBatchItemWriter<FicNodeDimensionHash> updateDimensionHashJdbcItemWriter;

	@Autowired
	private JdbcBatchItemWriter<FicNodeDimensionHash> ficNodeDimensionHashJdbcItemWriter;

	@Override
	public void write(List<? extends List<FicNodeDimensionHash>> items) throws Exception {
		for (List<FicNodeDimensionHash> dim : items) {
			for (FicNodeDimensionHash dimHash : dim) {
				if ("N".equals(dimHash.getIsActiveFlag())) {
					List<FicNodeDimensionHash> updateItem = new ArrayList<FicNodeDimensionHash>();
					updateItem.add(dimHash);
					updateDimensionHashJdbcItemWriter.write(updateItem);
				} else {
					List<FicNodeDimensionHash> updateItem = new ArrayList<FicNodeDimensionHash>();
					updateItem.add(dimHash);
					ficNodeDimensionHashJdbcItemWriter.write(updateItem);
				}

			}
		}

	}

}
