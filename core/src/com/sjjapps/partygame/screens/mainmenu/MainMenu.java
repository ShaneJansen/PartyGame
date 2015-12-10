package com.sjjapps.partygame.screens.mainmenu;

import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Realm;
import com.sjjapps.partygame.screens.mainmenu.stages.BackgroundStage;
import com.sjjapps.partygame.screens.mainmenu.stages.PencilStage;

/**
 * Created by Shane Jansen on 11/17/15.
 */
public class MainMenu extends Realm implements BackgroundStage.BackgroundInterface {
    private PencilStage mPencilStage;

    public MainMenu() {
        super();
        // Loading Assets
        BackgroundStage.addAssets();
        //UiController.addAssets();
        PencilStage.addAssets();
        //SettingsDialog.addAssets();
        Game.ASSETS.finishLoading(); // Blocks main thread. No loading screen.
        finishedLoading();
    }

    private void finishedLoading() {
        // Initialize
        //addStage(new UiController(this));
        //addStage(new BackgroundStage(this));
        mPencilStage = new PencilStage();
        addStage(mPencilStage);
        addInputListeners();

        // Finalize
        mPencilStage.addPencil(0, 0, true);
    }

    @Override
    public void backgroundClicked(int posX, int posY) {
        mPencilStage.addPencil(posX, posY, false);
    }
}
