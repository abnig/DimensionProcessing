package com.dimframework.database.support;

import org.jboss.logging.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component("dimensionProcessingStepExecutionListener")
public class DimensionProcessingStepExecutionListener implements StepExecutionListener {
	
	private static Logger logger = Logger.getLogger(DimensionProcessingStepExecutionListener.class);

	@Override
	public void beforeStep(StepExecution stepExecution) {

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("ReadCount: " + stepExecution.getReadCount() + "; ProcessSkipCount: " + stepExecution.getProcessSkipCount() + "; WriteCount: " + stepExecution.getWriteCount());		
		return stepExecution.getExitStatus();
	}

}
