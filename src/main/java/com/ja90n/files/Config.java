package com.ja90n.files;

import java.util.HashMap;

public class Config {

    private boolean hoi;

    public void setDefault() {
        hoi = true;
    }

    public void setHoi(boolean hoi) {
        this.hoi = hoi;
    }

    public boolean isHoi() {
        return hoi;
    }
}
