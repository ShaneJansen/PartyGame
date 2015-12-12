package com.sjjapps.partygame.screens.mainmenu;

import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.DialogRealm;
import com.sjjapps.partygame.screens.mainmenu.dialogs.SettingsDialog;
import com.sjjapps.partygame.screens.mainmenu.stages.BackgroundStage;
import com.sjjapps.partygame.screens.mainmenu.stages.PencilStage;
import com.sjjapps.partygame.screens.mainmenu.stages.UiStage;

/**
 * Created by Shane Jansen on 11/17/15.
 */
public class MainMenu extends DialogRealm {
    private PencilStage mStgPencil;

    public MainMenu() {
        super();
        // Loading Assets
        UiStage.addAssets();
        PencilStage.addAssets();
        BackgroundStage.addAssets();
        SettingsDialog.addAssets();
        Game.ASSETS.finishLoading(); // Blocks main thread. No loading screen.
        finishedLoading();
    }

    private void finishedLoading() {
        // Initialize
        addStage(new UiStage(new UiStage.UiInterface() {
            @Override
            public void btnPlayClicked() {
                Game.log("Play clicked");
            }

            @Override
            public void btnCreditsClicked() {
                Game.log("Credits clicked");
            }

            @Override
            public void btnSettingsClicked() {
                Game.log("Settings clicked");
                addDialog(new SettingsDialog());
            }
        }));
        mStgPencil = new PencilStage();
        addStage(mStgPencil);
        addStage(new BackgroundStage(new BackgroundStage.BackgroundInterface() {
            @Override
            public void backgroundClicked(int posX, int posY) {
                mStgPencil.addPencil(posX, posY, false);
            }
        }));

        // Finalize
        addInputListeners();
        mStgPencil.addPencil(0, 0, true);
    }
}
