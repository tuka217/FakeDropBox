package org.awalasek.fakedropbox.server.filelog;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.awalasek.fakedropbox.server.TaskScheduler;

class CsvRemoveLogHandler extends CsvLogWriter implements FileLogWriter {

    @Override
    public void updateLog(String username, String filename) {
        for (int i = 1; i <= TaskScheduler.THREAD_POOL_SIZE; i++) {
            try {
                logFile = Paths.get(getLogDirPath(i));
                mapFileToUsername = readLogFile(logFile);

                Path pathToFile = Paths.get(getFilePath(i, username, filename));
                mapFileToUsername.remove(pathToFile);
                updateLogFile();
            } catch (NullPointerException e) {
                // do nothing
            } catch (FileNotFoundException e) {
                // do nothing
            }
        }
    }
    
    private String getLogDirPath(Integer threadId) {
        return getFileDirPath(threadId) + LOG_NAME;
    }

}
