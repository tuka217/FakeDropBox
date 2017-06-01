package org.awalasek.fakedropbox.server.tasks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import org.awalasek.fakedropbox.common.FileChange;
import org.awalasek.fakedropbox.server.filelog.FileLogWriter;

public abstract class AbstractTask implements Runnable {

    protected static final String PATH_TO_STORAGE = "webapps/FileStorage/thread-";
    protected static final Set<PosixFilePermission> FILE_PERMISSIONS = PosixFilePermissions.fromString("rwxrwxr-x");

    protected static Logger logger;

    protected Integer threadNum;
    protected String username;
    protected String filename;
    protected FileLogWriter fileLogger;

    protected abstract void fakeFileOperation();
    protected abstract void createFileLogger();

    public AbstractTask(FileChange changeRequest) {
        logger = Logger.getLogger(this.getClass().getName());
        this.username = changeRequest.getUsername();
        this.filename = changeRequest.getFilename();
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
