package org.awalasek.fakedropbox.server;

import org.awalasek.fakedropbox.common.FileChange;

class TaskFactory {

    public AbstractTask getTask(FileChange changeRequest) {
        if (changeRequest == null) {
            return null;
        }

        switch (changeRequest.getChangeType()) {
        case CREATE:
            return new CreateTask(changeRequest);
        case MODIFY:
            return new ModifyTask(changeRequest);
        case REMOVE:
            return new RemoveTask(changeRequest);
        }

        return null;
    }
}