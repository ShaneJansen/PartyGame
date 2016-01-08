package com.sjjapps.partygame.screens.games.runaway.network;

/**
 * Created by Shane Jansen on 1/7/16.
 */
public class NetEnemy {
    private float mPosX, mPosY;

    public NetEnemy() {
        mPosX = 0;
        mPosY = 0;
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
