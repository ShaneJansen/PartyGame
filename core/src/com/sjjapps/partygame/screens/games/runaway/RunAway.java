package com.sjjapps.partygame.screens.games.runaway;

import com.sjjapps.partygame.common.realms.GameRealm;
import com.sjjapps.partygame.screens.games.runaway.stages.GameStage;
import com.sjjapps.partygame.screens.games.runaway.stages.UiStage;

/**
 * Created by Shane Jansen on 12/21/15.
 */
public class RunAway extends GameRealm {
    private UiStage mUiStage;
    private GameStage mGameStage;

    public RunAway() {
        super();
        // Loading assets
        UiStage.addAssets();
        GameStage.addAssets();
    }

    @Override
    public void finishedLoading() {
        super.finishedLoading();

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
    public void removeListeners() {

    }

    @Override
    public void clientDisconnected() {

    }
}
