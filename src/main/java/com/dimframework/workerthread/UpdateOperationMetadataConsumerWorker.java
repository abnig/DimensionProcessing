package com.dimframework.workerthread;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dimframework.domain.service.DimensionMetadataConsumerService;

@Component("updateOperationMetadataConsumerWorker")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UpdateOperationMetadataConsumerWorker implements Runnable {

	private static Logger logger = Logger.getLogger(UpdateOperationMetadataConsumerWorker.class);

	@Autowired
	private DimensionMetadataConsumerService dimensionMetadataConsumerServiceImpl;

	private CountDownLatch countDownLatch;

	public UpdateOperationMetadataConsumerWorker(CountDownLatch countDownLatch) {
		super();
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		try {
			this.dimensionMetadataConsumerServiceImpl.processUpdateOperation();
		} catch (InterruptedException | JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | IOException e) {
			if (e.getLocalizedMessage() == null) {
				logger.info(Thread.currentThread().getName() + " terminating normally");
			} else
				logger.error(e.getLocalizedMessage());
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			countDownLatch.countDown();
		}


	}

}
