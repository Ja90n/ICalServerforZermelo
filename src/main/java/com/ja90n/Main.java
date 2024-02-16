package com.ja90n;

import nl.mrwouter.zermelo4j.ZermeloAPI;
import nl.mrwouter.zermelo4j.api.ZermeloApiException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new ICalServerForZermelo();
        //getAccessesCode();
    }

    public static void getAccessesCode() throws ZermeloApiException {
        System.out.println(ZermeloAPI.getAccessToken("",""));
    }
}