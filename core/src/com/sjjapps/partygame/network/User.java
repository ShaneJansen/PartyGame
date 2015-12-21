package com.sjjapps.partygame.network;

import java.util.ArrayList;

/**
 * Created by Shane Jansen on 12/19/15.
 */
public class User {
    private String mName;
    private int mId;
    private int mScore;

    public User() {}

    public User(String name) {
        this.mName = name;
    }

    public String getName() {
        return mName;
    }

    public int getId() {
        return mId;
    }

    public int getScore() {
        return mScore;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public static class NetworkUsers {
        public ArrayList<User> users = new ArrayList<User>();

        public NetworkUsers() {}
    }
}
