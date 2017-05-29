package org.awalasek.fakedropbox.server;

import java.nio.file.Path;
import java.util.Map;

interface LogFileReader {
    Map<Path, String> readLogFiles();
    String splitPath(Path pathToSplit, String username);
}
