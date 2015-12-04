package com.sjjapps.partygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.sjjapps.partygame.Game;

import java.util.Stack;

/**
 * Created by Shane Jansen on 12/3/15.
 */
public abstract class DialogScreen implements Screen {
    private Stack<Screen> mDialogs;

    public DialogScreen() {
        mDialogs = new Stack<Screen>();
    }

    /**
     * Add listeners to the Multiplexer.
     * Order is important!
     */
    public abstract void addInputListeners();

    public void addDialog(Screen dialog) {
        mDialogs.push(dialog);
        Game.MULTIPLEXER_MANAGER.clear();
        Game.MULTIPLEXER_MANAGER.addInput(dialog);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Removes the current dialog if there is one.
     */
    public void removeDialog() {
        if (!mDialogs.empty()) {
            mDialogs.pop();
        }
        addInputListeners();
    }

    /**
     * Clears all of the dialogs from the screen.
     */
    public void clearDialogs() {
        mDialogs.clear();
        addInputListeners();
    }

    @Override
    public void show() {
        if (!mDialogs.empty()) {
            for (Screen d: mDialogs) {
                d.show();
            }
        }
    }

    @Override
    public void render(float delta) {
        if (!mDialogs.empty()) {
            mDialogs.peek().render(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        if (!mDialogs.empty()) {
            for (Screen d: mDialogs) {
                d.resize(width, height);
            }
        }
    }

    @Override
    public void pause() {
        if (!mDialogs.empty()) {
            for (Screen d: mDialogs) {
                d.pause();
            }
        }
    }

    @Override
    public void resume() {
        if (!mDialogs.empty()) {
            for (Screen d: mDialogs) {
                d.resume();
            }
        }
    }

    @Override
    public void hide() {
        if (!mDialogs.empty()) {
            for (Screen d: mDialogs) {
                d.hide();
            }
        }
    }

    @Override
    public void dispose() {
        if (!mDialogs.empty()) {
            for (Screen d: mDialogs) {
                d.dispose();
            }
        }
    }
}
