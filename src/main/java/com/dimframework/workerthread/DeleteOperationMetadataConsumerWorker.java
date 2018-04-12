package com.dimframework.workerthread;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dimframework.domain.service.DimensionMetadataConsumerService;

@Component("deleteOperationMetadataConsumerWorker")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeleteOperationMetadataConsumerWorker implements Runnable {

	private static Logger logger = Logger.getLogger(DeleteOperationMetadataConsumerWorker.class);

	@Autowired
	private DimensionMetadataConsumerService dimensionMetadataConsumerServiceImpl;

	private CountDownLatch countDownLatch;

	public DeleteOperationMetadataConsumerWorker(CountDownLatch countDownLatch) {
		super();
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		try {
			this.dimensionMetadataConsumerServiceImpl.processDeleteOperation();
		} catch (InterruptedException | JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
			if (e.getLocalizedMessage() == null) {
				logger.info(Thread.currentThread().getName() + " terminating normally");
			} else
				logger.error(e.getLocalizedMessage());
		} finally {
			countDownLatch.countDown();
		}

	}

}
