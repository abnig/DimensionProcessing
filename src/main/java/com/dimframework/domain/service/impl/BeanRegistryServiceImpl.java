package com.dimframework.domain.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.resource.ListPreparedStatementSetter;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dimframework.database.support.DatasourceAwareMySqlPagingQueryProvider;
import com.dimframework.database.support.PopulateHashItemProcessor;
import com.dimframework.domain.pojo.OutboundFileMetadata;
import com.dimframework.domain.pojo.PopulateHashBatchJobMetadata;
import com.dimframework.domain.service.BeanRegistryService;
import com.dimframework.domain.service.DimensionMetadataService;
import com.dimframework.rowmapper.CommonRowMapper;

@Component("beanRegistryServiceImpl")
public class BeanRegistryServiceImpl implements BeanRegistryService, InitializingBean {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JobRepositoryFactoryBean jobRepository;

	@Autowired
	private ApplicationContext applicationContext;

	private AutowireCapableBeanFactory autowireCapableBeanFactory;

	private BeanDefinitionRegistry beanDefinitionRegistry;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	private Logger logger = Logger.getLogger(BeanRegistryServiceImpl.class);

	@Resource
	private Map<Long, Set<String>> registeredBeanMap;

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private PopulateHashItemProcessor populateHashItemProcessor;
	
	@Autowired
	private DimensionMetadataService dimensionMetadataDaoServiceImpl;

	@Override
	public void afterPropertiesSet() throws Exception {
		
		autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
		beanDefinitionRegistry = (BeanDefinitionRegistry) autowireCapableBeanFactory;
		this.jobBuilderFactory = new JobBuilderFactory(this.jobRepository.getObject());
		this.stepBuilderFactory = new StepBuilderFactory(this.jobRepository.getObject(),
				this.transactionManager);
	}

	@Override
	public synchronized void unregisterBeansFromContext(Long runId) {

		Set<String> beanNameSet = this.registeredBeanMap.get(runId);
		Iterator<String> iterator = beanNameSet.iterator();
		while (iterator.hasNext()) {
			String beanName = iterator.next();
			if (beanDefinitionRegistry.containsBeanDefinition(beanName)) {
				logger.debug("Registered bean found with name " + beanName
						+ ". Attempting to deregister. Total Number of beans before deregister "
						+ this.beanDefinitionRegistry.getBeanDefinitionCount());
				beanDefinitionRegistry.removeBeanDefinition(beanName);
				logger.debug("Total Number of beans after deregister "
						+ this.beanDefinitionRegistry.getBeanDefinitionCount());
			} else {
				logger.info("No bean found with name " + beanName + ". Total Number of beans "
						+ this.beanDefinitionRegistry.getBeanDefinitionCount());
			}
			this.removeFromMap(runId, beanName);
		}

	}

	@Override
	public Object getBeanByName(String name) {
		return applicationContext.getBean(name);
	}

	@Override
	public Step createStep(OutboundFileMetadata outputFileMetaData) throws Exception {
		String name = "edwReplication_SourceEntity_" + outputFileMetaData.getSourceEntity() + "_Step";
		@SuppressWarnings("unchecked")
		TaskletStep step = stepBuilderFactory.get(name).<String, String>chunk(outputFileMetaData.getCommitInterval())
				.reader((ItemReader<? extends String>) applicationContext
						.getBean(instantiateJdbcCursorItemReader(outputFileMetaData)))
				.writer((ItemWriter<? super String>) applicationContext
						.getBean(instantiateFlatFileItemWriter(outputFileMetaData)))
				// .listener(this.edwReplicationWriteListener)
				// .listener(this.edwReplicationOnDemandStepExecutionListener)
				.transactionManager(this.transactionManager).build();
		return step;
	}

	public String getPrototypeOutboundFileMetadata(OutboundFileMetadata outputFileMetaData) {
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(OutboundFileMetadata.class);
		bdb.addPropertyValue("metadataId", outputFileMetaData.getMetadataId());
		bdb.addPropertyValue("selectSQL", outputFileMetaData.getSelectSQL());
		bdb.addPropertyValue("delimiter", outputFileMetaData.getDelimiter());
		bdb.addPropertyValue("recordTerminator", outputFileMetaData.getRecordTerminator());
		bdb.addPropertyValue("absoluteDataFileName", outputFileMetaData.getAbsoluteDataFileName());
		bdb.addPropertyValue("lastSuccessfulReplicationTimeStamp",
				outputFileMetaData.getLastSuccessfulReplicationTimeStamp());
		bdb.addPropertyValue("jobInstanceId", outputFileMetaData.getJobInstanceId());
		bdb.addPropertyValue("sourceEntity", outputFileMetaData.getSourceEntity());
		bdb.addPropertyValue("targetEntity", outputFileMetaData.getTargetEntity());
		bdb.addPropertyValue("TargetSchema", outputFileMetaData.getTargetSchema());
		bdb.addPropertyValue("orderDate", outputFileMetaData.getOrderDate());
		bdb.addPropertyValue("parameterMap", outputFileMetaData.getParameterMap());
		bdb.addPropertyValue("selectClause", outputFileMetaData.getSelectClause());
		bdb.addPropertyValue("fromClause", outputFileMetaData.getFromClause());
		bdb.addPropertyValue("whereClause", outputFileMetaData.getWhereClause());
		bdb.addPropertyValue("sortKeys", outputFileMetaData.getSortKeys());
		bdb.addPropertyValue("runId", outputFileMetaData.getRunId());
		bdb.addPropertyValue("concurrencyLimit", outputFileMetaData.getConcurrencyLimit());
		bdb.addPropertyValue("commitInterval", outputFileMetaData.getCommitInterval());
		bdb.addPropertyValue("dataSource", outputFileMetaData.getDataSource());
		String name = outputFileMetaData.getRunId().toString().concat("_OutboundFileMetadata");
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		this.addToMap(outputFileMetaData.getRunId(), name);
		return name;
	}

	@Override
	public String instantiateJdbcPagingItemReader(OutboundFileMetadata outputFileMetaData) throws Exception {
		JdbcPagingItemReader<String> jdbcPagingItemReader = new JdbcPagingItemReader<String>();
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(jdbcPagingItemReader.getClass());
		bdb.addPropertyReference("dataSource", StringUtils.isEmpty(outputFileMetaData.getDataSource()) ? "dataSource"
				: outputFileMetaData.getDataSource());
		bdb.addPropertyValue("pageSize", outputFileMetaData.getCommitInterval());
		bdb.addPropertyValue("saveState", Boolean.FALSE.booleanValue());
//		bdb.addPropertyValue("rowMapper",new CommonRowMapper(outputFileMetaData.getDelimiter(), outputFileMetaData.getRecordTerminator()));
		bdb.addPropertyValue("parameterValues", outputFileMetaData.getParameterMap());
		bdb.addPropertyReference("queryProvider", instantiateSqlPagingQueryProviderFactoryBean(outputFileMetaData));

		String name = outputFileMetaData.getRunId().toString().concat("_jdbcPagingItemReader");
		bdb.addPropertyValue("name", name);
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		this.addToMap(outputFileMetaData.getRunId(), name);
		return name;
	}

	@Override
	public String instantiateJdbcCursorItemReader(OutboundFileMetadata outputFileMetaData) throws Exception {
		JdbcCursorItemReader<String> jdbcCursorItemReader = new JdbcCursorItemReader<>();
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(jdbcCursorItemReader.getClass());
		bdb.addPropertyReference("dataSource", StringUtils.isEmpty(outputFileMetaData.getDataSource()) ? "dataSource"
				: outputFileMetaData.getDataSource());
		bdb.addPropertyValue("fetchSize", outputFileMetaData.getCommitInterval());
		bdb.addPropertyValue("saveState", Boolean.FALSE.booleanValue());
		//bdb.addPropertyValue("rowMapper",new CommonRowMapper(outputFileMetaData.getDelimiter(), outputFileMetaData.getRecordTerminator()));
		if (outputFileMetaData.getParameterMap() != null && outputFileMetaData.getParameterMap().size() > 0) {
			ListPreparedStatementSetter setter = new ListPreparedStatementSetter();
			setter.setParameters(Arrays.asList(outputFileMetaData.getParameterMap().values().toArray()));
			bdb.addPropertyValue("preparedStatementSetter", setter);
		}

		bdb.addPropertyValue("sql", outputFileMetaData.getSelectSQL());
		String name = outputFileMetaData.getRunId().toString().concat("_jdbcCursorItemReader");
		bdb.addPropertyValue("name", name);
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		this.addToMap(outputFileMetaData.getRunId(), name);
		return name;

	}

	public String instantiateSqlPagingQueryProviderFactoryBean(OutboundFileMetadata outputFileMetaData)
			throws Exception {
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder
				.genericBeanDefinition(DatasourceAwareMySqlPagingQueryProvider.class);
		bdb.addPropertyValue("selectClause", outputFileMetaData.getSelectClause());
		bdb.addPropertyValue("fromClause", outputFileMetaData.getFromClause());
		bdb.addPropertyValue("whereClause", outputFileMetaData.getWhereClause());
		bdb.addPropertyValue("sortKeys", outputFileMetaData.getSortKeys());
		bdb.addPropertyReference("dataSource", StringUtils.isEmpty(outputFileMetaData.getDataSource()) ? "dataSource"
				: outputFileMetaData.getDataSource());
		String name = outputFileMetaData.getRunId().toString().concat("_sqlPagingQueryProvider");
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		this.addToMap(outputFileMetaData.getRunId(), name);
		return name;
	}

	public String instantiateFlatFileItemWriter(OutboundFileMetadata outputFileMetaData) {
		FlatFileItemWriter<String> flatFileItemWriter = new FlatFileItemWriter<String>();
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(flatFileItemWriter.getClass());
		Path out = Paths.get(outputFileMetaData.getAbsoluteDataFileName());
		String name = outputFileMetaData.getRunId().toString().concat("_flatFileItemWriter");
		bdb.addPropertyValue("resource", new FileSystemResource(out.toFile()));
		bdb.addPropertyValue("shouldDeleteIfEmpty", Boolean.TRUE);
		bdb.addPropertyValue("encoding", "UTF-8");
		bdb.addPropertyValue("name", name);
		bdb.addPropertyValue("lineAggregator", new PassThroughLineAggregator<String>());
		bdb.addPropertyValue("lineSeparator", outputFileMetaData.getRecordTerminator());
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		this.addToMap(outputFileMetaData.getRunId(), name);
		return name;
	}

	@Override
	public JobExecution run(Job fileCreationJob, JobParameters jobParameters)
			throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
			JobParametersInvalidException {
		JobExecution je = jobLauncher.run(fileCreationJob, jobParameters);
		return je;
	}

	@Override
	public synchronized void addToMap(Long runId, String beanName) {
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

	@Override
	public synchronized void removeFromMap(Long runId, String beanName) {
		if (this.registeredBeanMap.containsKey(runId)) {
			Set<String> set = this.registeredBeanMap.get(runId);
			if (set.contains(beanName)) {
				set.remove(beanName);
				if (set.size() != 0)
					this.registeredBeanMap.put(runId, set);
				else
					this.registeredBeanMap.remove(runId);
			}
		}
	}

	@Override
	public String getPrototypePopulateHashBatchJobMetadata(PopulateHashBatchJobMetadata populateHashBatchJobMetadata) {
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(PopulateHashBatchJobMetadata.class);
		bdb.addConstructorArgValue(populateHashBatchJobMetadata.getDimensionMetadata());
		bdb.addConstructorArgValue(populateHashBatchJobMetadata.getSelectSQL());
		bdb.addConstructorArgValue(populateHashBatchJobMetadata.getInsertSQL());
		bdb.addConstructorArgValue(populateHashBatchJobMetadata.getDimensionProcessLog());
		bdb.addConstructorArgValue(populateHashBatchJobMetadata.getFileName());
		bdb.addConstructorArgValue(populateHashBatchJobMetadata.getProcessId());
		bdb.addConstructorArgValue(populateHashBatchJobMetadata.getSchemaName());
		String name = populateHashBatchJobMetadata.getDimensionMetadata().getMetadataId().toString().concat("_PopulateHashBatchJobMetadata");
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		return name;
	}

	@Override
	public Job instantiatePopulateHashJob(String populateHashBatchJobMetadataBeanName) {
		PopulateHashBatchJobMetadata o = null;
		
		if(applicationContext.containsBeanDefinition(populateHashBatchJobMetadataBeanName))
		{
			logger.debug("Bean with name " + populateHashBatchJobMetadataBeanName + " found successfully.");
			o = applicationContext.getBean(populateHashBatchJobMetadataBeanName, PopulateHashBatchJobMetadata.class);
		}
		else
		{
			logger.error("Bean with name " + populateHashBatchJobMetadataBeanName + " not found. Bailing out...");
			System.exit(1);
		}
		
		String name = "LoadHash_" + o.getDimensionMetadata().getSourceTable() + "_Job_" + o.getDimensionProcessLog().getProcessId();
		try {
			return jobBuilderFactory.get(name).repository(jobRepository.getObject()).incrementer(new RunIdIncrementer())
					.flow(createStep(o)).next(createLoadIntoHashColumnsStep(o)).end().build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	final Step createLoadIntoHashColumnsStep(PopulateHashBatchJobMetadata hm) {
		String name = "LoadOperation_"  
				+ hm.getDimensionMetadata().getSourceTableHash() + "_Step_" + hm.getProcessId() ;
		TaskletStep step = stepBuilderFactory.get(name).tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				dimensionMetadataDaoServiceImpl.executeLoadIntoHash(hm);
				return RepeatStatus.FINISHED;
			}
		}).transactionManager(this.transactionManager).build();
		return step;
	}

	private Step createStep(PopulateHashBatchJobMetadata o) {
		String name = "LoadHash_" + o.getDimensionMetadata().getSourceTable() + "_Step_" + o.getDimensionProcessLog().getProcessId();
		@SuppressWarnings("unchecked")
		TaskletStep step = stepBuilderFactory.get(name).<String, String>chunk(o.getDimensionMetadata().getCommitInterval())
				.reader((ItemReader<? extends String>) applicationContext.getBean(instantiateJdbcCursorItemReader(o)))
				.processor(this.populateHashItemProcessor)
				.writer((ItemWriter<? super String>) applicationContext.getBean(instantiateHashFileItemWriter(o)))
				// .listener(this.edwReplicationWriteListener)
				// .listener(this.edwReplicationOnDemandStepExecutionListener)
				.transactionManager(this.transactionManager).build();
		return step;
	}

	private String instantiateHashFileItemWriter(PopulateHashBatchJobMetadata o) {
		FlatFileItemWriter<String> flatFileItemWriter = new FlatFileItemWriter<String>();
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(flatFileItemWriter.getClass());
		Long processId = o.getDimensionProcessLog().getProcessId();
		Path out = Paths.get(o.getFileName());
		String name = processId.toString().concat("_flatFileItemWriter");
		bdb.addPropertyValue("resource", new FileSystemResource(out.toFile()));
		bdb.addPropertyValue("shouldDeleteIfEmpty", Boolean.TRUE);
		bdb.addPropertyValue("encoding", "UTF-8");
		bdb.addPropertyValue("name", name);
		bdb.addPropertyValue("lineAggregator", new PassThroughLineAggregator<String>());
		bdb.addPropertyValue("lineSeparator", o.getDimensionMetadata().getRecordTerminator());
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		return name;
	}

	private String instantiateJdbcCursorItemReader(PopulateHashBatchJobMetadata o) {
		JdbcCursorItemReader<String> jdbcCursorItemReader = new JdbcCursorItemReader<>();
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(jdbcCursorItemReader.getClass());
		bdb.addPropertyReference("dataSource", o.getDimensionMetadata().getDataSourceBeanName());
		bdb.addPropertyValue("fetchSize", o.getDimensionMetadata().getCommitInterval());
		bdb.addPropertyValue("saveState", Boolean.FALSE.booleanValue());
		bdb.addPropertyValue("rowMapper", new CommonRowMapper(o.getDimensionMetadata().getFieldDelimiter(), o.getDimensionMetadata().getRecordTerminator()));
		bdb.addPropertyValue("sql", o.getSelectSQL());
		String name = o.getDimensionMetadata().getMetadataId().toString().concat("_jdbcCursorItemReader");
		bdb.addPropertyValue("name", name);
		beanDefinitionRegistry.registerBeanDefinition(name, bdb.getBeanDefinition());
		this.addToMap(o.getDimensionMetadata().getMetadataId().longValue(), name);
		logger.debug("Created JdbcCursorItemReader bean successfully using " + o.toString());
		return name;
	}

}
