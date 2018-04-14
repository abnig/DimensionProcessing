package com.dimframework.domain.service.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.annotation.Resource;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.dimframework.domain.service.DimensionMetadataService;

@Service("operationMetadataService")
public class OperationMetadataService {
	
	@Resource
	Map<Long, Set<String>> registeredBeanMap;
	
	@Autowired
	@Qualifier("schemaName")
	String schemaName;

	@Autowired
	ApplicationContext applicationContext;

	AutowireCapableBeanFactory autowireCapableBeanFactory;

	BeanDefinitionRegistry beanDefinitionRegistry;

	JobBuilderFactory jobBuilderFactory;

	StepBuilderFactory stepBuilderFactory;

	@Autowired
	JobRepositoryFactoryBean jobRepository;

	@Autowired
	DimensionMetadataService dimensionMetadataDaoServiceImpl;

	@Autowired
	DataSourceTransactionManager transactionManager;
	
	synchronized void addToMap(Long runId, String beanName) {
		if (this.registeredBeanMap.containsKey(runId)) {
			Set<String> set = this.registeredBeanMap.get(runId);
			set.add(beanName);
			this.registeredBeanMap.put(runId, set);
		} else {
			Set<String> set = new ConcurrentSkipListSet<String>();
			set.add(beanName);
			this.registeredBeanMap.put(runId, set);
		}
	}
	
	synchronized void removeFromMap(Long processId, String beanName) {
		if (this.registeredBeanMap.containsKey(processId)) {
			Set<String> set = this.registeredBeanMap.get(processId);
			if (set.contains(beanName)) {
				set.remove(beanName);
				if (set.size() != 0)
					this.registeredBeanMap.put(processId, set);
				else
					this.registeredBeanMap.remove(processId);
			}
		}
	}

}
