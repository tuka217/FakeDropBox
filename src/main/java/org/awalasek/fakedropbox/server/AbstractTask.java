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

abstract class AbstractTask implements Runnable {

    protected static final String PATH_TO_STORAGE = "webapps/FileStorage/thread-";
    protected static final Set<PosixFilePermission> FILE_PERMISSIONS = PosixFilePermissions.fromString("rwxrwxr-x");

    protected static Logger logger;

    protected Integer threadNum;
    protected String username;
    protected String filename;
    protected FileLogger fileLogger;

    protected abstract void fakeFileOperation();

    public AbstractTask(String username, String filename) {
        logger = Logger.getLogger(this.getClass().getName());
        this.username = username;
        this.filename = filename;
        this.threadNum = 0;
    }

    @Override
    public final void run() {
        getThreadNumberIfNotSet();
        createFileLogger();
        createDirectoryIfDoesNotExist();
        updateFile();

        logger.info(this.getClass().getName() + " finished, username=" + username + ", filename=" + filename
                + ", threadNum=" + threadNum);
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

    private final void updateFile() {
        try {
            Thread.sleep(new Random().nextInt(3) * 1000);
            fakeFileOperation();
            fileLogger.updateLog(username, filename);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
