package com.sjjapps.partygame.screens.mainmenu;

import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.DialogRealm;
import com.sjjapps.partygame.common.Realm;
import com.sjjapps.partygame.common.Utils;
import com.sjjapps.partygame.screens.mainmenu.dialogs.SettingsDialog;
import com.sjjapps.partygame.screens.mainmenu.stages.BackgroundStage;
import com.sjjapps.partygame.screens.mainmenu.stages.PencilStage;
import com.sjjapps.partygame.screens.mainmenu.stages.UiStage;

/**
 * Created by Shane Jansen on 11/17/15.
 */
public class MainMenu extends DialogRealm {
    private PencilStage mPencilStage;

    public MainMenu() {
        super();
        // Loading Assets
        UiStage.addAssets();
        PencilStage.addAssets();
        BackgroundStage.addAssets();
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
        mPencilStage = new PencilStage();
        addStage(mPencilStage);
        addStage(new BackgroundStage(new BackgroundStage.BackgroundInterface() {
            @Override
            public void backgroundClicked(int posX, int posY) {
                mPencilStage.addPencil(posX, posY, false);
            }
        }));

        // Finalize
        addInputListeners();
        mPencilStage.addPencil(0, 0, true);
    }
}
