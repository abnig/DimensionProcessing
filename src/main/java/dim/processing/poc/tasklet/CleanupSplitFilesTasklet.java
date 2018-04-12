package dim.processing.poc.tasklet;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component("cleanupSplitFilesTasklet")
public class CleanupSplitFilesTasklet implements Tasklet {
	
	private String splitFileDir;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		File file = new File(this.splitFileDir);
		if(file.isDirectory()) {
			FileUtils.deleteDirectory(file);
			return RepeatStatus.FINISHED;
		}
		else {
			return RepeatStatus.CONTINUABLE;
		}
	}

	public String getSplitFileDir() {
		return splitFileDir;
	}

	public void setSplitFileDir(String splitFileDir) {
		this.splitFileDir = splitFileDir;
	}
	
}
