package com.sjjapps.partygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
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
    private Size mSize;
    private FillViewport mBackgroundViewport;
    private Texture mTexture;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public Dialog(float widthScale) {
        super(new ScreenViewport(), Game.SPRITE_BATCH);
        mBackgroundViewport = new FillViewport(Game.WORLD_WIDTH, Game.WORLD_HEIGHT);
        mSize = Utils.scaleScreenSize(Gdx.graphics.getHeight(), Gdx.graphics.getWidth(),
                Gdx.graphics.getWidth(), widthScale);
        mTexture = Game.ASSETS.get(mAssets[0].file);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        getViewport().setScreenSize((int) mSize.width, (int) mSize.height);
        getViewport().setScreenPosition((int) (Gdx.graphics.getWidth() * 0.5f - getViewport().getScreenWidth() * 0.5f),
                (int) (Gdx.graphics.getHeight() * 0.5f - getViewport().getScreenHeight() * 0.5f));

        mBackgroundViewport.setScreenSize((int) mSize.width, (int) mSize.height);
        mBackgroundViewport.setScreenPosition((int) (Gdx.graphics.getWidth() * 0.5f - mBackgroundViewport.getScreenWidth() * 0.5f),
                (int) (Gdx.graphics.getHeight() * 0.5f - mBackgroundViewport.getScreenHeight() * 0.5f));
    }

    @Override
    public void draw() {
        super.draw();

        // Background
        mBackgroundViewport.apply(true);
        getBatch().setProjectionMatrix(mBackgroundViewport.getCamera().combined);
        getBatch().begin();
        getBatch().draw(mTexture,
                mBackgroundViewport.getWorldWidth() * 0.5f - mTexture.getWidth() * 0.5f,
                mBackgroundViewport.getWorldHeight() * 0.5f - mTexture.getHeight() * 0.5f);
        getBatch().end();
        // Test Circles
        Game.SHAPE_RENDERER.setProjectionMatrix(mBackgroundViewport.getCamera().combined);
        Game.SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
        Game.SHAPE_RENDERER.setColor(Color.YELLOW);
        Game.SHAPE_RENDERER.circle(mBackgroundViewport.getWorldWidth(), mBackgroundViewport.getWorldHeight(), 30);
        Game.SHAPE_RENDERER.circle(0, 0, 30);
        Game.SHAPE_RENDERER.end();

        // Foreground
        getViewport().apply(true);
        Game.SHAPE_RENDERER.setProjectionMatrix(getViewport().getCamera().combined);
        Game.SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
        Game.SHAPE_RENDERER.setColor(Color.RED);
        Game.SHAPE_RENDERER.circle(getViewport().getWorldWidth(), getViewport().getWorldHeight(), 20);
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
