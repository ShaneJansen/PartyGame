package com.sjjapps.partygame.common.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.WidgetFactory;
import com.sjjapps.partygame.common.models.MiniGame;
import com.sjjapps.partygame.common.models.User;
import com.sjjapps.partygame.network.NetworkHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Shane Jansen on 12/30/15.
 */
public class GameUiStage extends Stage implements NetworkHelper.NetworkInterface {
    private Listener mListener;
    private GameUiStageInterface mInterface;
    private Label mLblName, mLblRound, mLblScore, mLblReady;

    public static void addAssets() {
        WidgetFactory.addAssets();
    }

    public interface GameUiStageInterface {
        void startGame();
    }

    public GameUiStage(GameUiStageInterface gameUiStageInterface) {
        super(new ScreenViewport(), Game.SPRITE_BATCH);
        mInterface = gameUiStageInterface;
        Game.NETWORK_HELPER.setNetworkInterface(this);
        Game.PAUSED = true;

        // Create views
        float labelWidth = getCamera().viewportWidth * (2.5f / 10f);
        mLblName = WidgetFactory.getInstance().getStdLabel(labelWidth, labelWidth * (2f / 10f),
                WidgetFactory.mBfNormalRg, "Name");
        mLblRound = WidgetFactory.getInstance().getStdLabel(labelWidth, labelWidth * (2f / 10f),
                WidgetFactory.mBfNormalRg, "Round: ");
        mLblScore = WidgetFactory.getInstance().getStdLabel(labelWidth, labelWidth * (2f / 10f),
                WidgetFactory.mBfNormalRg, "Score: ");
        mLblReady = WidgetFactory.getInstance().getStdLabel(getCamera().viewportWidth,
                getCamera().viewportHeight, WidgetFactory.mBfNormalLg, "Ready...");

        // Create tables
        Table tblName = new Table();
        tblName.add(mLblName).width(mLblName.getWidth()).height(mLblName.getHeight());
        tblName.setFillParent(true);
        tblName.top().left().pack();
        addActor(tblName);

        Table tblRound = new Table();
        tblRound.add(mLblRound).width(mLblRound.getWidth()).height(mLblRound.getHeight());
        tblRound.setFillParent(true);
        tblRound.bottom().right().pack();
        addActor(tblRound);

        Table tblScore = new Table();
        tblScore.add(mLblScore).width(mLblScore.getWidth()).height(mLblScore.getHeight());
        tblScore.setFillParent(true);
        tblScore.top().right().pack();
        addActor(tblScore);

        Table tblReady = new Table();
        tblReady.add(mLblReady).width(mLblReady.getWidth()).height(mLblReady.getHeight());
        tblReady.setFillParent(true);
        tblReady.pack();
        addActor(tblReady);

        updateUi();
        userReady();
    }

    public void updateUi() {
        User user = Game.NETWORK_HELPER.findThisUser();
        MiniGame miniGame = Game.NETWORK_HELPER.gameState.getMiniGames().iterator().next();
        mLblName.setText(user.getName());
        mLblScore.setText("Score: " + user.getScore());
        mLblRound.setText("Round: " + miniGame.getCurrentRound() + " of " + miniGame.getNumRounds());
    }

    public void userReady() {
        if (!Game.NETWORK_HELPER.isServer()) {
            Client client = (Client) Game.NETWORK_HELPER.getEndPoint();
            User user = Game.NETWORK_HELPER.findThisUser();
            user.setIsReady(true);
            client.sendTCP(user);
        }
    }

    public void resetUsersReady() {
        for (User u: Game.NETWORK_HELPER.users.getUsers()) {
            u.setIsReady(false);
        }
    }

    /**
     * Called when everyone has finished loading.
     * Starts a timer for 3 seconds, displays go
     * text for 2 seconds, and calls a callback
     * method.
     */
    public void finishedLoading() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mLblReady.setText("GO!");
                Game.PAUSED = false;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mLblReady.setVisible(false);
                        mInterface.startGame();
                    }
                }, 2000);
            }
        }, 3000);
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

    }
}
