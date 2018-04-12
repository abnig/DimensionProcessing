package dim.processing.poc.processor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import dim.processing.poc.input.FicNodeHash;
import dim.processing.poc.output.FicNodeDimensionHash;

public class FicNodeHashProcessor implements ItemProcessor<FicNodeHash, FicNodeDimensionHash> {

	@Override
	public FicNodeDimensionHash process(FicNodeHash ficNodeHash) throws Exception {
		FicNodeDimensionHash ficNodeDimensionHash = new FicNodeDimensionHash(ficNodeHash);
		ficNodeDimensionHash.setCreationDate(new Date());
		ficNodeDimensionHash.setCreatorId("SYS");
		ficNodeDimensionHash.setLastWriteDate(new Date());
		ficNodeDimensionHash.setLastWriteId("SYS");
		ficNodeDimensionHash.setEffectiveStartDate(new Date());
		ficNodeDimensionHash.setEffectiveEndDate(Timestamp.from(LocalDate.of(2999, Month.DECEMBER, 31).atStartOfDay().atZone(ZoneId.of("-05:00")).toInstant()));
		ficNodeDimensionHash.setIsActiveFlag("Y");
		return ficNodeDimensionHash;
	}

}
