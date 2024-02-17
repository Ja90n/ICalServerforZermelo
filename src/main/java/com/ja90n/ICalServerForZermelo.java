package com.ja90n;

import com.ja90n.files.User;
import com.ja90n.handlers.FileManager;
import com.ja90n.handlers.HttpHandler;
import com.ja90n.handlers.ZermeloHandler;

import com.sun.net.httpserver.HttpServer;
import nl.mrwouter.zermelo4j.ZermeloAPI;
import nl.mrwouter.zermelo4j.api.ZermeloApiException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ICalServerForZermelo {

    private final FileManager fileManager;
    private HttpServer server;

    public ICalServerForZermelo() throws IOException {

        fileManager = new FileManager();

        startServer(fileManager.getConfig().getPort());

        startUp();
    }

    public void startServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress("0", port), 0);
        server.start();
    }

    public void startUp() {
        ArrayList<User> users = fileManager.getUsers().getUsers();

        for (User user : users) {

            ZermeloAPI zermeloUser = getZermeloUser(user.getAuthCode(), user.getSchoolCode());
            if (zermeloUser == null) continue;

            ZermeloHandler zermeloHandler = new ZermeloHandler(zermeloUser);
            zermeloHandler.refreshCalender();
            server.createContext("/" + user.getLinkPath(), new HttpHandler(zermeloUser.getAccessToken(),zermeloHandler));
        }
    }


    public ZermeloAPI getZermeloUser(String authCode, String schoolCode) {
        ZermeloAPI api = ZermeloAPI.getAPI(schoolCode,authCode);

        try {
            api.getUser();
            return api;
        } catch (ZermeloApiException e) {
            System.out.println("Could not find user '" + authCode + "' from " + schoolCode);
            return null;
        }
    }
}
