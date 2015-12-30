package com.sjjapps.partygame.common.models;

/**
 * Created by Shane Jansen on 12/29/15.
 */
public class MiniGame {
    private String mName;
    private int mNumRounds, mCurrentRound;

    public MiniGame() {
        mName = "";
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

    public int getNumRounds() {
        return mNumRounds;
    }

    public int getCurrentRound() {
        return mCurrentRound;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setNumRounds(int numRounds) {
        mNumRounds = numRounds;
    }

    public void setCurrentRound(int currentRound) {
        mCurrentRound = currentRound;
    }
}
