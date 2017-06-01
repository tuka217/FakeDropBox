package org.awalasek.fakedropbox.server.filelog;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.awalasek.fakedropbox.server.TaskScheduler;

public class CsvLogReader extends AbstractCsvLogHandler implements FileLogReader {

    private static String getFileDirPath(int threadId) {
        return PATH_TO_STORAGE + threadId + "/";
    }
    
    @Override
    public Map<Path, String> readLogFiles() {
        Map<Path, String> mapFileToUsername = new ConcurrentHashMap<>();

        for (int i = 1; i <= TaskScheduler.THREAD_POOL_SIZE; ++i) {
            try {
                Map<Path, String> tempMap = readLogFile(Paths.get(getFileDirPath(i) + LOG_NAME));
                mapFileToUsername.putAll(tempMap);
            } catch (FileNotFoundException e) {
                // do nothing
            }
        }
        return mapFileToUsername;
    }

    @Override
    public String splitPath(Path pathToSplit, String username) {
        return pathToSplit.toString().split(getPathToSplit(username))[1];
    }
    
    private String getPathToSplit(String username) {
        return PATH_TO_STORAGE + "./" + username + "/";
    }

}
