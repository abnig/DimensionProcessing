package com.dimframework.domain.service.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("operationMetadataService")
public class OperationMetadataService {
	
	@Resource
	Map<Long, Set<String>> registeredBeanMap;
	
	synchronized void addToMap(Long runId, String beanName) {
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

}
