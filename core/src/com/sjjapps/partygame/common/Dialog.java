package com.sjjapps.partygame.common;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.managers.FilePathManager;
import com.sjjapps.partygame.models.Asset;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public abstract class Dialog implements Screen {
    protected Array<Asset> mAssets;
    protected Viewport mViewport;
    protected Texture mBackground;

    public Dialog() {
        // Default assets
        mAssets = new Array<Asset>();
        mAssets.add(new Asset(FilePathManager.MAIN_MENU, Texture.class));

        mViewport = new FitViewport(Game.WORLD_WIDTH, Game.WORLD_HEIGHT);
    }


}
