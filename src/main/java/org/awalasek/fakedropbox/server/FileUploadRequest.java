package org.awalasek.fakedropbox.server;

import javax.servlet.http.HttpServletRequest;

import org.awalasek.FakeDropBox.common.ChangeType;

class FileUploadRequest {

    public FileUploadRequest(HttpServletRequest request)
            throws NullPointerException, NumberFormatException {
        username = request.getParameter("username");
        filename = request.getParameter("filename");
        changeType = ChangeType.valueOf(request.getParameter("changeType"));
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

    public ChangeType getOperationType() {
        return changeType;
    }

    private String username;
    private String filename;
    private ChangeType changeType;
}
