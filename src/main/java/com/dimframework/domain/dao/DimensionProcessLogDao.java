package com.dimframework.domain.dao;

import com.dimframework.domain.DimensionProcessLog;

public interface DimensionProcessLogDao {
	int create(DimensionProcessLog runLog);
	DimensionProcessLog getById(int Id);
	

}
