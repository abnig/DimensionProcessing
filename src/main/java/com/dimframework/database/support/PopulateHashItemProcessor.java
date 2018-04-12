package com.dimframework.database.support;

import org.jboss.logging.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dimframework.domain.service.DimensionMetadataService;

@Component("populateHashItemProcessor")
public class PopulateHashItemProcessor implements ItemProcessor<String, String> {
	
	@Autowired
	private DimensionMetadataService dimensionMetadataDaoServiceImpl; 
	
	private static Logger logger = Logger.getLogger(PopulateHashItemProcessor.class); 

	@Override
	public String process(String item) throws Exception {
		logger.debug("row data from reader: " + item);
		String data = this.dimensionMetadataDaoServiceImpl.concatenatePKHashAndDataColumnHash(item);
		logger.debug("row data after hashing: " + data);
		return data;
	}


}
