package com.dimframework.database.support;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;

import com.dimframework.domain.CompositeUpdateDTO;

public class UpdateOperationCompositeWriter implements ItemWriter<CompositeUpdateDTO>, ItemStreamWriter<CompositeUpdateDTO> {
	
	private FlatFileItemWriter<String> insertWriter;
	
	private ItemWriter<String> updateWriter;
	
	public UpdateOperationCompositeWriter(FlatFileItemWriter<String> insertWriter, ItemWriter<String> updateWriter) {
		super();
		this.insertWriter = insertWriter;
		this.updateWriter = updateWriter;
	}

	@Override
	public void write(List<? extends CompositeUpdateDTO> items) throws Exception {
		this.insertWriter.write(items.stream().map(u -> u.getInsertRowData()).collect(Collectors.toList()));
		this.updateWriter.write(items.stream().map(u -> u.getHashPK()).collect(Collectors.toList()));
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		this.insertWriter.open(executionContext);
		
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {

	}

	@Override
	public void close() throws ItemStreamException {
		this.insertWriter.close();
	}

}
