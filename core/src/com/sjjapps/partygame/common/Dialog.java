package com.sjjapps.partygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.managers.FilePathManager;
import com.sjjapps.partygame.models.Asset;
import com.sjjapps.partygame.models.Size;

/**
 * Created by Shane Jansen on 12/11/15.
 */
public abstract class Dialog extends Stage {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.DIALOG_BACKGROUND, Texture.class)
    };
    private Texture mTexture;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public Dialog(float widthScale) {
        super(new ScreenViewport(), Game.SPRITE_BATCH);
        Size size = Utils.scaleScreenSize(Gdx.graphics.getHeight(), Gdx.graphics.getWidth(),
                Gdx.graphics.getWidth(), widthScale);
        //getViewport().setScreenSize((int) size.width, (int) size.height);
        //getViewport().setScreenSize(100, 100);
        //getViewport().setWorldSize(100, 100);
        getViewport().update(100, 100);
        mTexture = Game.ASSETS.get(mAssets[0].file);
    }

    @Override
    public void draw() {
        super.draw();
        Game.SPRITE_BATCH.setProjectionMatrix(getCamera().combined);
        Game.SPRITE_BATCH.begin();
        Game.SPRITE_BATCH.draw(mTexture, 0, 0);
        /*mButtonX.setPosition(-mTexture.getWidth() * 0.5f - mButtonX.getWidth() * 0.5f,
                mTexture.getHeight() * 0.5f - mButtonX.getHeight() * 0.5f);
        mButtonX.draw(Game.SPRITE_BATCH);*/
        //Game.SPRITE_BATCH.draw(new TextureRegion(mTexture), getX(), getY(), getOriginX(), getOriginY(),
        //        getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        Game.SPRITE_BATCH.end();

        Game.SHAPE_RENDERER.setProjectionMatrix(getBatch().getProjectionMatrix());
        Game.SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
        Game.SHAPE_RENDERER.setColor(Color.YELLOW);
        //Game.SHAPE_RENDERER.circle(getCamera().viewportWidth * 0.5f,
        //        getCamera().viewportHeight * 0.5f, 20);
        Game.SHAPE_RENDERER.circle(0, 0, 20);
        Game.SHAPE_RENDERER.end();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        /*Vector3 vector = new Vector3(screenX, screenY, 0);
        mViewport.getCamera().unproject(vector, mViewport.getScreenX(), mViewport.getScreenY(),
                mViewport.getScreenWidth(), mViewport.getScreenHeight());
        Rectangle textureBounds = new Rectangle(mButtonX.getX(), mButtonX.getY(),
                mButtonX.getWidth(), mButtonX.getHeight());
        if (textureBounds.contains(vector.x, vector.y)) {
            mInterface.btnDialogExitClicked();
        }*/
        return true;
    }
}
