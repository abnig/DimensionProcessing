package com.dimframework.domain.pojo;

public enum DataSelectionStrategy {
	
	CURSOR("CURSOR"), 
	PAGE("PAGE");
	
	private final String strategy;

	private DataSelectionStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String strategy() {
		return strategy;
	}

}
