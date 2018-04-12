package dim.processing.poc.tasklet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.util.file.Files;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component("archivePinCodeFileTasklet")
public class ArchivePinCodeFileTasklet implements Tasklet {

	private String archiveFilesDir;

	private String inboundFileQualifiedName;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss-SSSXXX");

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		// copy the source file to the archive directory
		File pinCodeDataFile = new File(this.inboundFileQualifiedName);
		// rename the file with timestamp
		File nameAfterRenaming = new File(getNewFileName(this.archiveFilesDir.concat("/PincodeArchiveDir/"), pinCodeDataFile.getName()));
		Files.copy(pinCodeDataFile, nameAfterRenaming);
		Files.delete(this.inboundFileQualifiedName);
		return RepeatStatus.FINISHED;
	}

	private String getNewFileName(String archiveFilesDir, String oldFileName) {
		String now = sdf.format((new Date()));
		// find occurrence of filename separator .
		int position = oldFileName.indexOf(".");
		String newFileName = null;
		switch (position) {
		case -1:
			newFileName = oldFileName.concat("_".concat(now));
			break;
		default:
			StringBuffer fileName = new StringBuffer(oldFileName.substring(0, position));
			StringBuffer extName = new StringBuffer(oldFileName.substring(position));
			newFileName = archiveFilesDir.concat(fileName.append("_").append(now).append(extName.toString()).toString());
		}
		return newFileName;
	}

	public String getArchiveFilesDir() {
		return archiveFilesDir;
	}

	public void setArchiveFilesDir(String archiveFilesDir) {
		this.archiveFilesDir = archiveFilesDir;
	}

	public String getInboundFileQualifiedName() {
		return inboundFileQualifiedName;
	}

	public void setInboundFileQualifiedName(String inboundFileQualifiedName) {
		this.inboundFileQualifiedName = inboundFileQualifiedName;
	}
	
}
