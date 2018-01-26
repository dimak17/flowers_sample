package com.impltech.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";
    public static final String DOC_UPLOAD_EXT_REGEX = ".*(.pdf|.txt|.doc|.docx|.xls|.xlsx|.rtf)$";
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";

    private Constants() {
    }
}
