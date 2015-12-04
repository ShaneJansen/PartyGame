package com.sjjapps.partygame.screens.mainmenu;

import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.DialogRealm;
import com.sjjapps.partygame.screens.mainmenu.controllers.BackgroundController;
import com.sjjapps.partygame.screens.mainmenu.controllers.BackgroundControllerInterface;
import com.sjjapps.partygame.screens.mainmenu.controllers.PencilController;
import com.sjjapps.partygame.screens.mainmenu.controllers.UiController;
import com.sjjapps.partygame.screens.mainmenu.dialogs.SettingsDialog;
import com.sjjapps.partygame.screens.mainmenu.models.Pencil;

/**
 * Created by Shane Jansen on 11/17/15.
 */
public class MainMenu extends DialogRealm implements BackgroundControllerInterface {
    private PencilController mPencilController;

    private SettingsDialog mSettingsDialog;

    public MainMenu() {
        super();
        // Loading Assets
        UiController.addAssets();
        BackgroundController.addAssets();
        Pencil.addAssets();
        Game.ASSETS.finishLoading(); // Blocks main thread. No loading screen.
        finishedLoading();
    }

    private void finishedLoading() {
        //Initialize
        addController(new UiController());
        addController(new BackgroundController(this));
        mPencilController = new PencilController();
        addController(mPencilController);
        addInputListeners();
        mSettingsDialog = new SettingsDialog();

        // Finalize
        mPencilController.addPencil(0, 0, true);
    }

    @Override
    public void backgroundClicked(int posX, int posY) {
        mPencilController.addPencil(posX, posY, false);

        /*if (temp) {
            removeDialog();
            temp = false;
        }
        else {
            addDialog(tempDialog);
            temp = true;
        }*/
    }

    @Override
    public void dispose() {

    }
}
