package com.dimframework.main;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dimframework.beanconfig.BeanConfig;
import com.dimframework.invoker.DimensionProcessInvoker;
import com.dimframework.invoker.impl.DimensionProcessInvokerImpl;

public class MainRunner {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
		DimensionProcessInvoker helloWorld = ctx.getBean(DimensionProcessInvokerImpl.class);
		helloWorld.invoker("D1", new Date(new Date().getTime()+2000000L), new Date(new Date().getTime()-2000000L));

	}

}
