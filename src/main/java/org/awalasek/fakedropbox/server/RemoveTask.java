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
        for (int i = 1; i <= TaskScheduler.THREAD_POOL_SIZE; i++) {
            Path path = Paths.get(PATH_TO_STORAGE + i + "/" + username + "/" + filename);
            if (path.toFile().exists()) {
                path.toFile().delete();
            }
        }
    }
}
