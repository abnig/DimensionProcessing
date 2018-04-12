package com.poc.classifier.main;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:splitter-job.xml");

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("splitter-poc-job");
		JdbcPagingItemReader reader = context.getBean(JdbcPagingItemReader.class);
		SqlPagingQueryProviderFactoryBean sqlBean = context.getBean(SqlPagingQueryProviderFactoryBean.class);
		try {
			// JobExecution execution = jobLauncher.run(job, new JobParameters());

			MySqlPagingQueryProvider s	= (MySqlPagingQueryProvider) sqlBean.getObject();
			//s.generateFirstPageQuery(1000);
		int totalCount = 154797;
		int pageSize = 25000;	
		int itemIndex = 1;
		int pageNum = 1;
		System.out.println(s.generateFirstPageQuery(pageSize));
		while((itemIndex + pageSize) < totalCount) {
			pageNum++;
			itemIndex = (pageSize * pageNum);
			System.out.println("itemIndex " + itemIndex);
			System.out.println(s.generateJumpToItemQuery(itemIndex, pageSize));
			System.out.println(s.generateRemainingPagesQuery(25000));
			
		}
		
	/*	
		
		MyJdbcTemplate template = context.getBean(MyJdbcTemplate.class);
		String sql = "SELECT * FROM ecom.T_INDIA_PIN_CODE";
		SortBy sb = new SortBy("pinCodeId", 1); 
		Pagination pagination = new Pagination();
		pagination.addSortBy(sb);
		pagination.setPageSize(250);
		for(;;) {
		PaginationResult<String> ss = template.queryForPage(sql, pagination, new CommonRowMapper());
		System.out.println(ss.getCurrentPage());
		System.out.println(ss.getPageSize());
		for(String sss : ss.getData())
			System.out.println(sss);
		}
*/
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
