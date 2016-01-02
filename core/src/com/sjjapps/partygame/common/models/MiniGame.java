package com.sjjapps.partygame.common.models;

/**
 * Created by Shane Jansen on 12/29/15.
 */
public class MiniGame {
    private String mName;
    private boolean mIsStarted;
    private int mNumRounds, mCurrentRound;

    public MiniGame() {
        mName = "";
        mIsStarted = false;
        mNumRounds = 0;
        mCurrentRound = 1;
    }

    public MiniGame(String name) {
        this();
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public boolean isStarted() {
        return mIsStarted;
    }

    public int getNumRounds() {
        return mNumRounds;
    }

    public int getCurrentRound() {
        return mCurrentRound;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setIsStarted(boolean isStarted) {
        mIsStarted = isStarted;
    }

    public void setNumRounds(int numRounds) {
        mNumRounds = numRounds;
    }

    public void setCurrentRound(int currentRound) {
        mCurrentRound = currentRound;
    }
}
