package com.dimframework.domain.service.impl;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import com.dimframework.domain.UpdateOperationMetadata;
import com.dimframework.domain.service.UpdateOperationService;

@Component("updateOperationServiceImpl")
public class UpdateOperationServiceImpl implements UpdateOperationService, InitializingBean {
	
	@Autowired
	private ApplicationContext applicationContext;

	private AutowireCapableBeanFactory autowireCapableBeanFactory;

	private BeanDefinitionRegistry beanDefinitionRegistry;

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JobRepositoryFactoryBean jobRepository;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Resource
	private Map<Long, Set<String>> registeredBeanMap;

	@Override
	public void afterPropertiesSet() throws Exception {
		autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
		beanDefinitionRegistry = (BeanDefinitionRegistry) autowireCapableBeanFactory;
		this.jobBuilderFactory = new JobBuilderFactory(this.jobRepository.getObject());
		this.stepBuilderFactory = new StepBuilderFactory(this.jobRepository.getObject(), this.transactionManager);
	}

	@Override
	public Job instantiateUpdateOperationBatchJob(UpdateOperationMetadata updateOperationMetadata) {
		// TODO Auto-generated method stub
		return null;
	}

}
