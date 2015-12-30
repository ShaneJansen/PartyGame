package com.sjjapps.partygame.network;

import com.sjjapps.partygame.common.models.MiniGame;

import java.util.HashSet;

/**
 * Created by Shane Jansen on 12/20/15.
 */
public class GameState {
    private boolean mIsPaused;
    private boolean mIsStarted;
    private MiniGame[] mMiniGameTypes;
    private HashSet<MiniGame> mMiniGames;

    public GameState() {
        mIsPaused = false;
        mIsStarted = false;
        mMiniGameTypes = new MiniGame[]{
                new MiniGame("Run Away")
        };
        mMiniGames = new HashSet<MiniGame>();
    }

    public boolean isPaused() {
        return mIsPaused;
    }

    public boolean isStarted() {
        return mIsStarted;
    }

    public MiniGame[] getMiniGameTypes() {
        return mMiniGameTypes;
    }

    public HashSet<MiniGame> getMiniGames() {
        return mMiniGames;
    }

    public void setIsPaused(boolean isPaused) {
        mIsPaused = isPaused;
    }

    public void setIsStarted(boolean isStarted) {
        mIsStarted = isStarted;
    }
}
