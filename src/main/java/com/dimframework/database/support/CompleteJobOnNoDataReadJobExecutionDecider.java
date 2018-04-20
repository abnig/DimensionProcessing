package com.dimframework.database.support;

import org.jboss.logging.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

@Component("completeJobOnNoDataReadJobExecutionDecider")
public class CompleteJobOnNoDataReadJobExecutionDecider implements JobExecutionDecider {

	private static Logger logger = Logger.getLogger(CompleteJobOnNoDataReadJobExecutionDecider.class);

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		if (stepExecution.getReadCount() == 0) {
			logger.info("No data read; Step: " + stepExecution.getStepName() + "; job: " + jobExecution.getJobInstance().getJobName());
			return FlowExecutionStatus.COMPLETED;
		}
		return new FlowExecutionStatus("CONTINUE");
	}
}