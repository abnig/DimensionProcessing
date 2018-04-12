package dim.processing.poc.processor;

import java.util.Date;

import org.getopt.util.hash.FNV1;
import org.springframework.batch.item.ItemProcessor;

import dim.processing.poc.input.FicNode;
import dim.processing.poc.input.FicNodeHash;

public class FicNodeProcessor implements ItemProcessor<FicNode, FicNodeHash> {
	
	private FNV1 hashGeneratorClass;
	
	@Override
	public FicNodeHash process(FicNode ficNode) throws Exception {
		FicNodeHash ficNodeBase = new FicNodeHash(ficNode);
		ficNodeBase.setCreationDate(new Date());
		ficNodeBase.setCreatorId("SYS");
		ficNodeBase.setHashColumns(new Long(ficNode.hashCodeColumns()).toString());
		ficNodeBase.setHashPK(new Long(ficNode.hashCode()).toString());
		ficNodeBase.setLastWriteDate(new Date());
		ficNodeBase.setLastWriteId("SYS");
		return ficNodeBase;
	}

	public FNV1 getHashGeneratorClass() {
		return hashGeneratorClass;
	}

	public void setHashGeneratorClass(FNV1 hashGeneratorClass) {
		this.hashGeneratorClass = hashGeneratorClass;
	}
	
}
