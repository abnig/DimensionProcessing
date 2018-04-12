package com.poc.classifier;

import java.util.Map;

import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

public class SplitterClassifier<T> implements Classifier<T, ItemWriter<? super T>> {

	private Map<Class<T>, ItemWriter<T>> itemWriterMap;

	@Override
	public ItemWriter<T> classify(T classifiable) {
		Class<?> clazz = classifiable.getClass();
		if (itemWriterMap.containsKey(clazz))
			return itemWriterMap.get(clazz);
		else
			throw new IllegalArgumentException("No writer found for domain class '" + clazz + "'");
	}

	public Map<Class<T>, ItemWriter<T>> getItemWriterMap() {
		return itemWriterMap;
	}

	public void setItemWriterMap(Map<Class<T>, ItemWriter<T>> itemWriterMap) {
		this.itemWriterMap = itemWriterMap;
	}
}
