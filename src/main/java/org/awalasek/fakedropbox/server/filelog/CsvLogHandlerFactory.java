package org.awalasek.fakedropbox.server.filelog;

import java.io.IOException;

import org.awalasek.fakedropbox.common.ChangeType;

public class CsvLogHandlerFactory {

    private Integer threadId;

    public CsvLogHandlerFactory() {
        this.threadId = 0;
    }

    public CsvLogHandlerFactory(Integer threadId) {
        this.threadId = threadId;
    }

    public FileLogWriter getLogWriter(ChangeType changeRequest) throws IOException {
        if (changeRequest == null) {
            return null;
        }

        switch (changeRequest) {
        case CREATE:
            return new CsvAddLogHandler(threadId);
        case REMOVE:
            return new CsvRemoveLogHandler();
        }

        return null;
    }
}
