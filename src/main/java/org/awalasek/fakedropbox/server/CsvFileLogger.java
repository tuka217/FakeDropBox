package org.awalasek.fakedropbox.server;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

class CsvFileLogger implements FileLogger {

    private static final String PATH_TO_STORAGE = "webapps/FileStorage/thread-";
    private static final Set<PosixFilePermission> FILE_PERMISSIONS = PosixFilePermissions.fromString("rwxrwxr-x");

    private static final String LOG_NAME = ".log.csv";
    private static final String SEP = ";";
    private Map<Path, String> mapFileToUsername;
    private Path logFile;
    private String fileDirPath;

    public CsvFileLogger(Integer threadId) throws IOException {
        fileDirPath = PATH_TO_STORAGE + threadId + "/";
        logFile = Paths.get(fileDirPath + LOG_NAME);
        mapFileToUsername = new ConcurrentHashMap<>();

        createFileIfDoesNotExist();
        readLogFile();
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

    private void readLogFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(logFile.toString()));

            String line;
            while ((line = br.readLine()) != null) {
                String[] entry = line.split(SEP);
                mapFileToUsername.put(Paths.get(entry[0]), entry[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
