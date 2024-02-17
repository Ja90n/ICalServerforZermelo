package com.ja90n.files;

import java.util.ArrayList;

public class Users {

    private ArrayList<User> users;

    public Users() {
        users = new ArrayList<>();
    }

    public void setDefault() {
        User user = new User();
        user.setDefault();

        users.add(user);
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
