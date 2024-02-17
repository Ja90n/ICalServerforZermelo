package com.ja90n.files;

import java.util.HashMap;

public class User {

    private String authCode;
    private String schoolCode;
    private String linkPath;

    public void setDefault() {
        authCode = "authCode";
        schoolCode = "schoolCode";
        linkPath = "linkPath";
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public String getLinkPath() {
        return linkPath;
    }
}
