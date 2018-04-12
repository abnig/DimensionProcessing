package com.dimframework.workerthread;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dimframework.domain.service.DimensionMetadataConsumerService;

@Component("hashFileMetadataConsumerWorker")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HashFileMetadataConsumerWorker implements Runnable {

	private static Logger logger = Logger.getLogger(HashFileMetadataConsumerWorker.class);

	@Autowired
	private DimensionMetadataConsumerService dimensionMetadataConsumerServiceImpl;

	private CountDownLatch countDownLatch;

	public HashFileMetadataConsumerWorker(CountDownLatch countDownLatch) {
		super();
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		try {
			this.dimensionMetadataConsumerServiceImpl.processHashFileMetadata();
		} catch (InterruptedException | IOException e) {
			if (e.getLocalizedMessage() == null) {
				logger.info(Thread.currentThread().getName() + " terminating normally");
			} else
				logger.error(e.getLocalizedMessage());
		} finally {
			countDownLatch.countDown();
		}

	}

}
