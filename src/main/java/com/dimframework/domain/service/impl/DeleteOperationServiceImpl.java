package com.dimframework.domain.service.impl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.stereotype.Component;

import com.dimframework.domain.DeleteOperationMetadata;
import com.dimframework.domain.service.DeleteOperationService;

@Component("deleteOperationServiceImpl")
public class DeleteOperationServiceImpl extends AbstractDeleteOperationMetadataService implements DeleteOperationService, InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {

		autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
		beanDefinitionRegistry = (BeanDefinitionRegistry) autowireCapableBeanFactory;
		this.jobBuilderFactory = new JobBuilderFactory(this.jobRepository.getObject());
		this.stepBuilderFactory = new StepBuilderFactory(this.jobRepository.getObject(), this.transactionManager);
	}

	@Override
	public Job instantiateDeleteOperationBatchJob(DeleteOperationMetadata o) {
		String name = "DeleteOperation_" + o.getDimensionMetadata().getSourceTable() + "_Job_" + o.getProcessId();
		try {
			return jobBuilderFactory.get(name).repository(jobRepository.getObject()).incrementer(new RunIdIncrementer())
					.flow(createDeleteOperationStep(o)).end().build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}




	

		
}