package com.ja90n.files;

import java.util.HashMap;

public class Users {

    private HashMap<String, String> users; // School code + Auth code

    public void setDefault() {
        users = new HashMap<>();
        users.put("schoolcode1", "authcode1");
        users.put("schoolcode2", "authcode2");
    }

    public HashMap<String, String> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, String> users) {
        this.users = users;
    }
}
