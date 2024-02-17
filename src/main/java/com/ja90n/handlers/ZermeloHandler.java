package com.ja90n.handlers;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import nl.mrwouter.zermelo4j.ZermeloAPI;
import nl.mrwouter.zermelo4j.api.ZermeloApiException;
import nl.mrwouter.zermelo4j.appointments.Appointment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ZermeloHandler {

    private final ZermeloAPI zermeloUser;

    public ZermeloHandler(ZermeloAPI zermeloAPI) {
        zermeloUser = zermeloAPI;
        refreshCalender();
    }

    public void refreshCalender() {

        List<Appointment> appointments = getAppointments(zermeloUser);
        Calendar calendar = getCalender(appointments);
        writeToFile(zermeloUser, calendar);

    }

    private void writeToFile(ZermeloAPI zermeloUser, Calendar calendar) {
        try {
            FileWriter myWriter = new FileWriter("cal-" + zermeloUser.getAccessToken() + ".ics");
            myWriter.write(calendar.toString());
            myWriter.close();
            System.out.println("Successfully calendar to the file");
        } catch (IOException e) {
            System.out.println("Could not write to file!");
            e.printStackTrace();
        }
    }

    private Calendar getCalender(List<Appointment> appointments) {

        Calendar calendar = new Calendar();

        for (Appointment appointment : appointments) {
            if (appointment.isCancelled()) continue;
            VEvent event = getEvent(appointment);
            calendar.withComponent(event);
        }

        return calendar;
    }

    private VEvent getEvent(Appointment appointment) {
        CalendarBuilder builder = new CalendarBuilder();
        TimeZoneRegistry registry = builder.getRegistry();
        TimeZone timeZone = registry.getTimeZone("Europe/Amsterdam");

        DateTime start = new DateTime(appointment.getStart() * 1000);
        start.setTimeZone(timeZone);
        DateTime end = new DateTime(appointment.getEnd() * 1000);
        end.setTimeZone(timeZone);

        StringBuilder eventName = new StringBuilder();

        if (!appointment.getSubjects().isEmpty()) eventName.append(appointment.getSubjects().get(0)).append(", ");

        if (!appointment.getLocations().isEmpty()) eventName.append(appointment.getLocations().get(0)).append(", ");

        if (!appointment.getTeachers().isEmpty()) eventName.append(appointment.getTeachers().get(0));

        return new VEvent(start,end,eventName.toString());
    }

    private List<Appointment> getAppointments(ZermeloAPI zermeloUser) {

        Date endDate = new Date();
        endDate.setTime(endDate.getTime() - 4320000000L);

        Date startDate = new Date();
        startDate.setTime(startDate.getTime() + 4320000000L);

        try {
            return zermeloUser.getAppointments(endDate, startDate);
        } catch (ZermeloApiException e) {
            System.out.println("Could not get appointments form " + zermeloUser.getAccessToken());
            return null;
        }
    }

}
