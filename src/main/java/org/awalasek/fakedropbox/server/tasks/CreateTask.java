package org.awalasek.fakedropbox.server.tasks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;

import org.awalasek.fakedropbox.common.ChangeType;
import org.awalasek.fakedropbox.common.FileChange;
import org.awalasek.fakedropbox.server.filelog.CsvLogHandlerFactory;

class CreateTask extends AbstractTask {

    public CreateTask(FileChange request) {
        super(request);
    }

    @Override
    protected final void fakeFileOperation() {
        Path directoryPath = Paths.get(PATH_TO_STORAGE + threadNum + "/" + username);
        Path filePath = Paths.get(PATH_TO_STORAGE + threadNum + "/" + username + "/" + filename);
        try {
            Files.createDirectories(directoryPath, PosixFilePermissions.asFileAttribute(FILE_PERMISSIONS));
            logger.info("Created directory: " + directoryPath.toAbsolutePath().toString());
            filePath.toFile().getParentFile().mkdirs();
            Files.createFile(filePath, PosixFilePermissions.asFileAttribute(FILE_PERMISSIONS));
            fileLogger.updateLog(username, filename);
            logger.info("Created file: " + filePath.toAbsolutePath().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void createFileLogger() {
        try {
            if (fileLogger == null) {
                CsvLogHandlerFactory csvLogHandlerFactory = new CsvLogHandlerFactory(threadNum);
                fileLogger = csvLogHandlerFactory.getLogWriter(ChangeType.CREATE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
