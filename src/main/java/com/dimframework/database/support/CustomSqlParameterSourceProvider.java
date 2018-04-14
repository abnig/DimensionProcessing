package com.dimframework.database.support;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public  class CustomSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<String> {
	
	private Logger logger = Logger.getLogger(CustomSqlParameterSourceProvider.class);
	
	private final Date effectiveEndDate;
	
	public CustomSqlParameterSourceProvider(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	@Override
	public SqlParameterSource createSqlParameterSource(final String item) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("listHashPK", item);
		logger.debug("Effective End Date : " + effectiveEndDate);
		map.put("effectiveEndDate", this.effectiveEndDate);
		return new MapSqlParameterSource(map);
	}
}
