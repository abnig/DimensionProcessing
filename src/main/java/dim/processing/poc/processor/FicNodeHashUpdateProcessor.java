package dim.processing.poc.processor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import dim.processing.poc.input.FicNodeHash;
import dim.processing.poc.output.FicNodeDimensionHash;

@Component("ficNodeHashUpdateProcessor")
@Scope(value = "step")
public class FicNodeHashUpdateProcessor implements ItemProcessor<FicNodeHash, List<FicNodeDimensionHash>> {

	@Override
	public List<FicNodeDimensionHash> process(FicNodeHash ficNodeHash) throws Exception {
		List<FicNodeDimensionHash> list = new ArrayList<>();
		FicNodeDimensionHash ficNodeDimensionHash = new FicNodeDimensionHash(ficNodeHash);
		ficNodeDimensionHash.setCreationDate(new Date());
		ficNodeDimensionHash.setCreatorId("SYS");
		ficNodeDimensionHash.setLastWriteDate(new Date());
		ficNodeDimensionHash.setLastWriteId("SYS");
		ficNodeDimensionHash.setEffectiveStartDate(ficNodeHash.getEffectiveDate());
		ficNodeDimensionHash.setEffectiveEndDate(new Timestamp(new Date().getTime()));
		ficNodeDimensionHash.setIsActiveFlag("N");
		list.add(ficNodeDimensionHash);
		ficNodeDimensionHash = new FicNodeDimensionHash(ficNodeHash);
		ficNodeDimensionHash.setCreationDate(new Date());
		ficNodeDimensionHash.setCreatorId("SYS");
		ficNodeDimensionHash.setLastWriteDate(new Date());
		ficNodeDimensionHash.setLastWriteId("SYS");
		ficNodeDimensionHash.setEffectiveStartDate(new Date());
		ficNodeDimensionHash.setEffectiveEndDate(Timestamp.from(LocalDate.of(2999, Month.DECEMBER, 31).atStartOfDay().atZone(ZoneId.of("-05:00")).toInstant()));
		ficNodeDimensionHash.setIsActiveFlag("Y");
		list.add(ficNodeDimensionHash);
		return list;
	}

}
