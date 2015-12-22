package com.sjjapps.partygame.screens.games.bubby;

import com.esotericsoftware.minlog.Log;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Alert;
import com.sjjapps.partygame.common.Dialog;
import com.sjjapps.partygame.common.realms.DialogRealm;
import com.sjjapps.partygame.network.NetworkHelper;
import com.sjjapps.partygame.screens.games.bubby.stages.GameStage;
import com.sjjapps.partygame.screens.mainmenu.MainMenu;

/**
 * Created by Shane Jansen on 12/21/15.
 */
public class Bubby extends DialogRealm implements NetworkHelper.NetworkInterface {
    private GameStage mGameStage;

    public Bubby() {
        super();
        // Loading assets
        GameStage.addAssets();

        Game.ASSETS.finishLoading();
        finishedLoading();
    }

    private void finishedLoading() {
        // Network setup
        Log.set(Log.LEVEL_DEBUG);
        Game.NETWORK_HELPER.setNetworkInterface(this);

        // Game stage
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
