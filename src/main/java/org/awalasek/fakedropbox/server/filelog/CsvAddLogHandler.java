package org.awalasek.fakedropbox.server.filelog;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class CsvAddLogHandler extends CsvLogWriter implements FileLogWriter {

    private Integer threadId;

    public CsvAddLogHandler(Integer threadId) throws IOException {
        this.threadId = threadId;
        logFile = Paths.get(getFileDirPath(threadId) + LOG_NAME);

        createFileIfDoesNotExist();
        mapFileToUsername = readLogFile(logFile);
    }

    @Override
    public void updateLog(String username, String filename) {
        Path pathToFile = Paths.get(getFilePath(threadId, username, filename));
        mapFileToUsername.put(pathToFile, username);
        updateLogFile();
    }

}
