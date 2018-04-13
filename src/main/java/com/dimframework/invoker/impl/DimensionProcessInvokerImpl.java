package com.dimframework.invoker.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.HashFileMetadata;
import com.dimframework.domain.service.impl.DimensionMetadataDaoServiceImpl;
import com.dimframework.invoker.DimensionProcessInvoker;
import com.dimframework.workerthread.DeleteOperationMetadataConsumerWorker;
import com.dimframework.workerthread.DimensionMetadataConsumerWorker;
import com.dimframework.workerthread.HashFileMetadataConsumerWorker;

@Service("dimensionProcessInvokerImpl")
public class DimensionProcessInvokerImpl implements DimensionProcessInvoker {

	private static Logger logger = Logger.getLogger(DimensionProcessInvokerImpl.class);

	@Autowired
	private DimensionMetadataDaoServiceImpl dimensionMetadataDaoServiceImpl;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ExecutorService dimensionProcessingExecutorService;

	@Resource
	private BlockingQueue<DimensionMetadata> dimensionMetadataBlockingQueue;
	
	@Resource
	private BlockingQueue<HashFileMetadata>	hashFileMetadataBlockingQueue;

	@Override
	public void invoker(String domainName, Date effectiveStartDate, Date effectiveEndDate ){
		List<DimensionMetadata> list = this.dimensionMetadataDaoServiceImpl.getByDomainName(domainName, effectiveStartDate, effectiveEndDate);
		this.pushToDimensionMetadataBlockingQueue(list);
		this.callPerformDimensionProcessing(list, domainName);
		this.callProcessHashFile(list, domainName);
		this.callPerformDeleteOperation(list, domainName);
	}

	private void pushToDimensionMetadataBlockingQueue(List<DimensionMetadata> list) {
		logger.info("Number of table for dimension generation " + list.size());
		this.dimensionMetadataBlockingQueue.addAll(list);
	}

	private void callPerformDimensionProcessing(List<DimensionMetadata> list, String domainName) {
		CountDownLatch countDownLatch = (CountDownLatch) this.applicationContext.getBean("countDownLatch", list.size());
		for (int i = 0; i < list.size(); i++) {
			DimensionMetadataConsumerWorker workerThread = (DimensionMetadataConsumerWorker) this.applicationContext
					.getBean("dimensionMetadataConsumerWorker", countDownLatch);
			this.dimensionProcessingExecutorService.execute(workerThread);
		}
	}
	
	private void callProcessHashFile(List<DimensionMetadata> list, String domainName) {
		CountDownLatch countDownLatch = (CountDownLatch) this.applicationContext.getBean("countDownLatch", list.size());
		for (int i = 0; i < list.size(); i++) {
			HashFileMetadataConsumerWorker workerThread = (HashFileMetadataConsumerWorker) this.applicationContext
					.getBean("hashFileMetadataConsumerWorker", countDownLatch);
			this.dimensionProcessingExecutorService.execute(workerThread);
		}
	}

	private void callPerformDeleteOperation(List<DimensionMetadata> list, String domainName) {
		CountDownLatch countDownLatch = (CountDownLatch) this.applicationContext.getBean("countDownLatch", list.size());
		for (int i = 0; i < list.size(); i++) {
			DeleteOperationMetadataConsumerWorker workerThread = (DeleteOperationMetadataConsumerWorker) this.applicationContext
					.getBean("deleteOperationMetadataConsumerWorker", countDownLatch);
			this.dimensionProcessingExecutorService.execute(workerThread);
		}
	}

}
