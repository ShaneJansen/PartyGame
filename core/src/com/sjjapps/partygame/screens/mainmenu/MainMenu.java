package com.sjjapps.partygame.screens.mainmenu;

import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Dialog;
import com.sjjapps.partygame.common.DialogRealm;
import com.sjjapps.partygame.common.Realm;
import com.sjjapps.partygame.screens.mainmenu.controllers.BackgroundController;
import com.sjjapps.partygame.screens.mainmenu.controllers.PencilController;
import com.sjjapps.partygame.screens.mainmenu.controllers.UiController;
import com.sjjapps.partygame.screens.mainmenu.dialogs.SettingsDialog;
import com.sjjapps.partygame.screens.mainmenu.stages.BackgroundStage;

/**
 * Created by Shane Jansen on 11/17/15.
 */
public class MainMenu extends Realm implements BackgroundStage.BackgroundInterface {
    //private PencilController mPencilController;

    public MainMenu() {
        super();
        // Loading Assets
        BackgroundStage.addAssets();
        //UiController.addAssets();
        //PencilController.addAssets();
        //SettingsDialog.addAssets();
        Game.ASSETS.finishLoading(); // Blocks main thread. No loading screen.
        finishedLoading();
    }

    private void finishedLoading() {
        // Initialize
        //addStage(new UiController(this));
        addStage(new BackgroundStage(this));
        //mPencilController = new PencilController();
        //addStage(mPencilController);
        //addInputListeners();

        // Finalize
        //mPencilController.addPencil(0, 0, true);
    }

    @Override
    public void backgroundClicked(int posX, int posY) {
        //mPencilController.addPencil(posX, posY, false);
    }
}
