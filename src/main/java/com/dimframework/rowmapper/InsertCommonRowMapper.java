package com.dimframework.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class InsertCommonRowMapper implements RowMapper<String> {

	private final String delimiter;

	private final String recordTerminator;

	public InsertCommonRowMapper(final String delimiter, final String recordTerminator) {
		super();
		this.delimiter = delimiter;
		this.recordTerminator = recordTerminator;
	}

	@Override
	public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		Assert.notNull(delimiter, "The delimiter cannot be null");
		Assert.hasLength(delimiter, "The delimiter cannot be blank");
		StringBuffer rowData = new StringBuffer();
		int columnCount = rs.getMetaData().getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			String dataItem = rs.getString(i);
			if (i < columnCount) {
				if (dataItem != null)
					rowData.append(dataItem).append(delimiter);
				else
					rowData.append(delimiter);
			} else if (i == columnCount && dataItem != null) {
				rowData.append(dataItem).append(delimiter);
			} else {
				rowData.append(recordTerminator);
			}
		}
		return rowData.toString();
	}

	public String getDelimiter() {
		return delimiter;
	}

}