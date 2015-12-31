package com.sjjapps.partygame.common.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.WidgetFactory;
import com.sjjapps.partygame.common.models.MiniGame;
import com.sjjapps.partygame.network.User;

/**
 * Created by Shane Jansen on 12/30/15.
 */
public class GameUiStage extends Stage {
    private Label mLblName, mLblRound, mLblScore;

    public static void addAssets() {
        WidgetFactory.addAssets();
    }

    public GameUiStage() {
        super(new ScreenViewport(), Game.SPRITE_BATCH);

        // Create views
        float labelWidth = getCamera().viewportWidth * (2.5f / 10f);
        mLblName = WidgetFactory.getInstance().getStdLabel(labelWidth, labelWidth * (2f / 10f),
                WidgetFactory.mBfNormalRg, "Name");
        mLblRound = WidgetFactory.getInstance().getStdLabel(labelWidth, labelWidth * (2f / 10f),
                WidgetFactory.mBfNormalRg, "Round: ");
        mLblScore = WidgetFactory.getInstance().getStdLabel(labelWidth, labelWidth * (2f / 10f),
                WidgetFactory.mBfNormalRg, "Score: ");

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
    }

    public void updateUi() {
        User user = Game.NETWORK_HELPER.findThisUser();
        MiniGame miniGame = Game.NETWORK_HELPER.getGameState().getMiniGames().iterator().next();
        getLblName().setText(user.getName());
        getLblScore().setText("Score: " + user.getScore());
        getLblRound().setText("Round: " + miniGame.getCurrentRound() + "/" + miniGame.getNumRounds());
    }

    public Label getLblName() {
        return mLblName;
    }

    public Label getLblRound() {
        return mLblRound;
    }

    public Label getLblScore() {
        return mLblScore;
    }
}
