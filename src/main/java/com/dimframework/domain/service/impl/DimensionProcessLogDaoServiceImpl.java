package com.dimframework.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dimframework.domain.DimensionProcessLog;
import com.dimframework.domain.dao.DimensionProcessLogDao;
import com.dimframework.domain.service.DimensionProcessLogDaoService;

@Service("dimensionProcessLogDaoServiceImpl")
public class DimensionProcessLogDaoServiceImpl implements DimensionProcessLogDaoService {

	@Autowired
	private DimensionProcessLogDao dimensionProcessLogDaoImpl;
	
	@Override
	public DimensionProcessLog create(DimensionProcessLog runLog) {
		int key =  this.dimensionProcessLogDaoImpl.create(runLog);
		return this.dimensionProcessLogDaoImpl.getById(key);
	}

}
