package com.sjjapps.partygame.common;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.managers.FilePathManager;
import com.sjjapps.partygame.models.Asset;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public abstract class Dialog extends InputAdapter implements Screen {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.DIALOG_BACKGROUND, Texture.class),
            new Asset(FilePathManager.BUTTON_X, Texture.class)
    };
    protected static final float ZOOM_AMOUNT = 0.3f;
    private DialogInterface mInterface;
    protected Viewport mViewport;
    protected Texture mBackground;
    private Sprite mButtonX;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public Dialog(DialogInterface dialogInterface) {
        this.mInterface = dialogInterface;

        mBackground = Game.ASSETS.get(mAssets[0].file);
        mButtonX = new Sprite((Texture) Game.ASSETS.get(mAssets[1].file));

        mViewport = new FitViewport(mBackground.getWidth(), mBackground.getHeight());
        ((OrthographicCamera) mViewport.getCamera()).zoom += ZOOM_AMOUNT; // Zoom the camera out to reveal the background
        mViewport.getCamera().update();
    }

    @Override
    public void render(float delta) {
        mViewport.apply();

        Game.SPRITE_BATCH.setProjectionMatrix(mViewport.getCamera().combined);
        Game.SPRITE_BATCH.begin();
        Game.SPRITE_BATCH.draw(mBackground, -mBackground.getWidth() * 0.5f, -mBackground.getHeight() * 0.5f);
        mButtonX.setPosition(-mBackground.getWidth() * 0.5f - mButtonX.getWidth() * 0.5f,
                mBackground.getHeight() * 0.5f - mButtonX.getHeight() * 0.5f);
        mButtonX.draw(Game.SPRITE_BATCH);
        Game.SPRITE_BATCH.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height);
    }

    @Override
    public void dispose() {
        for (Asset a: mAssets) {
            Game.ASSETS.unload(a.file);
        }
        mBackground.dispose();
        mButtonX.getTexture().dispose();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 vector = new Vector3(screenX, screenY, 0);
        mViewport.getCamera().unproject(vector, mViewport.getScreenX(), mViewport.getScreenY(),
                mViewport.getScreenWidth(), mViewport.getScreenHeight());
        Rectangle textureBounds = new Rectangle(mButtonX.getX(), mButtonX.getY(),
                mButtonX.getWidth(), mButtonX.getHeight());
        if (textureBounds.contains(vector.x, vector.y)) {
            mInterface.btnDialogExitClicked();
        }
        return true;
    }

    public interface DialogInterface {
        void btnDialogExitClicked();
    }
}
