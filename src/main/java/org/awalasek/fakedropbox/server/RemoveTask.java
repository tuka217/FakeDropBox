package org.awalasek.fakedropbox.server;

import java.nio.file.Path;
import java.nio.file.Paths;

class RemoveTask extends AbstractTask {

    public RemoveTask(FileUploadRequest request) {
        super(request.getUsername(), request.getFilename());
    }

    @Override
    protected final void fakeFileOperation() {
        Path path = Paths.get(PATH_TO_STORAGE + threadNum + "/" + username + "/" + filename);
        if (path.toFile().exists()) {
            path.toFile().delete();
        }
    }
}
