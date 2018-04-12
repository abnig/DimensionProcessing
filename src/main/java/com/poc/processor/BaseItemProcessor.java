package com.poc.processor;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.poc.domain.input.BaseInputObject;
import com.poc.domain.output.BaseOutputObject;

public abstract class BaseItemProcessor<P extends BaseInputObject, T extends BaseOutputObject> implements ItemProcessor<P, List<T>> {

	public abstract List<T> process(P arg0) throws Exception;
}
