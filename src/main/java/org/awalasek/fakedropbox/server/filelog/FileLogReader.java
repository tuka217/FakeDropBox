package org.awalasek.fakedropbox.server.filelog;

import java.nio.file.Path;
import java.util.Map;

public interface FileLogReader {
    Map<Path, String> readLogFiles();
    String splitPath(Path pathToSplit, String username);
}
