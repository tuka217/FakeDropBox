package org.awalasek.fakeDropBox;

public interface UploadScheduler {
    void addNewUpload(FileUploadRequest request);
}
