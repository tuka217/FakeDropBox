package org.awalasek.fakedropbox.server;

import org.awalasek.fakedropbox.common.FileChange;

public interface UploadScheduler {
    void addNewUpload(FileChange request);
}
