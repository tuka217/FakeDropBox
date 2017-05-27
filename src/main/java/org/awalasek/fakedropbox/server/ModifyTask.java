package org.awalasek.fakedropbox.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;

class ModifyTask extends AbstractTask {

    public ModifyTask(FileUploadRequest request) {
        super(request.getUsername(), request.getFilename());
    }

    @Override
    protected final void fakeFileOperation() {
        Path path = Paths.get(PATH_TO_STORAGE + threadNum + "/" + username + "/" + filename);
        if (path.toFile().exists()) {
            path.toFile().delete();
        }
        try {
            Files.createFile(path, PosixFilePermissions.asFileAttribute(FILE_PERMISSIONS));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
