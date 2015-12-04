package com.sjjapps.partygame.screens.mainmenu.controllers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Loadable;
import com.sjjapps.partygame.common.SjjController;
import com.sjjapps.partygame.managers.FilePathManager;

/**
 * Created by Shane Jansen on 11/27/15.
 */
public class BackgroundController extends InputAdapter implements SjjController, Loadable {
    private BackgroundControllerInterface mControllerInterface;
    private Viewport mViewport;
    private Texture mBackground;

    public BackgroundController(BackgroundControllerInterface controllerInterface, OrthographicCamera camera) {
        this.mControllerInterface = controllerInterface;
        mViewport = new FillViewport(Game.WORLD_WIDTH, Game.WORLD_HEIGHT, camera);

        // Load Assets
        Game.ASSETS.load(FilePathManager.MAIN_MENU, Texture.class);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mControllerInterface.backgroundClicked(screenX, screenY);
        return true;
    }

    @Override
    public void update(float deltaTime) {
        mViewport.apply();

        Game.SPRITE_BATCH.setProjectionMatrix(mViewport.getCamera().combined);
        Game.SPRITE_BATCH.begin();
        //Game.SPRITE_BATCH.draw(mBackground, -mBackground.getWidth() * 0.5f, -mBackground.getHeight() * 0.5f);
        Game.SPRITE_BATCH.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height);
    }

    @Override
    public void didFinishLoading() {
        mBackground = Game.ASSETS.get(FilePathManager.MAIN_MENU, Texture.class);
    }

    @Override
    public void dispose() {
        Game.ASSETS.unload(FilePathManager.MAIN_MENU);
    }
}
