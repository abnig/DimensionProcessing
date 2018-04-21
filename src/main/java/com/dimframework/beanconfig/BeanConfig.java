package com.dimframework.beanconfig;

import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.dimframework.domain.DeleteOperationMetadata;
import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.InsertOperationMetadata;
import com.dimframework.domain.UpdateOperationMetadata;
import com.dimframework.rowmapper.CommonRowMapper;
import com.dimframework.rowmapper.InsertCommonRowMapper;

@Configuration
@ComponentScan("com.dimframework")
@PropertySource("classpath:/config/splitter-jdbc.properties")
@EnableAspectJAutoProxy
public class BeanConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${jdbc.driverClassName}")
	private String driverClassName;

	@Value("${jdbc.url}")
	private String url;

	@Value("${jdbc.username}")
	private String username;

	@Value("${jdbc.password}")
	private String password;

	@Value("${serverTimezone}")
	private String serverTimezone;

	@Value("${verifyServerCertificate}")
	private String verifyServerCertificate;

	@Value("${useSSL}")
	private String useSSL;

	@Value("${field.delimiter}")
	private String fieldDelimiterAscii;

	@Value("${record.terminator}")
	private String recordTerminatorAscii;

	@Value("${hash.datafile.base.path}")
	private String hashDataFilesBasePath;

	@Value("${schemaName}")
	private String schemaName;

	@Bean
	public DateTimeFormatter defaultMySqlDateFormatter() {
		// YYYY-MM-DD HH:MM:SS
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s");
		return formatter;
	}

	@Bean
	public String hashDataFilesBasePath() {
		return new String(hashDataFilesBasePath);
	}

	@Bean
	public String fieldDelimiter() {
		return Character.toString((char) new Integer(fieldDelimiterAscii).intValue());
	}

	@Bean
	public String recordTerminator() {
		return Character.toString((char) new Integer(recordTerminatorAscii).intValue());
	}

	@Bean
	public String schemaName() {
		return new String(schemaName);
	}

	@Bean
	public CommonRowMapper commonRowMapper(String fieldDelimiter, String recordTerminator) {
		return new CommonRowMapper(fieldDelimiter, recordTerminator);
	}

	@Bean
	public InsertCommonRowMapper insertCommonRowMapper(String fieldDelimiter, String recordTerminator) {
		return new InsertCommonRowMapper(fieldDelimiter, recordTerminator);
	}

	@Bean
	public DriverManagerDataSource mysqlDataSource() throws NoSuchAlgorithmException {
		DriverManagerDataSource mysqlDataSource = new DriverManagerDataSource();
		mysqlDataSource.setDriverClassName(this.driverClassName);
		mysqlDataSource.setUrl(this.url);
		mysqlDataSource.setUsername(this.username);
		mysqlDataSource.setPassword(this.password);
		Properties properties = new Properties();
		properties.put("serverTimezone", this.serverTimezone);
		properties.put("verifyServerCertificate", this.verifyServerCertificate);
		properties.put("useSSL", this.useSSL);
		mysqlDataSource.setConnectionProperties(properties);
		return mysqlDataSource;
	}

	@Bean(name = "namedJdbcMySQLTemplate")
	public NamedParameterJdbcTemplate namedJdbcMySQLTemplate(DriverManagerDataSource mysqlDataSource) {
		NamedParameterJdbcTemplate namedJdbcMySQLTemplate = new NamedParameterJdbcTemplate(mysqlDataSource);
		return namedJdbcMySQLTemplate;
	}

	@Bean(name = "mySqlJdbcTemplate")
	public JdbcTemplate mySqlJdbcTemplate(DriverManagerDataSource mysqlDataSource) {
		JdbcTemplate mySqlJdbcTemplate = new JdbcTemplate(mysqlDataSource);
		mySqlJdbcTemplate.setIgnoreWarnings(false);
		return mySqlJdbcTemplate;
	}

	@Bean(name = "mySQLSimpleJdbcInsert")
	public SimpleJdbcInsert mySQLSimpleJdbcInsert(DriverManagerDataSource mysqlDataSource) {
		SimpleJdbcInsert namedJdbcMySQLTemplate = new SimpleJdbcInsert(mysqlDataSource);
		return namedJdbcMySQLTemplate;
	}

	@Bean(name = "dimensionMetadataBlockingQueue")
	public BlockingQueue<DimensionMetadata> dimensionMetadataBlockingQueue() {
		return new LinkedBlockingQueue<DimensionMetadata>();
	}

	@Bean(name = "deleteOperationMetadataBlockingQueue")
	public BlockingQueue<DeleteOperationMetadata> deleteOperationMetadataBlockingQueue() {
		return new LinkedBlockingQueue<DeleteOperationMetadata>();
	}

	@Bean(name = "insertOperationMetadataBlockingQueue")
	public BlockingQueue<InsertOperationMetadata> insertOperationMetadataBlockingQueue() {
		return new LinkedBlockingQueue<InsertOperationMetadata>();
	}

	@Bean(name = "updateOperationMetadataBlockingQueue")
	public BlockingQueue<UpdateOperationMetadata> updateOperationMetadataBlockingQueue() {
		return new LinkedBlockingQueue<UpdateOperationMetadata>();
	}

	@Bean(name = "dimensionProcessingExecutorService")
	public ExecutorService dimensionProcessingExecutorService() {
		ExecutorService dimensionProcessingExecutorService = Executors.newFixedThreadPool(10);
		return dimensionProcessingExecutorService;
	}

	@Bean(name = "countDownLatch")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public CountDownLatch countDownLatch(int size) {
		CountDownLatch countDownLatch = new CountDownLatch(size);
		return countDownLatch;
	}

	@Bean
	public Map<Long, Set<String>> registeredBeanMap() {
		return new ConcurrentHashMap<Long, Set<String>>();
	}

	@Bean
	public DataSourceTransactionManager transactionManager(DriverManagerDataSource mysqlDataSource) {
		return new DataSourceTransactionManager(mysqlDataSource);
	}

	@Bean
	public SimpleJobLauncher jobLauncher(JobRepositoryFactoryBean jobRepository) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository.getObject());
		return jobLauncher;
	}

	@Bean
	public JobRepositoryFactoryBean jobRepository(DataSourceTransactionManager transactionManager,
			DriverManagerDataSource mysqlDataSource) {
		JobRepositoryFactoryBean jobRepository = new JobRepositoryFactoryBean();
		jobRepository.setDataSource(mysqlDataSource);
		jobRepository.setTransactionManager(transactionManager);
		jobRepository.setDatabaseType(DatabaseType.MYSQL.name());
		return jobRepository;
	}

}
