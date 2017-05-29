package org.awalasek.fakedropbox.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Map;
import java.util.Map.Entry;

class CsvFileLogger extends AbstractLogHandler implements FileLogger {
    
    private Map<Path, String> mapFileToUsername;
    private Path logFile;
    private String fileDirPath;

    public CsvFileLogger(Integer threadId) throws IOException {
        fileDirPath = PATH_TO_STORAGE + threadId + "/";
        logFile = Paths.get(fileDirPath + LOG_NAME);

        createFileIfDoesNotExist();
        mapFileToUsername = readLogFile(logFile);
    }

    @Override
    public void updateLog(String username, String filename) {
        Path pathToFile = Paths.get(getFilePath(username, filename));
        mapFileToUsername.put(pathToFile, username);
        updateLogFile();
    }

    private void createFileIfDoesNotExist() throws IOException {
        if (!logFile.toFile().exists()) {
            Files.createDirectories(logFile.getParent(),
                    PosixFilePermissions.asFileAttribute(FILE_PERMISSIONS));
            Files.createFile(logFile, PosixFilePermissions.asFileAttribute(FILE_PERMISSIONS));
        }
    }

    private void updateLogFile() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(logFile.toFile()));
            for (Entry<Path, String> entry : mapFileToUsername.entrySet()) {
                writer.println(entry.getKey().toString() + ";" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

    private String getFilePath(String username, String filename) {
        return fileDirPath + username + "/" + filename;
    }
}
