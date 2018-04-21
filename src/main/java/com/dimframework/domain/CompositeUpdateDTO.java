package com.dimframework.domain;

import java.io.Serializable;

public class CompositeUpdateDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8581556354706434602L;

	private final String hashPK;
	
	private final String insertRowData;
	
	public CompositeUpdateDTO(String hashPK, String insertRowData) {
		this.hashPK = hashPK;
		this.insertRowData = insertRowData;
	}

	public String getHashPK() {
		return hashPK;
	}

	public String getInsertRowData() {
		return insertRowData;
	}
	
}
