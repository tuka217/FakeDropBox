package org.awalasek.fakedropbox.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

class UploadTask implements Runnable {

    public static final String PATH_TO_STORAGE = "webapps/FileStorage/thread-";
    public static final Set<PosixFilePermission> FILE_PERMISSIONS =
            PosixFilePermissions.fromString("rwxrwxr-x");
    
    private static Logger logger;
    
    private Integer threadNum;
    private String username;
    private String filename;
    private FileLogger fileLogger;

    public UploadTask(String username, String filename) {
        logger = Logger.getLogger("Task");
        this.username = username;
        this.filename = filename;
        this.threadNum = 0;
    }

    @Override
    public void run() {
        getThreadNumberIfNotSet();
        createFileLogger();
        createDirectoryIfDoesNotExist();
        fakeFileUploading();

        logger.info("Upload finished, username=" + username + ", threadNum=" + threadNum);
    }

    private void getThreadNumberIfNotSet() {
        if (threadNum == 0) {
            String threadName = Thread.currentThread().getName();
            threadNum = Character.getNumericValue(threadName.charAt(threadName.length() - 1));
        }
    }
    
    private void createFileLogger() {
        try {
            if (fileLogger == null)
                fileLogger = new CsvFileLogger(threadNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void createDirectoryIfDoesNotExist() {
        try {
            Path path = Paths.get(PATH_TO_STORAGE + threadNum + "/" + username);
            if (!path.toFile().exists()) {
                Files.createDirectories(path, PosixFilePermissions.asFileAttribute(FILE_PERMISSIONS));
                logger.info("Created directory: " + path.toAbsolutePath().toString());
            }
        } catch (IOException e) {
            // do nothing
        }
    }

    private void fakeFileUploading() {
        try {
            Thread.sleep(new Random().nextInt(3) * 1000);
            createFile();
            fileLogger.updateLog(username, filename);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void createFile() {
        Path path = Paths.get(PATH_TO_STORAGE + threadNum + "/" + username + "/" + filename);
        if (path.toFile().exists()) {
            path.toFile().delete();
        }
        try {
            Files.createDirectories(path, PosixFilePermissions.asFileAttribute(UploadTask.FILE_PERMISSIONS));
            Files.createFile(path, PosixFilePermissions.asFileAttribute(UploadTask.FILE_PERMISSIONS));
        } catch (IOException e) {
        }
        
    }
}
