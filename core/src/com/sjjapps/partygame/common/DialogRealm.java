package com.sjjapps.partygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.sjjapps.partygame.Game;

import java.util.Stack;

/**
 * Created by Shane Jansen on 12/3/15.
 */
public abstract class DialogRealm extends Realm implements Screen {
    private Stack<Dialog> mDialogs;

    public DialogRealm() {
        mDialogs = new Stack<Dialog>();
    }

    /**
     * Adds a dialog to the Realm.
     * @param dialog
     */
    public void addDialog(Dialog dialog) {
        mDialogs.push(dialog);
        Game.MULTIPLEXER_MANAGER.clear();
        Game.MULTIPLEXER_MANAGER.addInput(dialog);
        Game.PAUSED = true;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Removes the current dialog if there is one.
     */
    public void removeDialog() {
        if (!mDialogs.empty()) {
            mDialogs.pop();
        }
        Game.PAUSED = false;
        addInputListeners();
    }

    /**
     * Clears all of the dialogs from the screen.
     */
    public void clearDialogs() {
        mDialogs.clear();
        addInputListeners();
    }

    /**
     * Called when all assets are finished loading.
     */
    @Override
    public void didFinishLoading() {
        super.didFinishLoading();
        for (Dialog d: mDialogs) {
            d.didFinishLoading();
        }
    }

    @Override
    public void show() {
        if (!mDialogs.empty()) {
            for (Dialog d: mDialogs) {
                d.show();
            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (!mDialogs.empty()) {
            mDialogs.peek().render(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        if (!mDialogs.empty()) {
            for (Dialog d: mDialogs) {
                d.resize(width, height);
            }
        }
    }

    @Override
    public void pause() {
        if (!mDialogs.empty()) {
            for (Dialog d: mDialogs) {
                d.pause();
            }
        }
    }

    @Override
    public void resume() {
        if (!mDialogs.empty()) {
            for (Dialog d: mDialogs) {
                d.resume();
            }
        }
    }

    @Override
    public void hide() {
        if (!mDialogs.empty()) {
            for (Dialog d: mDialogs) {
                d.hide();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        if (!mDialogs.empty()) {
            for (Dialog d: mDialogs) {
                d.dispose();
            }
        }
    }
}
