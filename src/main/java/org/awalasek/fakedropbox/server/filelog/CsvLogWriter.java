package org.awalasek.fakedropbox.server.filelog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Map;
import java.util.Map.Entry;

abstract class CsvLogWriter extends AbstractCsvLogHandler {

    protected Map<Path, String> mapFileToUsername;
    protected Path logFile;

    protected void createFileIfDoesNotExist() throws IOException {
        if (!logFile.toFile().exists()) {
            Files.createDirectories(logFile.getParent(),
                    PosixFilePermissions.asFileAttribute(FILE_PERMISSIONS));
            Files.createFile(logFile, PosixFilePermissions.asFileAttribute(FILE_PERMISSIONS));
        }
    }

    protected void updateLogFile() {
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

    protected String getFileDirPath(Integer threadId) {
        return PATH_TO_STORAGE + threadId + "/";
    }

    protected String getFilePath(Integer threadId, String username, String filename) {
        return getFileDirPath(threadId) + username + "/" + filename;
    }
}
