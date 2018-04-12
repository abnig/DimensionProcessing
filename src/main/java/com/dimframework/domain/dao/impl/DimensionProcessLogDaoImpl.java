package com.dimframework.domain.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.dimframework.domain.DimensionProcessLog;
import com.dimframework.domain.dao.DimensionProcessLogDao;
import com.dimframework.rowmapper.DimensionProcessLogRowMapper;

@Repository("dimensionProcessLogDaoImpl")
public class DimensionProcessLogDaoImpl implements DimensionProcessLogDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcMySQLTemplate;
	
//	private Logger logger = Logger.getLogger(DimensionProcessLogDaoImpl.class);

	@Override
	public int create(DimensionProcessLog runLog) {
		String query = "INSERT INTO DIM_PROCESS_LOG (endStatus,failureReason,startTimestamp,endTimestamp, metadataId) VALUES(:endStatus, :failureReason, :startTimestamp, :endTimestamp, :metadataId);";
		Map<String, Object> namedParameters = new HashMap<>();
		namedParameters.put("endStatus", runLog.getEndStatus());
		namedParameters.put("failureReason", runLog.getFailureReason());
		namedParameters.put("startTimestamp", runLog.getRunStartTimestamp());
		namedParameters.put("endTimestamp", runLog.getRunEndTimestamp());
		namedParameters.put("metadataId", runLog.getMetadataId());
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource(namedParameters);
		this.namedJdbcMySQLTemplate.update(query, sqlParameterSource, holder);
		return holder.getKey().intValue();
	}
	
	@Override
	public DimensionProcessLog getById(int id) {
		String query = "SELECT dimProcessId, startTimestamp, endTimestamp, metadataId, endStatus, failureReason FROM DIM_PROCESS_LOG WHERE dimProcessId = :dimProcessId";
		Map<String, Object> namedParameters = new HashMap<>();
		namedParameters.put("dimProcessId", Integer.valueOf(id));
		List<DimensionProcessLog>list =  (List<DimensionProcessLog>) this.namedJdbcMySQLTemplate.query(query, namedParameters, new DimensionProcessLogRowMapper());
		return list.get(0);
	}

}
