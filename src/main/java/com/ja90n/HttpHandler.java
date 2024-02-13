package com.ja90n;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.Scanner;

public class HttpHandler implements com.sun.net.httpserver.HttpHandler {


    @Override
    public void handle(HttpExchange t) throws IOException {
        Headers h = t.getResponseHeaders();

        String line;
        String resp = "";

        try {
            File newFile = new File("hoihoi.ics");
            System.out.println(newFile.getName());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));

            while ((line = bufferedReader.readLine()) != null) {
                resp += line;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        h.add("Content-Type", "text/html");
        t.sendResponseHeaders(200, resp.length());
        OutputStream os = t.getResponseBody();
        os.write(resp.getBytes());
        os.close();
    }

}