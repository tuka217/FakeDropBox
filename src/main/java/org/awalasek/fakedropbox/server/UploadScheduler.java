package org.awalasek.fakedropbox.server;

import org.awalasek.fakedropbox.common.FileChange;

interface UploadScheduler {
    void addNewUpload(FileChange request);
}
