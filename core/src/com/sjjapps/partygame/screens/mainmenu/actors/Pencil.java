package com.sjjapps.partygame.screens.mainmenu.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Utils;
import com.sjjapps.partygame.managers.FilePathManager;
import com.sjjapps.partygame.models.Asset;
import com.sjjapps.partygame.models.Point;
import com.sjjapps.partygame.models.Size;

/**
 * Created by Shane Jansen on 11/23/15.
 */
public class Pencil extends Actor implements Disposable {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.PENCIL, Texture.class)
    };
    private Texture mTxtBackground;
    private int mVelocityX, mVelocityY;
    private Array<Point> mPoints;
    private int mRadius;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public Pencil(float viewportWidth, float scaleWidth, int velocity, int radius) {
        this.mVelocityX = velocity;
        this.mVelocityY = velocity;
        this.mRadius = radius;
        mPoints = new Array<Point>();
        mTxtBackground = Game.ASSETS.get(mAssets[0].file);

        Size size = Utils.scaleScreenSize(mTxtBackground.getHeight(), mTxtBackground.getWidth(),
                viewportWidth, scaleWidth);
        setWidth(size.width);
        setHeight(size.height);
    }

    public void bounce(boolean reverseX, boolean reverseY) {
        if (reverseX) mVelocityX *= -1;
        if (reverseY) mVelocityY *= -1;
        mPoints.add(new Point(getX(), getY()));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        float newX = getX() + (delta * mVelocityX);
        float newY = getY() + (delta * mVelocityY);
        setPosition(newX, newY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        // Draw pencil lines
        Game.SHAPE_RENDERER.setProjectionMatrix(batch.getProjectionMatrix());
        Game.SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
        Game.SHAPE_RENDERER.setColor(getColor());
        for (int i=0; i<mPoints.size; i++) {
            if (i == mPoints.size - 1) {
                Game.SHAPE_RENDERER.rectLine(mPoints.get(i).x, mPoints.get(i).y,
                        getX(), getY(), mRadius);
            }
            else {
                Game.SHAPE_RENDERER.rectLine(mPoints.get(i).x, mPoints.get(i).y,
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y, mRadius);
            }
        }
        Game.SHAPE_RENDERER.end();

        // Draw pencil
        batch.begin();
        batch.draw(new TextureRegion(mTxtBackground), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void dispose() {
        for (Asset a: mAssets) {
            Game.ASSETS.unload(a.file);
        }
        mTxtBackground.dispose();
    }
}
