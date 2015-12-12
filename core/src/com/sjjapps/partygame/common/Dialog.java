package com.sjjapps.partygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
            new Asset(FilePathManager.DIALOG_BACKGROUND, Texture.class),
            new Asset(FilePathManager.BUTTON_X, Texture.class)
    };
    private Size mSize;
    private FillViewport mVpBackground;
    private Texture mTxtBackground, mTxtButtonX;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public Dialog(float widthScale) {
        super(new ScreenViewport(), Game.SPRITE_BATCH);
        mVpBackground = new FillViewport(Game.WORLD_WIDTH, Game.WORLD_HEIGHT);
        mSize = Utils.scaleScreenSize(Gdx.graphics.getHeight(), Gdx.graphics.getWidth(),
                Gdx.graphics.getWidth(), widthScale);
        mTxtBackground = Game.ASSETS.get(mAssets[0].file);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        getViewport().setScreenSize((int) mSize.width, (int) mSize.height);
        getViewport().setScreenPosition((int) (Gdx.graphics.getWidth() * 0.5f - getViewport().getScreenWidth() * 0.5f),
                (int) (Gdx.graphics.getHeight() * 0.5f - getViewport().getScreenHeight() * 0.5f));

        mVpBackground.setScreenSize((int) mSize.width, (int) mSize.height);
        mVpBackground.setScreenPosition((int) (Gdx.graphics.getWidth() * 0.5f - mVpBackground.getScreenWidth() * 0.5f),
                (int) (Gdx.graphics.getHeight() * 0.5f - mVpBackground.getScreenHeight() * 0.5f));
    }

    @Override
    public void draw() {
        super.draw();

        // Background
        mVpBackground.apply(true);
        getBatch().setProjectionMatrix(mVpBackground.getCamera().combined);
        getBatch().begin();
        getBatch().draw(mTxtBackground,
                mVpBackground.getWorldWidth() * 0.5f - mTxtBackground.getWidth() * 0.5f,
                mVpBackground.getWorldHeight() * 0.5f - mTxtBackground.getHeight() * 0.5f);
        getBatch().end();
        // Test circles
        Game.SHAPE_RENDERER.setProjectionMatrix(mVpBackground.getCamera().combined);
        Game.SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
        Game.SHAPE_RENDERER.setColor(Color.YELLOW);
        Game.SHAPE_RENDERER.circle(mVpBackground.getWorldWidth(), mVpBackground.getWorldHeight(), 30);
        Game.SHAPE_RENDERER.circle(0, 0, 30);
        Game.SHAPE_RENDERER.end();

        // Foreground
        getViewport().apply(true);
        // Test circles
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
