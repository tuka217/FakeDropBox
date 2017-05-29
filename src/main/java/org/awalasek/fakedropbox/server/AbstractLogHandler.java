package org.awalasek.fakedropbox.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

abstract class AbstractLogHandler {

    protected static final String PATH_TO_STORAGE = "webapps/FileStorage/thread-";
    protected static final Set<PosixFilePermission> FILE_PERMISSIONS = PosixFilePermissions.fromString("rwxrwxr-x");
    protected static final String LOG_NAME = ".log.csv";
    protected static final String SEP = ";";

    protected Map<Path, String> readLogFile(Path logFile) throws FileNotFoundException {
        Map<Path, String> mapFileToUsername = null;
        BufferedReader br = null;
        FileReader fr = new FileReader(logFile.toString());
        try {
            br = new BufferedReader(fr);
            mapFileToUsername = new ConcurrentHashMap<>();

            String line;
            while ((line = br.readLine()) != null) {
                String[] entry = line.split(SEP);
                mapFileToUsername.put(Paths.get(entry[0]), entry[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return mapFileToUsername;
    }
}
