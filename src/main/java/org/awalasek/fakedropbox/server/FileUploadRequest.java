package org.awalasek.fakedropbox.server;

import javax.servlet.http.HttpServletRequest;

class FileUploadRequest {

    public FileUploadRequest(HttpServletRequest request)
            throws NullPointerException, NumberFormatException {
        username = request.getParameter("username");
        filename = request.getParameter("filename");
        if (username == null || filename == null) {
            throw new NullPointerException();
        }
    }

    public String getUsername() {
        return username;
    }

    public String getFilename() {
        return filename;
    }

    private String username;
    private String filename;
}
