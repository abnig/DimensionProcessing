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
// 7|abhinav|666|Jersey|architect-super|7902699be42c8a8e46fbbb4501726517e86b22c56a189f7625a6da49081b2451|1b3677d7f55aa2501230403c8346a56c57c594f05e8e168ced4856e3b1f470be|0edc533705fb5e03be490e84ad55454f1cfe9473100b1f371a75646b30c4798a|

	@Override
	public CompositeUpdateDTO process(String item) throws Exception {
		logger.info("Recieved Row: " + item);
		String[] rowArray = item.split("\\" + fieldDelimiter);
		String hashPK = rowArray[rowArray.length - 3];
		String hashColOld = rowArray[rowArray.length - 2];
		String hashColNew = rowArray[rowArray.length - 1];
		logger.info("HASHPK for update : " + hashPK);
		logger.info("HASHColOld for update : " + hashColOld);
		logger.info("HASHColNew for update : " + hashColNew);
		String newItem = item.replace(hashColNew + "|", "");
		logger.info("row data after 1st replace : " + newItem);
		String finalRowData = newItem.replaceAll(hashColOld, hashColNew);
		logger.info("row data sent to writer : " + finalRowData);
		return new CompositeUpdateDTO(hashPK, finalRowData);
	}

}
