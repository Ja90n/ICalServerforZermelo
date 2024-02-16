package com.ja90n.files;

import java.util.HashMap;

public class Users {

    private HashMap<String, String> users; // Auth code + School code

    public void setDefault() {
        users = new HashMap<>();
        users.put("authcode1", "schoolcode1");
        users.put("authcode2", "schoolcode2");
    }

    public HashMap<String, String> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, String> users) {
        this.users = users;
    }
}
