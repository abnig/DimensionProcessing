package com.poc.writer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;
import org.springframework.classify.ClassifierSupport;

import com.poc.domain.output.BaseOutputObject;

public class BaseJdbcItemWriter<T extends BaseOutputObject> implements ItemWriter<List<T>> {

	private Classifier<T, ItemWriter<? super T>> classifier = new ClassifierSupport<T, ItemWriter<? super T>>(null);
	
	public void setClassifier(Classifier<T, ItemWriter<? super T>> classifier) {
		this.classifier = classifier;
	}
	
	public Classifier<T, ItemWriter<? super T>> getClassifier() {
		return classifier;
	}

	@Override
	public void write(List<? extends List<T>> items) throws Exception {
		Map<ItemWriter<? super T>, List<T>> map = new LinkedHashMap<ItemWriter<? super T>, List<T>>();

		for (List<T> item : items) {
			for (T t : item) {
				ItemWriter<? super T> key = classifier.classify(t);
				if (!map.containsKey(key)) {
					map.put(key, new ArrayList<T>());
				}
				map.get(key).add(t);
			}
		}

		for (ItemWriter<? super T> writer : map.keySet()) {
				writer.write(map.get(writer));
		}

	}

}
