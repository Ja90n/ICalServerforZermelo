package com.ja90n;

import com.sun.net.httpserver.HttpServer;
import nl.mrwouter.zermelo4j.ZermeloAPI;
import nl.mrwouter.zermelo4j.api.ZermeloApiException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress("0", 8001), 0);

        server.createContext("/test", new HttpHandler());

        server.start();

    }

    public void zermelo() {
        // Access token can be created by using ZermeloAPI#getAccessToken("[school]", "[koppel app code]");
        ZermeloAPI api = ZermeloAPI.getAPI("x", "x");
        System.out.println(api.getAccessToken());
        Date endDate = new Date();
        endDate.setTime(endDate.getTime() - 432000000L);

        try {
            api.getAppointments(endDate, new Date())
                    .forEach(appointment -> System.out.println(appointment.toString()));

            api.getAnnouncements()
                    .forEach(announcement -> System.out.println(announcement.toString()));
        } catch (ZermeloApiException exception) {
            exception.printStackTrace();
        }
    }
}