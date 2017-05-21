package org.awalasek.fakeDropBox;

interface UploadScheduler {
    void addNewUpload(FileUploadRequest request);
}
