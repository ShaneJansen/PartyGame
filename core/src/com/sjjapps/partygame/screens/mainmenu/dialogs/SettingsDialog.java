package com.sjjapps.partygame.screens.mainmenu.dialogs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Dialog;
import com.sjjapps.partygame.managers.FilePathManager;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public class SettingsDialog extends Dialog {
    private Viewport mViewport;
    private Texture mBackground;

    public SettingsDialog() {
        super();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        mViewport.apply();

        Game.SPRITE_BATCH.setProjectionMatrix(mViewport.getCamera().combined);
        Game.SPRITE_BATCH.begin();
        Game.SPRITE_BATCH.draw(mBackground, -mBackground.getWidth() * 0.5f, -mBackground.getHeight() * 0.5f);
        Game.SPRITE_BATCH.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void didFinishLoading() {
        mBackground = Game.ASSETS.get(FilePathManager.MAIN_MENU, Texture.class);
    }

    @Override
    public void dispose() {

    }
}
