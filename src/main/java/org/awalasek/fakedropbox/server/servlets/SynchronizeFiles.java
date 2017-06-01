package org.awalasek.fakedropbox.server.servlets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.awalasek.fakedropbox.server.filelog.CsvLogReader;
import org.awalasek.fakedropbox.server.filelog.FileLogReader;
import org.json.JSONObject;

/**
 * Servlet implementation class SynchronizeFilesServlet
 */
@WebServlet("/synchronizeFiles")
public class SynchronizeFiles extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected static Logger logger;

    private FileLogReader logFileReader = new CsvLogReader();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SynchronizeFiles() {
        super();
        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        Map<String, String> mapFileToUsername = new ConcurrentHashMap<>();
        for (Entry<Path, String> entry : logFileReader.readLogFiles().entrySet()) {
            if (entry.getValue().equals(username)) {
                String splittedPath = logFileReader.splitPath(entry.getKey(), username);
                mapFileToUsername.put(splittedPath, entry.getValue());
            }
        }

        StringBuilder builder = new StringBuilder();
        for (Entry<String, String> entry : mapFileToUsername.entrySet()) {
            builder.append(entry.getKey() + "; ");
        }
        logger.info(builder.toString());

        response.setContentType("application/json");
        JSONObject jsonObject = new JSONObject(mapFileToUsername);
        logger.info(jsonObject.toString());
        response.getWriter().write(jsonObject.toString());
    }
}
