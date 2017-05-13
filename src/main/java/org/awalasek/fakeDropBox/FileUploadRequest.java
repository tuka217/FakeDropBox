package org.awalasek.fakeDropBox;

import javax.servlet.http.HttpServletRequest;

class FileUploadRequest {

    public FileUploadRequest(HttpServletRequest request) {
        username = request.getParameter("username");
        fileAmount = Integer.parseInt(request.getParameter("fileAmount"));
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
