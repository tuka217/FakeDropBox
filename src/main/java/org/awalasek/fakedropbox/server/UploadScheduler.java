package org.awalasek.fakedropbox.server;

interface UploadScheduler {
    void addNewUpload(FileUploadRequest request);
}
