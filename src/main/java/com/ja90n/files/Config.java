package com.ja90n.files;

public class Config {

    private int port;

    public void setDefault() {
        port = 8001;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
