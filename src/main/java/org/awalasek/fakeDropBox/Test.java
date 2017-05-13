package org.awalasek.fakeDropBox;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Test
 */
public class Test extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static Logger logger;
    private UploadScheduler uploadScheduler;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        logger = Logger.getLogger("Test");
        uploadScheduler = new UploadSchedulerImpl();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().append("Served at: ").append(request.getContextPath());

        try {
            FileUploadRequest fileUploadRequest = new FileUploadRequest(request);
            uploadScheduler.addNewUpload(fileUploadRequest);
            logger.info("New upload added, username=" + fileUploadRequest.getUsername() + ", fileAmount="
                    + fileUploadRequest.getFileAmount());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
