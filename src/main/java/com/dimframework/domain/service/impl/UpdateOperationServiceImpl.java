package com.dimframework.domain.service.impl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.stereotype.Component;

import com.dimframework.domain.UpdateOperationMetadata;
import com.dimframework.domain.service.UpdateOperationService;

@Component("updateOperationServiceImpl")
public class UpdateOperationServiceImpl extends AbstractUpdateOperationMetadataService
		implements UpdateOperationService, InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
		beanDefinitionRegistry = (BeanDefinitionRegistry) autowireCapableBeanFactory;
		this.jobBuilderFactory = new JobBuilderFactory(this.jobRepository.getObject());
		this.stepBuilderFactory = new StepBuilderFactory(this.jobRepository.getObject(), this.transactionManager);
	}

	@Override
	public Job instantiateUpdateOperationBatchJob(UpdateOperationMetadata updateOperationMetadata) throws BeansException {

		String name = "UpdateOperation_" + updateOperationMetadata.getDimensionMetadata().getSourceTable() + "_Job_"
				+ updateOperationMetadata.getProcessId();
		try {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("InsertOperationFlow");
		
	    Flow flow = flowBuilder
		        .start(super.createUpdateOperationStep(updateOperationMetadata))
		        .next(completeJobOnNoDataReadJobExecutionDecider)
		        .on("CONTINUE")
		        .to(super.createInsertIntoDimensionStep(updateOperationMetadata))
		        .from(completeJobOnNoDataReadJobExecutionDecider)
		        .on(FlowExecutionStatus.COMPLETED.getName())
		        .end()
		        .build();
	    
		return jobBuilderFactory.get(name)
				.repository(jobRepository.getObject())
				.incrementer(new RunIdIncrementer())
				.start(flow)
				.end()
				.build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
