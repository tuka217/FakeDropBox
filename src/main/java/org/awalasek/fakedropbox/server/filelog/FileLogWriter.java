package org.awalasek.fakedropbox.server.filelog;

public interface FileLogWriter {
    void updateLog(String username, String filename);
}
