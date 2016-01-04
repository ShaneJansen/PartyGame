package com.sjjapps.partygame.screens.games.runaway.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.models.Asset;
import com.sjjapps.partygame.common.models.User;
import com.sjjapps.partygame.managers.FilePathManager;

/**
 * Created by Shane Jansen on 1/3/16.
 */
public class Player extends Actor {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.PENCIL, Texture.class)
    };
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private User mUser;
    private BitmapFont mBitmapFont;
    private Texture mTxtBackground;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public Player(User user, BitmapFont bitmapFont) {
        mTxtBackground = Game.ASSETS.get(mAssets[0].file);
        mBitmapFont = bitmapFont;
        mUser = user;

        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(new TextureRegion(mTxtBackground), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        // Draw the user's name
        mBitmapFont.draw(batch, mUser.getName(),
                getX(), getY() + getHeight() + 35);
    }

    public User getUser() {
        return mUser;
    }
}