package com.dimframework.database.support;

import javax.annotation.Resource;

import org.jboss.logging.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dimframework.domain.CompositeUpdateDTO;

@Component("updateProcessItemProcessor")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UpdateProcessItemProcessor implements ItemProcessor<String, CompositeUpdateDTO> {
	
	@Resource
	@Qualifier("fieldDelimiter")
	private String fieldDelimiter;

	@Resource
	@Qualifier("recordTerminator")
	private String recordTerminator;
	
	private static Logger logger = Logger.getLogger(UpdateProcessItemProcessor.class);

	@Override
	public CompositeUpdateDTO process(String item) throws Exception {
		logger.info("Recieved Row: " + item);
		String[] rowArray = item.split("\\" + fieldDelimiter);
		String hashPK = rowArray[rowArray.length - 2];
		logger.info("HASHPK for update : " + hashPK);		
		return new CompositeUpdateDTO(hashPK, item);
	}

}
