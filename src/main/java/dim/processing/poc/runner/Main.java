package dim.processing.poc.runner;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:accountProcessingJob.xml");

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("accountDimProcessingJob"); // accountDimProcessingJob

		// String splitFileQualifiedName = (String)
		// context.getBean("splitFileQualifiedName");
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString("runid", new Date().toGMTString());
		// jobParametersBuilder.addLong("splitStepTimeout", Long.MAX_VALUE);
		// jobParametersBuilder.addString("splitFileQualifiedName", "/Users/abnig19/EcomPortal/InboundDataFiles/SplitFiles/split");

		// jobLauncher.run(job, jobParametersBuilder.toJobParameters());
		
		System.out.println("XXX: " + org.apache.commons.codec.digest.DigestUtils.sha256Hex("DUMMY1DUMMY12DUMMY123DUMMY1234DUMMY123452017-01-01"));

	}
}
