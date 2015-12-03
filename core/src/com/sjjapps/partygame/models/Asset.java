package com.sjjapps.partygame.models;

/**
 * Created by Shane Jansen on 11/29/15.
 */
public class Asset {
    public String file;
    public Class type;

    public Asset(String file, Class type) {
        this.file = file;
        this.type = type;
    }
}
