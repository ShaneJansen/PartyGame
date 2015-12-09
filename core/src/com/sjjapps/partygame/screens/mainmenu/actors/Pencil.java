package com.sjjapps.partygame.screens.mainmenu.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.managers.FilePathManager;
import com.sjjapps.partygame.models.Asset;
import com.sjjapps.partygame.models.Point;

/**
 * Created by Shane Jansen on 11/23/15.
 */
public class Pencil implements Disposable {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.PENCIL, Texture.class),
    };
    private Sprite mSprite;
    private int mVelocityX, mVelocityY;
    private Array<Point> mPoints;
    private Color mColor;
    private int mRadius;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public Pencil() {
        this.mVelocityX = 50;
        this.mVelocityY = 50;
        mPoints = new Array<Point>();
        mColor = Color.BLACK;

        mSprite = new Sprite((Texture) Game.ASSETS.get(mAssets[0].file));
    }

    public Sprite getSprite() {
        return mSprite;
    }

    public int getVelocityX() {
        return mVelocityX;
    }

    public int getVelocityY() {
        return mVelocityY;
    }

    public Array<Point> getPoints() {
        return mPoints;
    }

    public Color getColor() {
        return mColor;
    }

    public int getRadius() {
        return mRadius;
    }

    public void setVelocityX(int velocityX) {
        this.mVelocityX = velocityX;
    }

    public void setVelocityY(int velocityY) {
        this.mVelocityY = velocityY;
    }

    public void setColor(Color mColor) {
        this.mColor = mColor;
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
    }

    @Override
    public void dispose() {
        for (Asset a: mAssets) {
            Game.ASSETS.unload(a.file);
        }
        mSprite.getTexture().dispose();
    }
}
