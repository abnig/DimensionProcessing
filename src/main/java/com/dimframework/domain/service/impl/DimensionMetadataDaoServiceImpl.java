package com.dimframework.domain.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dimframework.domain.DeleteOperationMetadata;
import com.dimframework.domain.DimensionMetadata;
import com.dimframework.domain.DimensionProcessLog;
import com.dimframework.domain.HashFileMetadata;
import com.dimframework.domain.UpdateOperationMetadata;
import com.dimframework.domain.dao.DimensionMetadataDao;
import com.dimframework.domain.pojo.PopulateHashBatchJobMetadata;
import com.dimframework.domain.service.DimensionMetadataService;

@Service("dimensionMetadataDaoServiceImpl")
public class DimensionMetadataDaoServiceImpl implements DimensionMetadataService {

	private static Logger logger = Logger.getLogger(DimensionMetadataDaoServiceImpl.class);

	@Autowired
	private DimensionMetadataDao dimensionMetadataDaoImpl;
	
	@Resource
	@Qualifier("fieldDelimiter")
	private String fieldDelimiter;

	@Resource
	@Qualifier("recordTerminator")
	private String recordTerminator;
	
	@Resource
	@Qualifier("hashDataFilesBasePath")
	private String hashDataFilesBasePath;
	
	@Resource
	@Qualifier("schemaName")
	private String schemaName;

	@Override
	public List<DimensionMetadata> getByDomainName(String domainName, Date effectiveEndDate) {
		Optional<List<DimensionMetadata>> list = this.dimensionMetadataDaoImpl.getByDomainName(domainName);
		List<DimensionMetadata> list2 = new ArrayList<DimensionMetadata>();
		list.ifPresent(dimensionMetadataList -> dimensionMetadataList.forEach(c -> list2.add(new DimensionMetadata(c, effectiveEndDate, fieldDelimiter,  recordTerminator, hashDataFilesBasePath))));
		return list2;
	}

	@Override
	public void truncateTable(String tableName) {
		this.dimensionMetadataDaoImpl.truncateTable(tableName);
	}
	
	@Override
	public UpdateOperationMetadata generateUpdateOperationBatchJobMetadata(DimensionMetadata dimensionMetadata,
			Long processId) {
		/*
		SELECT NH.*
FROM dim.employee_dim DH RIGHT OUTER JOIN dim.employee_HASH 
		NH ON DH.HASH_PK = NH.HASH_PK AND DH.IS_ACTV_FL = 'Y'
        WHERE DH.HASH_COL <> NH.HASH_COL;
        
		 */
		StringBuilder selectSQL = new StringBuilder(" SELECT DH.").append(dimensionMetadata.getSourceTablePKColumns()).append(" , ").append(dimensionMetadata.getSourceTableDataColumns());
		selectSQL.append(" FROM " ).append(schemaName).append(".").append(dimensionMetadata.getDimTable()).append(" DH ");
		selectSQL.append(" RIGHT OUTER JOIN ").append(dimensionMetadata.getSourceTableHash()).append(" NH ON ");
		selectSQL.append(" DH.").append(dimensionMetadata.getPrimaryKeyHashColumn()).append(" = ").append(" NH.").append(dimensionMetadata.getPrimaryKeyHashColumn()).append(" AND ");
		selectSQL.append(" DH.ISACTV_FL = 'Y' WHERE ").append(" DH.").append(dimensionMetadata.getDataFieldsHashColumn()).append(" <> ").append(" NH.").append(dimensionMetadata.getDataFieldsHashColumn()).append(";");
		
		//         UPDATE dim.employee_dim DH SET DH.IS_ACTV_FL = 'N', EFF_END_DT = :effEndDate WHERE DH.HASH_PK IN (:colHashPK);
		StringBuilder updateSQL = new StringBuilder(" UPDATE ").append(dimensionMetadata.getDimTable()).append(" DH ");
		updateSQL.append(" SET DH.IS_ACTV_FL = 'N', EFF_END_DT = :effectiveEndDate WHERE DH.HASH_PK = :listHashPK; ");
		
		/*
		LOAD DATA LOCAL INFILE '/dimension-processing/hash-datafiles/test.dat' INTO TABLE dim.employee_hash 
		FIELDS TERMINATED BY '|'
		LINES TERMINATED BY '~'
		(emp_ID,
		name,
		salary,
		HASH_PK,
		HASH_COL);
	*/
		String fileName = new StringBuilder(processId.toString()).append(dimensionMetadata.getDimTable()).append("_insert").toString();
		StringBuilder sql = new StringBuilder("LOAD DATA LOCAL INFILE '").append(fileName).append("'");
		sql.append(" INTO TABLE ").append(schemaName).append(".").append(dimensionMetadata.getSourceTableHash());
		sql.append(" FIELDS TERMINATED BY '").append(dimensionMetadata.getFieldDelimiter()).append("'"); 
		sql.append(" LINES TERMINATED BY '").append(dimensionMetadata.getRecordTerminator()).append("' (");
		sql.append(dimensionMetadata.getSourceTablePKColumns()).append(", ");
		sql.append(dimensionMetadata.getSourceTableDataColumns()).append(", ");
		sql.append(dimensionMetadata.getPrimaryKeyHashColumn()).append(", ");
		sql.append(dimensionMetadata.getDataFieldsHashColumn()).append(");");
		String loadSQLString = sql.toString();
		logger.info("SQL Command to load into " + dimensionMetadata.getSourceTableHash() + " table is: " + loadSQLString);
		UpdateOperationMetadata updateOperationMetadata = new UpdateOperationMetadata(dimensionMetadata, schemaName, processId, fileName, selectSQL.toString(), updateSQL.toString(), loadSQLString);
		return updateOperationMetadata;
	}

	@Override
	public DeleteOperationMetadata generateDeleteOperationBatchJobMetadata(DimensionMetadata dimensionMetadata,  Long processId) {
		// SELECT DH.HASH_PK 		FROM dim.employee_dim DH  LEFT OUTER JOIN dim.employee_hash NH  ON DH.HASH_PK = NH.HASH_PK  WHERE DH.IS_ACTV_FL = 'Y' AND NH.HASH_PK IS NULL;
		StringBuilder selectSQL = new StringBuilder(" SELECT DH.HASH_PK ");
		selectSQL.append(" FROM ").append(dimensionMetadata.getDimTable()).append(" DH ");
		selectSQL.append(" LEFT OUTER JOIN  ").append(dimensionMetadata.getSourceTableHash()).append(" NH ");
		selectSQL.append(" ON DH.HASH_PK = NH.HASH_PK ");
		selectSQL.append(" WHERE DH.IS_ACTV_FL = 'Y' AND NH.HASH_PK IS NULL; ");
		
		//         UPDATE dim.employee_dim DH SET DH.IS_ACTV_FL = 'N', EFF_END_DT = :effEndDate WHERE DH.HASH_PK IN (:colHashPK);
		StringBuilder updateSQL = new StringBuilder(" UPDATE ").append(dimensionMetadata.getDimTable()).append(" DH ");
		updateSQL.append(" SET DH.IS_ACTV_FL = 'N', EFF_END_DT = :effectiveEndDate WHERE DH.HASH_PK = :listHashPK; ");
		DeleteOperationMetadata p = new DeleteOperationMetadata(dimensionMetadata, schemaName, processId, selectSQL.toString(), updateSQL.toString());
		return p;
	}

	@Override
	public PopulateHashBatchJobMetadata generatePopulateHashBatchJobMetadata(DimensionMetadata dimensionMetadata, DimensionProcessLog runLog) {
		StringBuilder selectSQL = new StringBuilder("SELECT concat(");
		// concat(NODE_ID, '\'PK_END_MARKER\'')
		selectSQL.append(dimensionMetadata.getSourceTablePKColumns()).append(", 'PK_END_MARKER'), ");
		selectSQL.append(dimensionMetadata.getSourceTableDataColumns()).append(" ");
		selectSQL.append(" FROM ").append(dimensionMetadata.getSourceTable());
		
		StringBuilder insertSQL = new StringBuilder("INSERT INTO ").append(dimensionMetadata.getSourceTableHash()).append("(");
		insertSQL.append(dimensionMetadata.getSourceTablePKColumns()).append(",");
		insertSQL.append(dimensionMetadata.getSourceTableDataColumns()).append(",");
		insertSQL.append("HASH_PK, HASH_COL) VALUES( ");
		insertSQL.append(generateValuesClauseForPopulateHashBatchJob(dimensionMetadata));
		insertSQL.append(" );");
		String fullyQualifiedFileName = 	this.hashDataFilesBasePath.concat(runLog.getProcessId().toString()).concat(dimensionMetadata.getSourceTable());
		PopulateHashBatchJobMetadata p = new PopulateHashBatchJobMetadata(dimensionMetadata, selectSQL.toString(),  insertSQL.toString(), runLog, fullyQualifiedFileName);
		return p;
	}
	
	private String generateValuesClauseForPopulateHashBatchJob(DimensionMetadata dimensionMetadata){
		StringBuilder columnNamesCSV = new StringBuilder(dimensionMetadata.getSourceTablePKColumns()).append(",").append(dimensionMetadata.getSourceTableDataColumns());
		String[] str = columnNamesCSV.toString().split(",");
		StringBuilder valuesClause = new StringBuilder();
		int s = 1;
		for(s = 1; s<= str.length + 1; s++) { 
			valuesClause.append(":").append(s).append(",");
		}
		
		valuesClause.append(":" + s++);
		return valuesClause.toString();
	}
	
	@Override
	public String concatenatePKHashAndDataColumnHash(String rowData) {
		// remove the record terminator char
		String pkHash = generatePKHash(rowData);// ok
		String dataColumnHash = generateDataColumnHash(rowData);
		String row = rowData.replaceAll(recordTerminator, fieldDelimiter);
		String row1 = row.replaceAll("PK_END_MARKER", "");
		StringBuilder rowDataHash = new StringBuilder(row1);
		rowDataHash.append(pkHash).append(fieldDelimiter);
		rowDataHash.append(dataColumnHash).append(fieldDelimiter);
		logger.debug("row data to be written to file: " + rowDataHash);
		return rowDataHash.toString();
	}	

	private String generateHashCode(String string) {
		return org.apache.commons.codec.digest.DigestUtils.sha256Hex(string);
	}	

	private String generatePKHash(String rowData) {
		String[] str = seperatePKAndDataColumns(rowData);
		String pk = removeLastComma(str[0]);
		String hashPK = this.generateHashCode(pk);
		logger.debug("Primary Key Value is " + pk + "; hash=" +hashPK);
		return hashPK;
	}


	private String generateDataColumnHash(String rowDataCSV) {
		logger.debug("Row Data Received " + rowDataCSV);
		String[] str = seperatePKAndDataColumns(rowDataCSV);
		logger.debug("Row Data After separating PK & Data Columns " + str[0] + ":::" + str[1]);
		String dataCols = new String(str[1]);
		String dataCols1 = new String(dataCols.replaceFirst(fieldDelimiter, ""));
		String dataCols2 = new String(dataCols1.replace(recordTerminator, ""));
		String rowAfterManipulation = removeFirstComma(dataCols2);
		logger.debug("Data Cols before Hashing " + rowAfterManipulation);
		String hashDataCols = this.generateHashCode(rowAfterManipulation);
		logger.debug("Data Cols before Hashing " + rowAfterManipulation + "hash=" + hashDataCols);
		return hashDataCols;
	}


	private String[] seperatePKAndDataColumns(String rowDataCSV) {
		String pkEndMarker = "PK_END_MARKER";
		String[] str = rowDataCSV.split(pkEndMarker);
		logger.debug("PK " + str[0]);
		logger.debug("Data Columns " + str[1]);
		return str;
	}

	private String removeLastComma(String str) {
		if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == this.fieldDelimiter.charAt(0)) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	private String removeFirstComma(String str) {
		if (str != null && str.length() > 0 && str.charAt(0) == this.fieldDelimiter.charAt(0))
			return str.substring(1);
		else
			return str;
	}

	@Override
	public void executeLoadIntoHash(HashFileMetadata hashFileMetadata) {
		this.dimensionMetadataDaoImpl.executeLoadIntoHash(hashFileMetadata);
	}

}
