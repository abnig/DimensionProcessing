package dim.processing.poc.tasklet;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component("createArchiveDirTasklet")
public class CreateArchiveDirTasklet implements Tasklet {

	private String archiveFilesDir;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		File file = new File(archiveFilesDir);
		String pinCodeFileArchiveDir = archiveFilesDir.concat("/PincodeArchiveDir");
		File file2 = new File(pinCodeFileArchiveDir);
		if (!file.exists()) {
			file.mkdir();
		}

		if (!file2.exists()) {
			file2.mkdir();
		}
		return RepeatStatus.FINISHED;
	}

	public String getArchiveFilesDir() {
		return archiveFilesDir;
	}

	public void setArchiveFilesDir(String archiveFilesDir) {
		this.archiveFilesDir = archiveFilesDir;
	}
	
}
