package com.ja90n.handlers;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ja90n.files.Config;
import com.ja90n.files.Users;

import java.io.*;

public class FileManager {

    private Config config;
    private Users users;

    public FileManager() throws IOException {
        reload();
    }

    public void reload() throws IOException {

        Gson gson = new Gson();
        File configFile = new File("config.json");
        if (!configFile.exists()) {
            Config tempConfig = new Config();
            tempConfig.setDefault();

            Writer writer = new FileWriter("config.json");
            gson.toJson(tempConfig, writer);
            writer.flush();
            writer.close();

            config = tempConfig;
        } else {
            config = gson.fromJson(new JsonReader(new FileReader("config.json")), Config.class);
        }

        File usersFile = new File("users.json");
        if (!usersFile.exists()) {
            Users tempUsers = new Users();
            tempUsers.setDefault();

            Writer writer = new FileWriter("users.json");
            gson.toJson(tempUsers, writer);
            writer.flush();
            writer.close();

            users = tempUsers;
        } else {
            users = gson.fromJson(new JsonReader(new FileReader("users.json")), Users.class);
        }
    }

    public Config getConfig() {
        return config;
    }

    public Users getUsers() {
        return users;
    }
}
