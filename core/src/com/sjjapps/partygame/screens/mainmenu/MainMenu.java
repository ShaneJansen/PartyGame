package com.sjjapps.partygame.screens.mainmenu;

import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Dialog;
import com.sjjapps.partygame.common.DialogRealm;
import com.sjjapps.partygame.screens.mainmenu.controllers.BackgroundController;
import com.sjjapps.partygame.screens.mainmenu.controllers.PencilController;
import com.sjjapps.partygame.screens.mainmenu.controllers.UiController;
import com.sjjapps.partygame.screens.mainmenu.dialogs.SettingsDialog;

/**
 * Created by Shane Jansen on 11/17/15.
 */
public class MainMenu extends DialogRealm implements BackgroundController.BackgroundInterface,
        UiController.UiInterface, Dialog.DialogInterface {
    private PencilController mPencilController;

    public MainMenu() {
        super();
        // Loading Assets
        UiController.addAssets();
        BackgroundController.addAssets();
        PencilController.addAssets();
        SettingsDialog.addAssets();
        Game.ASSETS.finishLoading(); // Blocks main thread. No loading screen.
        finishedLoading();
    }

    private void finishedLoading() {
        // Initialize
        addController(new UiController(this));
        addController(new BackgroundController(this));
        mPencilController = new PencilController();
        addController(mPencilController);
        addInputListeners();

        // Finalize
        mPencilController.addPencil(0, 0, true);
    }

    @Override
    public void backgroundClicked(int posX, int posY) {
        mPencilController.addPencil(posX, posY, false);
    }

    @Override
    public void btnPlayClicked() {

    }

    @Override
    public void btnCustomWordsClicked() {

    }

    @Override
    public void btnSettingsClicked() {
        addDialog(new SettingsDialog(this));
        addDialog(new SettingsDialog(this));
    }

    @Override
    public void btnDialogExitClicked() {
        removeDialog();
    }
}
