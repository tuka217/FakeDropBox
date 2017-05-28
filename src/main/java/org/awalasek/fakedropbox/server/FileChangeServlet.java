package org.awalasek.fakedropbox.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.awalasek.fakedropbox.common.FileChange;
import org.awalasek.fakedropbox.common.FileChangeFactory;

/**
 * Servlet implementation class Test
 */
@WebServlet("/fileChange")
public class FileChangeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static Logger logger;
    private UploadScheduler uploadScheduler;
    private FileChangeFactory fileChangeFactory;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileChangeServlet() {
        super();
        logger = Logger.getLogger(this.getClass().getName());
        uploadScheduler = new UploadSchedulerImpl();
        fileChangeFactory = new FileChangeFactory();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().append("Served at: ").append(request.getContextPath());

        try {
            FileChange fileChange = fileChangeFactory.getFileChange(request);
            uploadScheduler.addNewUpload(fileChange);

            logger.info("New upload added, username=" + fileChange.getUsername() + ", filename="
                    + fileChange.getFilename());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
