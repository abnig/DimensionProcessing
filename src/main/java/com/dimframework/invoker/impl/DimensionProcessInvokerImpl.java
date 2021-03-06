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
import com.dimframework.domain.service.impl.DimensionMetadataDaoServiceImpl;
import com.dimframework.invoker.DimensionProcessInvoker;
import com.dimframework.workerthread.DeleteOperationMetadataConsumerWorker;
import com.dimframework.workerthread.DimensionMetadataConsumerWorker;
import com.dimframework.workerthread.InsertOperationMetadataConsumerWorker;

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
	
	@Override
	public void invoker(String domainName, Date effectiveStartDate, Date effectiveEndDate ){
		List<DimensionMetadata> list = this.dimensionMetadataDaoServiceImpl.getByDomainName(domainName, effectiveStartDate, effectiveEndDate);
		this.pushToDimensionMetadataBlockingQueue(list);
		this.callPerformDimensionProcessing(list, domainName);
		this.callPerformDeleteAndUpdateOperation(list, domainName);
	//	this.callPerformInsertOperation(list, domainName);
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

	private void callPerformDeleteAndUpdateOperation(List<DimensionMetadata> list, String domainName) {
		CountDownLatch countDownLatch = (CountDownLatch) this.applicationContext.getBean("countDownLatch", list.size());
		for (int i = 0; i < list.size(); i++) {
			DeleteOperationMetadataConsumerWorker workerThread = (DeleteOperationMetadataConsumerWorker) this.applicationContext
					.getBean("deleteOperationMetadataConsumerWorker", countDownLatch);
			this.dimensionProcessingExecutorService.execute(workerThread);
		}
	}
	
	/*
	private void callPerformUpdateOperation(List<DimensionMetadata> list, String domainName) {
		CountDownLatch countDownLatch = (CountDownLatch) this.applicationContext.getBean("countDownLatch", list.size());
		for (int i = 0; i < list.size(); i++) {
			UpdateOperationMetadataConsumerWorker workerThread = (UpdateOperationMetadataConsumerWorker) this.applicationContext
					.getBean("updateOperationMetadataConsumerWorker", countDownLatch);
			this.dimensionProcessingExecutorService.execute(workerThread);
		}
	}
	
	
	private void callPerformInsertOperation(List<DimensionMetadata> list, String domainName) {
		CountDownLatch countDownLatch = (CountDownLatch) this.applicationContext.getBean("countDownLatch", list.size());
		for (int i = 0; i < list.size(); i++) {
			InsertOperationMetadataConsumerWorker workerThread = (InsertOperationMetadataConsumerWorker) this.applicationContext
					.getBean("insertOperationMetadataConsumerWorker", countDownLatch);
			this.dimensionProcessingExecutorService.execute(workerThread);
		}
	}
*/
}
