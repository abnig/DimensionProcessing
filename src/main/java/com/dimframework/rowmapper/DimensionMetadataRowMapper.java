package com.dimframework.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dimframework.domain.DimensionMetadata;

public class DimensionMetadataRowMapper implements RowMapper<DimensionMetadata> {

	@Override
	public DimensionMetadata mapRow(ResultSet rs, int rowNum) throws SQLException {
		DimensionMetadata dimensionMetadata = new DimensionMetadata(rs.getInt("MetaDataId"),
				rs.getString("sourceTable"), rs.getString("sourceTableHash"), rs.getString("dimTable"),
				rs.getInt("commitInterval"), rs.getString("sourceTablePKColumns"),
				rs.getString("sourceTableDataColumns"), rs.getString("domainName"), rs.getInt("concurrencyLimit"),
				rs.getString("dataSourceBeanName"), rs.getString("dataSelectionStrategy"), rs.getString("activeFlag"),
				rs.getString("primaryKeyHashColumn"),
				rs.getString("dataFieldsHashColumn"), null, null, null, null, null); 
		// for effective end date which comes as job param);
		return dimensionMetadata;
	}

}
