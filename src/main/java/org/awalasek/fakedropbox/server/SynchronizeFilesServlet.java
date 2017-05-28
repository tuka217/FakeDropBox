package org.awalasek.fakedropbox.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SynchronizeFilesServlet
 */
@WebServlet("/synchronizeFiles")
public class SynchronizeFilesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String PATH_TO_STORAGE = "webapps/FileStorage/thread-";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SynchronizeFilesServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
    }

    private void readLogFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(logFile.toString()));

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
    }

}
