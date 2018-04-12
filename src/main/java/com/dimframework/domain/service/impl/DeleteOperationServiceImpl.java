package com.dimframework.domain.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dimframework.domain.DeleteOperationMetadata;
import com.dimframework.domain.service.DeleteOperationService;

@Component("deleteOperationServiceImpl")
public class DeleteOperationServiceImpl implements DeleteOperationService, InitializingBean {

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
	public Job instantiateDeleteOperationBatchJob(DeleteOperationMetadata o) {
		String name = "DimensionProcessing_SourceTable_" + o.getDimensionMetadata().getSourceTable();
		try {
			return jobBuilderFactory.get(name).repository(jobRepository.getObject()).incrementer(new RunIdIncrementer())
					.flow(createDeleteOperationStep(o)).end().build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	private String createDeleteOperationJdbcCursorItemReader(DeleteOperationMetadata deleteOperationMetadata)
			throws Exception {
		JdbcCursorItemReader<String> jdbcCursorItemReader = new JdbcCursorItemReader<>();
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(jdbcCursorItemReader.getClass());
		bdb.addPropertyReference("dataSource",
				StringUtils.isEmpty(deleteOperationMetadata.getDimensionMetadata().getDataSourceBeanName())
						? "dataSource"
						: deleteOperationMetadata.getDimensionMetadata().getDataSourceBeanName());
		bdb.addPropertyValue("fetchSize", deleteOperationMetadata.getDimensionMetadata().getCommitInterval());
		bdb.addPropertyValue("saveState", Boolean.FALSE.booleanValue());
		bdb.addPropertyValue("rowMapper",
				new SingleColumnRowMapper<>("requiredType".getClass()));

		bdb.addPropertyValue("sql", deleteOperationMetadata.getSelectSQL());
		String name = deleteOperationMetadata.getProcessId().toString().concat("_jdbcCursorItemReader");
		bdb.addPropertyValue("name", name);
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		this.addToMap(deleteOperationMetadata.getProcessId(), name);
		return name;
	}

	private synchronized void addToMap(Long runId, String beanName) {
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

	private synchronized void removeFromMap(Long processId, String beanName) {
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

	private String createDeleteOperationJdbcCursorItemWriter(DeleteOperationMetadata deleteOperationMetadata)
			throws Exception {
		CustomSqlParameterSourceProvider customSqlParameterSourceProvider = new CustomSqlParameterSourceProvider(deleteOperationMetadata.getDimensionMetadata().getEffectiveEndDate());
		BeanDefinitionBuilder bdb1 = BeanDefinitionBuilder.genericBeanDefinition(customSqlParameterSourceProvider.getClass());
		bdb1.addConstructorArgValue(deleteOperationMetadata.getDimensionMetadata().getEffectiveEndDate());
		String name = deleteOperationMetadata.getProcessId().toString().concat("_customSqlParameterSourceProvider");
		beanDefinitionRegistry.registerBeanDefinition(name, bdb1.getBeanDefinition());
		
		JdbcBatchItemWriter<String> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(jdbcBatchItemWriter.getClass());
		bdb.addPropertyReference("dataSource", StringUtils.isEmpty(deleteOperationMetadata.getDimensionMetadata().getDataSourceBeanName()) ? "dataSource" : deleteOperationMetadata.getDimensionMetadata().getDataSourceBeanName());
		bdb.addPropertyReference("jdbcTemplate", "namedJdbcMySQLTemplate");
		bdb.addPropertyValue("sql", deleteOperationMetadata.getUpdateSQL());
		bdb.addPropertyReference("itemSqlParameterSourceProvider", name);
		String nameJdbcBatchItemWriter= deleteOperationMetadata.getProcessId().toString().concat("_jdbcBatchItemWriter");
		beanDefinitionRegistry.registerBeanDefinition(nameJdbcBatchItemWriter, bdb.getBeanDefinition());
		return nameJdbcBatchItemWriter;
	}
	
	private Step createDeleteOperationStep(DeleteOperationMetadata o) throws BeansException, Exception {
		String name = "DimensionProcessing_SourceTable_" + o.getDimensionMetadata().getSourceTable() + "_Step";
		@SuppressWarnings("unchecked")
		TaskletStep step = stepBuilderFactory.get(name).<String, String>chunk(o.getDimensionMetadata().getCommitInterval())
				.reader((ItemReader<? extends String>) applicationContext.getBean( createDeleteOperationJdbcCursorItemReader(o)))
				.writer((ItemWriter<? super String>) applicationContext.getBean(createDeleteOperationJdbcCursorItemWriter(o)))
				.transactionManager(this.transactionManager).build();
		return step;
	}
		
}


final class CustomSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<String> {
	
	private Logger logger = Logger.getLogger(CustomSqlParameterSourceProvider.class);
	
	private final Date effectiveEndDate;
	
	public CustomSqlParameterSourceProvider(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	@Override
	public SqlParameterSource createSqlParameterSource(final String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("listHashPK", item);
		logger.debug("Effective End Date : " + effectiveEndDate);
		map.put("effectiveEndDate", this.effectiveEndDate);
		return new MapSqlParameterSource(map);
	}
}
