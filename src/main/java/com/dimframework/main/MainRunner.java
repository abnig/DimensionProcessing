package com.dimframework.main;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.jboss.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dimframework.beanconfig.BeanConfig;
import com.dimframework.invoker.DimensionProcessInvoker;
import com.dimframework.invoker.impl.DimensionProcessInvokerImpl;

public class MainRunner {
	
	private static Logger logger = Logger.getLogger(MainRunner.class);

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
		DimensionProcessInvoker helloWorld = ctx.getBean(DimensionProcessInvokerImpl.class);
		DateTimeFormatter defaultMySqlDateFormatter = ctx.getBean(DateTimeFormatter.class);
		Date effectiveStartDate = new Date(new Date().getTime()+2000000L);
		Date effectiveEndDate = new Date();
		logger.info("EffectiveStartDate: " + defaultMySqlDateFormatter.format(effectiveStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
		logger.info("EffectiveEndDate: " + defaultMySqlDateFormatter.format(effectiveEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
		helloWorld.invoker("D1", effectiveStartDate, effectiveEndDate);

	}

}
