package com.sjjapps.partygame.network;

/**
 * Created by Shane Jansen on 1/4/16.
 */
public class MovablePlayer {
    private int mUserId;
    private float mPosX, mPosY;

    public MovablePlayer() {}

    public MovablePlayer(int userId) {
        this.mUserId = userId;
        mPosX = 0;
        mPosY = 0;
    }

    public int getUserId() {
        return mUserId;
    }

    public float getPosX() {
        return mPosX;
    }

    public float getPosY() {
        return mPosY;
    }

    public void setPosY(float posY) {
        mPosY = posY;
    }

    public void setPosX(float posX) {
        mPosX = posX;
    }
}
