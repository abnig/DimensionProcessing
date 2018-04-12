package com.dimframework.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dimframework.domain.DimensionProcessLog;

public class DimensionProcessLogRowMapper implements RowMapper<DimensionProcessLog> {

	@Override
	public DimensionProcessLog mapRow(ResultSet rs, int rowNum) throws SQLException {

		DimensionProcessLog log = new DimensionProcessLog(rs.getLong("dimProcessId"), rs.getTimestamp("startTimestamp"),
				rs.getTimestamp("endTimestamp"), rs.getInt("metadataId"), rs.getString("endStatus"),
				rs.getString("failureReason"));
		return log;
	}

}
