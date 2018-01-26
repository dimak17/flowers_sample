package com.impltech.web.rest.request;

/**
 * Created by dima
 */
public class FileUploadRequest {

    private String base64file;
    private String name;

    public FileUploadRequest() {
    }

    public String getBase64file() {
        return base64file;
    }

    public void setBase64file(String base64file) {
        this.base64file = base64file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
