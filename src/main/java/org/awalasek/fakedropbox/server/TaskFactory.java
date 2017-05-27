package org.awalasek.fakedropbox.server;

import org.awalasek.FakeDropBox.common.ChangeType;

public class TaskFactory {

    public AbstractTask getTask(FileUploadRequest request) {
        if (request == null) {
            return null;
        }

        if (request.getOperationType().equals(ChangeType.CREATE)) {
            return new CreateTask(request);
        } else if (request.getOperationType().equals(ChangeType.MODIFY)) {
            return new ModifyTask(request);
        } else if (request.getOperationType().equals(ChangeType.REMOVE)) {
            return new RemoveTask(request);
        }

        return null;
    }
}