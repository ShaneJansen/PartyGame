package com.sjjapps.partygame.screens.lobby;

import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.DialogRealm;
import com.sjjapps.partygame.screens.mainmenu.MainMenu;

/**
 * Created by Shane Jansen on 12/17/15.
 */
public class Lobby extends DialogRealm {
    private String mIpAddress;

    public Lobby() {
        super();
        // Loading assets

        // Check if server or client
        mIpAddress = "";
        if (Game.SERVER != null) {
            mIpAddress = ServerHandler.startServer();
            if (mIpAddress == null) {
                // Server could not be started; Return to menu
                Game.GAME.setScreen(new MainMenu());
                dispose();
            }
        }

        Game.ASSETS.finishLoading();
        finishedLoading();
    }

    private void finishedLoading() {

    }
}
