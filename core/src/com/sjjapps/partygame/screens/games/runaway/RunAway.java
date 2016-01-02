package com.sjjapps.partygame.screens.games.runaway;

import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.realms.DialogRealm;
import com.sjjapps.partygame.common.stages.Alert;
import com.sjjapps.partygame.common.stages.Dialog;
import com.sjjapps.partygame.common.stages.GameUiStage;
import com.sjjapps.partygame.network.NetworkHelper;
import com.sjjapps.partygame.screens.games.runaway.stages.GameStage;
import com.sjjapps.partygame.screens.games.runaway.stages.UiStage;
import com.sjjapps.partygame.screens.mainmenu.MainMenu;

/**
 * Created by Shane Jansen on 12/21/15.
 */
public class RunAway extends DialogRealm implements NetworkHelper.NetworkInterface,
        GameUiStage.GameUiStageInterface {
    private boolean mIsLoaded = false;
    private GameUiStage mGameUiStage;
    private UiStage mUiStage;
    private GameStage mGameStage;

    public RunAway() {
        super();
        // Loading assets
        GameUiStage.addAssets();
        UiStage.addAssets();
        GameStage.addAssets();
    }

    private void finishedLoading() {
        // Setup
        mIsLoaded = true;

        // Game UI stage
        mGameUiStage = new GameUiStage(this);
        addStage(mGameUiStage);
        mGameUiStage.updateUi();

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
        addDialog(new Alert(new Dialog.DialogInterface() {
            @Override
            public void btnExitPressed() {
                Game.NETWORK_HELPER.getEndPoint().close();
                changeRealm(new MainMenu());
            }
        }, "You have been disconnected from the server."));
    }
}
