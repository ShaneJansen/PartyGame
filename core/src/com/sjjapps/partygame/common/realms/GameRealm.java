package com.sjjapps.partygame.common.realms;

import com.badlogic.gdx.Gdx;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.stages.Alert;
import com.sjjapps.partygame.common.stages.Dialog;
import com.sjjapps.partygame.common.stages.GameUiStage;
import com.sjjapps.partygame.network.NetworkHelper;
import com.sjjapps.partygame.screens.mainmenu.MainMenu;

/**
 * Created by Shane Jansen on 1/3/16.
 */
public abstract class GameRealm extends DialogRealm implements NetworkHelper.NetworkInterface,
        GameUiStage.GameUiStageInterface{
    private boolean mIsLoaded;
    private GameUiStage mGameUiStage;

    public GameRealm() {
        super();
        mIsLoaded = false;
        // Loading assets
        GameUiStage.addAssets();
    }

    public void finishedLoading() {
        // Setup
        mIsLoaded = true;

        // Game UI stage
        mGameUiStage = new GameUiStage(this);
        addStage(mGameUiStage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (!mIsLoaded) {
            if (Game.ASSETS.update()) finishedLoading();
        }
    }

    @Override
    public void startGame() {
        Game.NETWORK_HELPER.setNetworkInterface(this);
    }

    @Override
    public void addServerListeners() {

    }

    @Override
    public void addClientListeners() {

    }

    @Override
    public void removeListeners() {

    }

    @Override
    public void clientDisconnected() {

    }

    @Override
    public void serverDisconnected() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                addDialog(new Alert(new Dialog.DialogInterface() {
                    @Override
                    public void btnExitPressed() {
                        Game.NETWORK_HELPER.getEndPoint().close();
                        changeRealm(new MainMenu());
                    }
                }, "You have been disconnected from the server."));
            }
        });
    }
}
