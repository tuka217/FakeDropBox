package org.awalasek.fakedropbox.server.tasks;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.awalasek.fakedropbox.common.ChangeType;
import org.awalasek.fakedropbox.common.FileChange;
import org.awalasek.fakedropbox.server.TaskScheduler;
import org.awalasek.fakedropbox.server.filelog.CsvLogHandlerFactory;

class RemoveTask extends AbstractTask {

    public RemoveTask(FileChange request) {
        super(request);
    }

    @Override
    protected final void fakeFileOperation() {
        for (int i = 1; i <= TaskScheduler.THREAD_POOL_SIZE; i++) {
            Path path = Paths.get(PATH_TO_STORAGE + i + "/" + username + "/" + filename);
            if (path.toFile().exists()) {
                path.toFile().delete();
                fileLogger.updateLog(username, filename);
                logger.info("Removed file: " + path.toAbsolutePath().toString());
            }
        }
    }

    @Override
    protected void createFileLogger() {
        try {
            if (fileLogger == null) {
                CsvLogHandlerFactory csvLogHandlerFactory = new CsvLogHandlerFactory();
                fileLogger = csvLogHandlerFactory.getLogWriter(ChangeType.REMOVE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
