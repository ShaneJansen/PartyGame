package com.sjjapps.partygame.common;

import com.sjjapps.partygame.Game;

import java.util.Stack;

/**
 * Created by Shane Jansen on 12/3/15.
 */
public abstract class DialogRealm extends Realm implements Dialog.DialogInterface {
    private Stack<Dialog> mDialogs;

    public DialogRealm() {
        super();
        mDialogs = new Stack<Dialog>();
    }

    /**
     * Adds a dialog to the Realm.
     * @param dialog
     */
    public void addDialog(Dialog dialog, boolean shouldPause) {
        mDialogs.push(dialog);
        Game.MULTIPLEXER_MANAGER.clear();
        Game.MULTIPLEXER_MANAGER.addInput(dialog);
        Game.PAUSED = shouldPause;
    }

    /**
     * Removes the current dialog if there is one.
     */
    public void removeDialog() {
        if (!mDialogs.empty()) {
            Game.MULTIPLEXER_MANAGER.clear();
            mDialogs.pop();
            if (mDialogs.empty()) {
                addInputListeners();
                Game.PAUSED = false;
            }
            else {
                Game.MULTIPLEXER_MANAGER.addInput(mDialogs.peek());
            }
        }
    }

    /**
     * Clears all of the dialogs from the screen.
     */
    public void clearDialogs() {
        for (Dialog d: mDialogs) {
            Game.MULTIPLEXER_MANAGER.removeInput(mDialogs.pop());
        }
        addInputListeners();
        Game.PAUSED = false;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (!mDialogs.empty()) {
            mDialogs.peek().getViewport().apply(true);
            Game.SPRITE_BATCH.setProjectionMatrix(mDialogs.peek().getCamera().combined);
            mDialogs.peek().act(delta);
            mDialogs.peek().draw();
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

    @Override
    public void btnExitPressed() {
        removeDialog();
    }
}
