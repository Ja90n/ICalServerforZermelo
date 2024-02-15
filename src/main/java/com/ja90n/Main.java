package com.ja90n;

import com.sun.net.httpserver.HttpServer;
import java.io.FileWriter;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import nl.mrwouter.zermelo4j.ZermeloAPI;
import nl.mrwouter.zermelo4j.api.ZermeloApiException;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Main {
    public static void main(String[] args) throws IOException, ZermeloApiException {

        zermelo();

    }

    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("0", 8001), 0);

        server.createContext("/test", new HttpHandler());

        server.start();
    }

    public static void zermelo() throws ZermeloApiException {
        // Access token can be created by using ZermeloAPI#getAccessToken("[school]", "[koppel app code]");
        ZermeloAPI api = ZermeloAPI.getAPI("x","x");
        Date endDate = new Date();
        endDate.setTime(endDate.getTime() - 43200000000L);

        System.out.println(api.getUser().getUser());

        Date startDate = new Date();
        startDate.setTime(startDate.getTime() + 43200000000L);

        net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();

        try {
            api.getAppointments(endDate, startDate)
                    .forEach(appointment -> {

                        DateTime start = new DateTime(appointment.getStart() * 1000);
                        DateTime end = new DateTime(appointment.getEnd() * 1000);

                        VEvent event = new VEvent(start, end, appointment.getSubjects().get(0));

                        calendar.withComponent(event);

                    });

        } catch (ZermeloApiException exception) {
            exception.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("higuys.ics");
            myWriter.write(calendar.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}