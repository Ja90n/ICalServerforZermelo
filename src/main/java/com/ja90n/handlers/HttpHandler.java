package com.ja90n.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;

public class HttpHandler implements com.sun.net.httpserver.HttpHandler {

    private final String authCode;
    private final ZermeloHandler zermeloHandler;

    public HttpHandler(String authCode, ZermeloHandler zermeloHandler) {
        this.authCode = authCode;
        this.zermeloHandler = zermeloHandler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Headers headers = exchange.getResponseHeaders();

        String line;
        StringBuilder resp = new StringBuilder();

        try {
            zermeloHandler.refreshCalender();

            File icsFile = new File("cal-" + authCode + ".ics");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(icsFile)));

            while ((line = bufferedReader.readLine()) != null) {
                resp.append(line);
                resp.append("\n");
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        headers.add("Content-Type", "text/calendar; name=zermelo.ics");
        headers.add("Content-Disposition", "attachment; filename=zermelo.ics");
        exchange.sendResponseHeaders(200, resp.length());
        OutputStream os = exchange.getResponseBody();
        os.write(resp.toString().getBytes());
        os.close();
    }

    private File createDirectory(String directoryPath) throws IOException {
        File dir = new File(directoryPath);
        if (dir.exists()) {
            return dir;
        }
        if (dir.mkdirs()) {
            return dir;
        }
        throw new IOException("Failed to create directory '" + dir.getAbsolutePath() + "' for an unknown reason.");
    }

}