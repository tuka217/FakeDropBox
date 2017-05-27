package org.awalasek.fakedropbox.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Test
 */
@WebServlet("fileChange")
public class FileChange extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static Logger logger;
    private UploadScheduler uploadScheduler;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileChange() {
        super();
        logger = Logger.getLogger(this.getClass().getName());
        uploadScheduler = new UploadSchedulerImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().append("Served at: ").append(request.getContextPath());

        try {
            FileUploadRequest fileUploadRequest = new FileUploadRequest(request);
            uploadScheduler.addNewUpload(fileUploadRequest);
            logger.info("New upload added, username=" + fileUploadRequest.getUsername() + ", filename="
                    + fileUploadRequest.getFilename());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
