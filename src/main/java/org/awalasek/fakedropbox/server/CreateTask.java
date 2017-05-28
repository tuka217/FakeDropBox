package org.awalasek.fakedropbox.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;

import org.awalasek.fakedropbox.common.FileChange;

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
            Files.createFile(filePath, PosixFilePermissions.asFileAttribute(FILE_PERMISSIONS));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
