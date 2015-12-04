package com.sjjapps.partygame.common;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.managers.FilePathManager;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public abstract class Dialog implements Screen, Loadable {
    protected Viewport mViewport;
    protected Texture mBackground;

    public Dialog() {
        mViewport = new FitViewport(Game.WORLD_WIDTH, Game.WORLD_HEIGHT);

        // Load Assets
        Game.ASSETS.load(FilePathManager.MAIN_MENU, Texture.class);
    }
}
