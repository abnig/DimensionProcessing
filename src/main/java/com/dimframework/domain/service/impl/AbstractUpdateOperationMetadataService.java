package com.dimframework.domain.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dimframework.database.support.CustomSqlParameterSourceProvider;
import com.dimframework.database.support.DimensionProcessingStepExecutionListener;
import com.dimframework.database.support.UpdateOperationCompositeWriter;
import com.dimframework.database.support.UpdateProcessItemProcessor;
import com.dimframework.domain.CompositeUpdateDTO;
import com.dimframework.domain.UpdateOperationMetadata;

@Service("abstractInsertOperationMetadataService")
public abstract class AbstractUpdateOperationMetadataService extends OperationMetadataService {

	final private String createUpdateOperationJdbcCursorItemReader(UpdateOperationMetadata updateOperationMetadata)
			throws Exception {
		JdbcCursorItemReader<String> jdbcCursorItemReader = new JdbcCursorItemReader<>();
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(jdbcCursorItemReader.getClass());
		bdb.addPropertyReference("dataSource",
				StringUtils.isEmpty(updateOperationMetadata.getDimensionMetadata().getDataSourceBeanName())
						? "dataSource"
						: updateOperationMetadata.getDimensionMetadata().getDataSourceBeanName());
		bdb.addPropertyValue("fetchSize", updateOperationMetadata.getDimensionMetadata().getCommitInterval());
		bdb.addPropertyValue("saveState", Boolean.FALSE.booleanValue());
		bdb.addPropertyValue("rowMapper", insertCommonRowMapper);

		bdb.addPropertyValue("sql", updateOperationMetadata.getSelectSQL());
		String name = new StringBuilder(updateOperationMetadata.getDimensionMetadata().getSourceTable()).append(updateOperationMetadata.getProcessId().toString()).append("_InsertOperationJdbcCursorItemReader").toString();
		bdb.addPropertyValue("name", name);
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		this.addToMap(updateOperationMetadata.getProcessId(), name);
		return name;
	}

	final Step createUpdateOperationStep(UpdateOperationMetadata updateOperationMetadata)
			throws BeansException, Exception {
		String name = "UpdateOperation_" + updateOperationMetadata.getDimensionMetadata().getSourceTable() + "_Step"
				+ updateOperationMetadata.getProcessId();

		@SuppressWarnings("unchecked")
		TaskletStep step = stepBuilderFactory.get(name)
				.<String, CompositeUpdateDTO>chunk(updateOperationMetadata.getDimensionMetadata().getCommitInterval())
				.reader((ItemReader<? extends String>) applicationContext.getBean(createUpdateOperationJdbcCursorItemReader(updateOperationMetadata)))
				.processor(applicationContext.getBean(UpdateProcessItemProcessor.class))
				.writer((ItemWriter<? super CompositeUpdateDTO>) applicationContext.getBean(createUpdateOperationCompositeWriter(updateOperationMetadata)))
				.listener(applicationContext.getBean(DimensionProcessingStepExecutionListener.class))
				.transactionManager(this.transactionManager).build();
		return step;
	}

	String createUpdateOperationCompositeWriter(UpdateOperationMetadata updateOperationMetadata) throws Exception {
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(UpdateOperationCompositeWriter.class);
		bdb.addConstructorArgReference(createUpdateFlatFileItemWriter(updateOperationMetadata));
		bdb.addConstructorArgReference(createUpdateOperationJdbcCursorItemWriter(updateOperationMetadata));
		String name = UpdateOperationCompositeWriter.class.getSimpleName().concat(updateOperationMetadata.getProcessId().toString());
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		return name;
	}

	String createUpdateOperationJdbcCursorItemWriter(UpdateOperationMetadata updateOperationMetadata) throws Exception {
		CustomSqlParameterSourceProvider customSqlParameterSourceProvider = new CustomSqlParameterSourceProvider(
				updateOperationMetadata.getDimensionMetadata().getEffectiveEndDate());
		BeanDefinitionBuilder bdb1 = BeanDefinitionBuilder
				.genericBeanDefinition(customSqlParameterSourceProvider.getClass());
		bdb1.addConstructorArgValue(updateOperationMetadata.getDimensionMetadata().getEffectiveEndDate());
		String name = new StringBuilder(updateOperationMetadata.getDimensionMetadata().getSourceTable()).append(updateOperationMetadata.getProcessId().toString()).append("_customSqlParameterSourceProvider").toString();
		beanDefinitionRegistry.registerBeanDefinition(name, bdb1.getBeanDefinition());

		JdbcBatchItemWriter<String> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(jdbcBatchItemWriter.getClass());
		bdb.addPropertyReference("dataSource",
				StringUtils.isEmpty(updateOperationMetadata.getDimensionMetadata().getDataSourceBeanName())
						? "dataSource"
						: updateOperationMetadata.getDimensionMetadata().getDataSourceBeanName());
		bdb.addPropertyReference("jdbcTemplate", "namedJdbcMySQLTemplate");
		bdb.addPropertyValue("sql", updateOperationMetadata.getUpdateSQL());
		bdb.addPropertyReference("itemSqlParameterSourceProvider", name);
		String nameJdbcBatchItemWriter = new StringBuilder(updateOperationMetadata.getDimensionMetadata().getSourceTable()).append(updateOperationMetadata.getProcessId().toString()).append("_jdbcBatchItemWriter").toString();
		beanDefinitionRegistry.registerBeanDefinition(nameJdbcBatchItemWriter, bdb.getBeanDefinition());
		return nameJdbcBatchItemWriter;
	}

	final private String createUpdateFlatFileItemWriter(UpdateOperationMetadata updateOperationMetadata) {
		FlatFileItemWriter<String> flatFileItemWriter = new FlatFileItemWriter<String>();
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(flatFileItemWriter.getClass());
		Path out = Paths.get(updateOperationMetadata.getFileName());
		String name = updateOperationMetadata.getProcessId().toString().concat("_UpdateOperationFlatFileItemWriter");
		bdb.addPropertyValue("resource", new FileSystemResource(out.toFile()));
		bdb.addPropertyValue("shouldDeleteIfEmpty", Boolean.TRUE);
		bdb.addPropertyValue("encoding", "UTF-8");
		bdb.addPropertyValue("name", name);
		bdb.addPropertyValue("lineAggregator", new PassThroughLineAggregator<String>());
		bdb.addPropertyValue("lineSeparator", updateOperationMetadata.getDimensionMetadata().getRecordTerminator());
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		this.addToMap(updateOperationMetadata.getProcessId(), name);
		return name;
	}

	final Step createInsertIntoDimensionStep(UpdateOperationMetadata updateOperationMetadata) {
		String name = "LoadOperation_" + updateOperationMetadata.getDimensionMetadata().getSourceTable() + "_Step_"
				+ updateOperationMetadata.getProcessId();
		TaskletStep step = stepBuilderFactory.get(name).tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				dimensionMetadataDaoServiceImpl.executeLoadIntoDimensionTable(updateOperationMetadata);
				return RepeatStatus.FINISHED;
			}
		}).listener((applicationContext.getBean(DimensionProcessingStepExecutionListener.class)))
		.transactionManager(this.transactionManager).build();
		return step;
	}

}
