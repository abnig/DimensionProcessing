package com.dimframework.domain.service.impl;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dimframework.database.support.CustomSqlParameterSourceProvider;
import com.dimframework.domain.DeleteOperationMetadata;

@Service("abstractDeleteOperationMetadataService")
public abstract class AbstractDeleteOperationMetadataService extends OperationMetadataService {
	
	Step createDeleteOperationStep(DeleteOperationMetadata o) throws BeansException, Exception {
		String name = "DeleteOperation_" + o.getDimensionMetadata().getSourceTable() + "_Step_" + o.getProcessId();
		@SuppressWarnings("unchecked")
		TaskletStep step = stepBuilderFactory.get(name).<String, String>chunk(o.getDimensionMetadata().getCommitInterval())
				.reader((ItemReader<? extends String>) applicationContext.getBean( createDeleteOperationJdbcCursorItemReader(o)))
				.writer((ItemWriter<? super String>) applicationContext.getBean(createDeleteOperationJdbcCursorItemWriter(o)))
				.transactionManager(this.transactionManager).build();
		return step;
	}
	
	String createDeleteOperationJdbcCursorItemWriter(DeleteOperationMetadata deleteOperationMetadata)
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
	
	String createDeleteOperationJdbcCursorItemReader(DeleteOperationMetadata deleteOperationMetadata)
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

}
