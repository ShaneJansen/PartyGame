package com.sjjapps.partygame.common.models;

/**
 * Created by Shane Jansen on 12/19/15.
 */
public class User {
    private String mName;
    private int mId;
    private int mScore;
    private boolean mIsReady;

    public User() {
        mName = "unknown";
        mId = -1;
        mScore = 0;
        mIsReady = false;
    }

    public User(String name) {
        this();
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

    public boolean isReady() {
        return mIsReady;
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

    public void setIsReady(boolean isReady) {
        mIsReady = isReady;
    }
}
