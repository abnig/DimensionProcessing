package com.dimframework.domain.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dimframework.domain.InsertOperationMetadata;
import com.dimframework.domain.service.DimensionMetadataService;
import com.dimframework.rowmapper.CommonRowMapper;

@Service("abstractInsertOperationMetadataService")
public abstract class AbstractInsertOperationMetadataService extends OperationMetadataService {

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
	private DimensionMetadataService dimensionMetadataDaoServiceImpl;

	@Autowired
	DataSourceTransactionManager transactionManager;

	final private String createInsertOperationJdbcCursorItemReader(InsertOperationMetadata insertOperationMetadata)
			throws Exception {
		JdbcCursorItemReader<String> jdbcCursorItemReader = new JdbcCursorItemReader<>();
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(jdbcCursorItemReader.getClass());
		bdb.addPropertyReference("dataSource",
				StringUtils.isEmpty(insertOperationMetadata.getDimensionMetadata().getDataSourceBeanName())
						? "dataSource"
						: insertOperationMetadata.getDimensionMetadata().getDataSourceBeanName());
		bdb.addPropertyValue("fetchSize", insertOperationMetadata.getDimensionMetadata().getCommitInterval());
		bdb.addPropertyValue("saveState", Boolean.FALSE.booleanValue());
		bdb.addPropertyValue("rowMapper", new CommonRowMapper(insertOperationMetadata.getDimensionMetadata().getFieldDelimiter(), insertOperationMetadata.getDimensionMetadata().getRecordTerminator()));

		bdb.addPropertyValue("sql", insertOperationMetadata.getSelectSQL());
		String name = insertOperationMetadata.getProcessId().toString().concat("_InsertOperationJdbcCursorItemReader");
		bdb.addPropertyValue("name", name);
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		this.addToMap(insertOperationMetadata.getProcessId(), name);
		return name;
	}

	final private String createFlatFileItemWriter(InsertOperationMetadata insertOperationMetadata) {
		FlatFileItemWriter<String> flatFileItemWriter = new FlatFileItemWriter<String>();
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(flatFileItemWriter.getClass());
		Path out = Paths.get(insertOperationMetadata.getFileName());
		String name = insertOperationMetadata.getProcessId().toString().concat("_InsertOperationFlatFileItemWriter");
		bdb.addPropertyValue("resource", new FileSystemResource(out.toFile()));
		bdb.addPropertyValue("shouldDeleteIfEmpty", Boolean.TRUE);
		bdb.addPropertyValue("encoding", "UTF-8");
		bdb.addPropertyValue("name", name);
		bdb.addPropertyValue("lineAggregator", new PassThroughLineAggregator<String>());
		bdb.addPropertyValue("lineSeparator", insertOperationMetadata.getDimensionMetadata().getRecordTerminator());
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		this.addToMap(insertOperationMetadata.getProcessId(), name);
		return name;
	}
	
	final Step createInsertOperationStep(InsertOperationMetadata insertOperationMetadata)
			throws BeansException, Exception {
		String name = "InsertOperation_"
				+ insertOperationMetadata.getDimensionMetadata().getSourceTable() + "_Step" + insertOperationMetadata.getProcessId();
		@SuppressWarnings("unchecked")
		TaskletStep step = stepBuilderFactory.get(name)
				.<String, String>chunk(insertOperationMetadata.getDimensionMetadata().getCommitInterval())
				.reader((ItemReader<? extends String>) applicationContext
						.getBean(createInsertOperationJdbcCursorItemReader(insertOperationMetadata)))
				.writer((ItemWriter<? super String>) applicationContext
						.getBean(createFlatFileItemWriter(insertOperationMetadata)))
				.transactionManager(this.transactionManager).build();
		return step;
	}

	final Step createLoadIntoDimensionStep(InsertOperationMetadata insertOperationMetadata) {
		String name = "LoadOperation_"  
				+ insertOperationMetadata.getDimensionMetadata().getSourceTable() + "_Step_" + insertOperationMetadata.getProcessId() ;
		TaskletStep step = stepBuilderFactory.get(name).tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				dimensionMetadataDaoServiceImpl.executeLoadIntoDimensionTable(insertOperationMetadata);
				return RepeatStatus.FINISHED;
			}
		}).transactionManager(this.transactionManager).build();
		return step;
	}

}
