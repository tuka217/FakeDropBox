package org.awalasek.fakedropbox.server.tasks;

import org.awalasek.fakedropbox.common.FileChange;

public class TaskFactory {

    public AbstractTask getTask(FileChange changeRequest) {
        if (changeRequest == null) {
            return null;
        }

        switch (changeRequest.getChangeType()) {
        case CREATE:
            return new CreateTask(changeRequest);
        case REMOVE:
            return new RemoveTask(changeRequest);
        }

        return null;
    }
}