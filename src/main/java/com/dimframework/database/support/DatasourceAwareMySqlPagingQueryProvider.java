package com.dimframework.database.support;

import javax.sql.DataSource;

import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class DatasourceAwareMySqlPagingQueryProvider extends MySqlPagingQueryProvider implements InitializingBean {
	
	private DataSource dataSource;

	
	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DatasourceAwareMySqlPagingQueryProvider() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.init(this.dataSource);
	}

}
