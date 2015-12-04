package com.sjjapps.partygame.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.sjjapps.partygame.common.DialogScreen;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.screens.mainmenu.controllers.BackgroundController;
import com.sjjapps.partygame.screens.mainmenu.controllers.BackgroundControllerInterface;
import com.sjjapps.partygame.screens.mainmenu.controllers.PencilController;
import com.sjjapps.partygame.screens.mainmenu.controllers.UiController;
import com.sjjapps.partygame.screens.mainmenu.dialogs.SettingsDialog;

/**
 * Created by Shane Jansen on 11/17/15.
 */
public class MainMenu extends DialogScreen implements BackgroundControllerInterface {
    private BackgroundController mBackgroundController;
    private UiController mUiController;
    private PencilController mPencilController;

    private boolean temp = false;
    private SettingsDialog tempDialog;

    public MainMenu() {
        super();

        tempDialog = new SettingsDialog(new OrthographicCamera());

        // Initialize
        mBackgroundController = new BackgroundController(this, new OrthographicCamera());
        mPencilController = new PencilController(new OrthographicCamera());
        mUiController = new UiController(new OrthographicCamera());

        addInputListeners();

        // Loading Assets
        Game.ASSETS.finishLoading(); // Blocks main thread. No loading screen.
        mUiController.didFinishLoading();
        mPencilController.didFinishLoading();
        mBackgroundController.didFinishLoading();

        // Finalize
        mPencilController.addPencil(0, 0, true);
    }

    @Override
    public void addInputListeners() {
        Game.MULTIPLEXER_MANAGER.addInput(mUiController);
        Game.MULTIPLEXER_MANAGER.addInput(mBackgroundController);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background
        mBackgroundController.update(delta);

        // Draw the pencil
        mPencilController.update(delta);

        // Draw the UI
        mUiController.update(delta);

        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        mBackgroundController.resize(width, height);
        mPencilController.resize(width, height);
        mUiController.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();

        mBackgroundController.dispose();
        mUiController.dispose();
        mPencilController.dispose();
    }

    @Override
    public void backgroundClicked(int posX, int posY) {
        tempDialog.didFinishLoading();
        mPencilController.addPencil(posX, posY, false);
        if (temp) {
            removeDialog();
            temp = false;
        }
        else {
            addDialog(tempDialog);
            temp = true;
        }
    }
}
