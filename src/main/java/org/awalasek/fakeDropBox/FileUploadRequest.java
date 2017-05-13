package org.awalasek.fakeDropBox;

import javax.servlet.http.HttpServletRequest;

class FileUploadRequest {

    public FileUploadRequest(HttpServletRequest request)
            throws NullPointerException, NumberFormatException {
        username = request.getParameter("username");
        fileAmount = Integer.parseInt(request.getParameter("fileAmount"));
        if (username == null || fileAmount == null) {
            throw new NullPointerException();
        }
    }

    public String getUsername() {
        return username;
    }

    public Integer getFileAmount() {
        return fileAmount;
    }

    public Boolean noFilesLeft() {
        return fileAmount <= 0;
    }

    private String username;
    private Integer fileAmount;
}
