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

@Component("dimensionMetadataConsumerWorker")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DimensionMetadataConsumerWorker implements Runnable {

	private static Logger logger = Logger.getLogger(DimensionMetadataConsumerWorker.class);

	@Autowired
	private DimensionMetadataConsumerService dimensionMetadataConsumerServiceImpl;

	private CountDownLatch countDownLatch;

	public DimensionMetadataConsumerWorker(CountDownLatch countDownLatch) {
		super();
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		try {
			this.dimensionMetadataConsumerServiceImpl.processDimensionMetadata();
		} catch (InterruptedException e) {
			if (e.getLocalizedMessage() == null) {
				logger.info(Thread.currentThread().getName() + " terminating normally");
			} else
				logger.error(e.getLocalizedMessage());
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			countDownLatch.countDown();
		}

	}

}
