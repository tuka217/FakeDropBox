package org.awalasek.fakedropbox.server;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.awalasek.fakedropbox.common.FileChange;

class RemoveTask extends AbstractTask {

    public RemoveTask(FileChange request) {
        super(request);
    }

    @Override
    protected final void fakeFileOperation() {
        Path path = Paths.get(PATH_TO_STORAGE + threadNum + "/" + username + "/" + filename);
        if (path.toFile().exists()) {
            path.toFile().delete();
        }
    }
}
