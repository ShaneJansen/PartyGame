package com.sjjapps.partygame.screens.games.runaway;

import com.esotericsoftware.minlog.Log;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.realms.DialogRealm;
import com.sjjapps.partygame.common.stages.Alert;
import com.sjjapps.partygame.common.stages.Dialog;
import com.sjjapps.partygame.network.NetworkHelper;
import com.sjjapps.partygame.screens.games.runaway.stages.GameStage;
import com.sjjapps.partygame.screens.games.runaway.stages.UiStage;
import com.sjjapps.partygame.screens.mainmenu.MainMenu;

/**
 * Created by Shane Jansen on 12/21/15.
 */
public class RunAway extends DialogRealm implements NetworkHelper.NetworkInterface {
    private UiStage mUiStage;
    private GameStage mGameStage;

    public RunAway() {
        super();
        // Loading assets
        UiStage.addAssets();
        GameStage.addAssets();

        Game.ASSETS.finishLoading();
        finishedLoading();
    }

    private void finishedLoading() {
        // Network setup
        Log.set(Log.LEVEL_DEBUG);
        Game.NETWORK_HELPER.setNetworkInterface(this);

        // UI stage
        mUiStage = new UiStage();
        addStage(mUiStage);

        // MiniGame stage
        mGameStage = new GameStage();
        addStage(mGameStage);

        // Finalize
        addInputListeners();
    }

    @Override
    public void addServerListeners() {

    }

    @Override
    public void addClientListeners() {

    }

    @Override
    public void clientDisconnected() {

    }

    @Override
    public void serverDisconnected() {
        addDialog(new Alert(new Dialog.DialogInterface() {
            @Override
            public void btnExitPressed() {
                Game.NETWORK_HELPER.getEndPoint().close();
                changeRealm(new MainMenu());
            }
        }, "You have been disconnected from the server."));
    }
}
