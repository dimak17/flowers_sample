package com.impltech.web.rest.request;

/**
 * Created by platon
 */
public class PasswordChangeRequest {
    private String newPassword;
    private String oldPassword;
    private String login;

    public PasswordChangeRequest() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
