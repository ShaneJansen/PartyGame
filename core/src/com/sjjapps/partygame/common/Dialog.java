package com.sjjapps.partygame.common;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.managers.FilePathManager;
import com.sjjapps.partygame.models.Asset;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public abstract class Dialog implements Screen {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.MAIN_MENU, Texture.class)
    };
    protected Viewport mViewport;
    protected Texture mBackground;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public Dialog() {
        mViewport = new FitViewport(Game.WORLD_WIDTH, Game.WORLD_HEIGHT);
        mBackground = Game.ASSETS.get(mAssets[0].file);
    }

    @Override
    public void dispose() {
        for (Asset a: mAssets) {
            Game.ASSETS.unload(a.file);
        }
        mBackground.dispose();
    }
}
