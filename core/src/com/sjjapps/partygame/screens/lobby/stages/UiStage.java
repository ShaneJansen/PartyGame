package com.sjjapps.partygame.screens.lobby.stages;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.actors.WidgetFactory;
import com.sjjapps.partygame.common.models.MiniGame;
import com.sjjapps.partygame.managers.DataManager;

/**
 * Created by Shane Jansen on 12/17/15.
 */
public class UiStage extends Stage {
    private UiInterface mUiInterface;
    private Label mLblAddress;
    private Label mLblPlayers;
    private TextButton mBtnStart;

    public static void addAssets() {
        WidgetFactory.addAssets();
    }

    public interface UiInterface {
        void btnAddSubtractClicked();
        void btnBackClicked();
        void btnStartClicked();
    }

    public UiStage(UiInterface uiInterface) {
        super(new ScreenViewport(), Game.SPRITE_BATCH);
        this.mUiInterface = uiInterface;

        // Create views
        float exitButtonSize = getCamera().viewportWidth * (1f / 10f);
        float startButtonSize = getCamera().viewportWidth * (3f / 10f);
        TextButton btnBack = WidgetFactory.getInstance().getStdButton(exitButtonSize, exitButtonSize,
                WidgetFactory.mBfNormalRg, "Back");
        mLblAddress = WidgetFactory.getInstance().getStdLabel(getCamera().viewportWidth * (4f / 10f),
                getCamera().viewportHeight * (1f / 10f), WidgetFactory.mBfNormalRg, "");
        mLblPlayers = WidgetFactory.getInstance().getStdLabel(getCamera().viewportWidth * (3f / 10f),
                getCamera().viewportHeight * (7f / 10f), WidgetFactory.mBfNormalRg, "Players:");
        mBtnStart = WidgetFactory.getInstance().getStdButton(startButtonSize, startButtonSize * (3f / 10f),
                WidgetFactory.mBfNormalRg, "Start");
        Label lblMore = WidgetFactory.getInstance().getStdLabel(startButtonSize, exitButtonSize,
                WidgetFactory.mBfNormalRg, "More games coming soon!");

        // Create exitTable
        Table exitTable = new Table();
        exitTable.add(btnBack).width(btnBack.getWidth()).height(btnBack.getHeight());
        exitTable.top().left();
        exitTable.setFillParent(true);
        exitTable.pack();
        if (DataManager.DEBUG) exitTable.debug();
        addActor(exitTable);

        // Create ipTable
        Table ipTable = new Table();
        ipTable.add(mLblAddress).width(mLblAddress.getWidth()).height(mLblAddress.getHeight());
        ipTable.right().bottom();
        ipTable.setFillParent(true);
        ipTable.pack();
        if (DataManager.DEBUG) ipTable.debug();
        addActor(ipTable);

        // Create games table
        float gamesWidgetSize = getCamera().viewportWidth * (1f / 20f);
        Table gamesTable = new Table();
        for (final MiniGame miniGame: Game.NETWORK_HELPER.getGameState().getMiniGameTypes()) {
            TextButton btnSubtract = WidgetFactory.getInstance().getStdButton(gamesWidgetSize, gamesWidgetSize,
                    WidgetFactory.mBfNormalLg, "-");
            final Label lblNumRounds = WidgetFactory.getInstance().getStdLabel(gamesWidgetSize, gamesWidgetSize,
                    WidgetFactory.mBfNormalRg, "0");
            TextButton btnAdd = WidgetFactory.getInstance().getStdButton(gamesWidgetSize, gamesWidgetSize,
                    WidgetFactory.mBfNormalLg, "+");
            Label lblGameName = WidgetFactory.getInstance().getStdLabel(getCamera().viewportWidth * (2f / 10f),
                    gamesWidgetSize, WidgetFactory.mBfNormalRg, miniGame.getName());
            gamesTable.add(btnSubtract).width(btnSubtract.getWidth()).height(btnSubtract.getHeight());
            gamesTable.add(lblNumRounds).width(lblNumRounds.getWidth()).height(lblNumRounds.getHeight());
            gamesTable.add(btnAdd).width(btnAdd.getWidth()).height(btnAdd.getHeight());
            gamesTable.add(lblGameName).width(lblGameName.getWidth()).height(lblGameName.getHeight()).padLeft(10);
            gamesTable.row();
            // Adding add/subtract button listeners
            btnAdd.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (miniGame.getNumRounds() < 5) {
                        miniGame.setNumRounds(miniGame.getNumRounds() + 1);
                        lblNumRounds.setText(String.valueOf(miniGame.getNumRounds()));
                        Game.NETWORK_HELPER.getGameState().getMiniGames().add(miniGame);
                    }
                    mUiInterface.btnAddSubtractClicked();
                }
            });
            btnSubtract.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (miniGame.getNumRounds() > 0) {
                        miniGame.setNumRounds(miniGame.getNumRounds() - 1);
                        lblNumRounds.setText(String.valueOf(miniGame.getNumRounds()));
                        Game.NETWORK_HELPER.getGameState().getMiniGames().add(miniGame);
                        if (miniGame.getNumRounds() == 0) {
                            Game.NETWORK_HELPER.getGameState().getMiniGames().remove(miniGame);
                        }
                    }
                    mUiInterface.btnAddSubtractClicked();
                }
            });
        }
        gamesTable.add(lblMore).width(lblMore.getWidth()).height(lblMore.getHeight()).colspan(4);
        gamesTable.pack();
        if (DataManager.DEBUG) gamesTable.debug();

        // Create playersTable
        Table playersTable = new Table();
        playersTable.add(mLblPlayers).width(mLblPlayers.getWidth()).height(mLblPlayers.getHeight());
        if (Game.NETWORK_HELPER.isServer()) playersTable.add(gamesTable);
        playersTable.row();
        playersTable.add(mBtnStart).width(mBtnStart.getWidth()).height(mBtnStart.getHeight());
        playersTable.setFillParent(true);
        playersTable.pack();
        if (DataManager.DEBUG) playersTable.debug();
        addActor(playersTable);

        // Listeners
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mUiInterface.btnBackClicked();
            }
        });
        mBtnStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mUiInterface.btnStartClicked();
            }
        });
    }

    public Label getLblAddress() {
        return mLblAddress;
    }

    public Label getLblPlayers() {
        return mLblPlayers;
    }

    public TextButton getBtnStart() {
        return mBtnStart;
    }
}
