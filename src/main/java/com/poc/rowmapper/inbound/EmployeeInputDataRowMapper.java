package com.poc.rowmapper.inbound;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.poc.domain.input.EmployeeInputData;

public class EmployeeInputDataRowMapper implements RowMapper<EmployeeInputData> {

	@Override
	public EmployeeInputData mapRow(ResultSet rs, int arg1) throws SQLException {
		EmployeeInputData e = new EmployeeInputData();
		e.setAddress(rs.getString("address"));
		e.setDepartment(rs.getString("department"));
		e.setFirstName(rs.getString("firstName"));
		e.setId(rs.getLong("id"));
		e.setLastName(rs.getString("lastName"));
		e.setSalary(rs.getBigDecimal("salary"));
		return e;
	}

}

