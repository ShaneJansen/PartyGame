package com.sjjapps.partygame.screens.lobby.stages;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.actors.WidgetFactory;
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
        void btnBackClicked();
        void btnStartClicked();
    }

    public UiStage(UiInterface uiInterface) {
        super(new ScreenViewport(), Game.SPRITE_BATCH);
        this.mUiInterface = uiInterface;

        // Create views
        float exitButtonSize = getCamera().viewportWidth * (1f / 10f);
        TextButton btnBack = WidgetFactory.getInstance().getStdButton(exitButtonSize, exitButtonSize,
                WidgetFactory.mBfNormalRg, "Back");
        mLblAddress = WidgetFactory.getInstance().getStdLabel(getCamera().viewportWidth * (4f / 10f),
                getCamera().viewportHeight * (1f / 10f), WidgetFactory.mBfNormalRg, "");
        mLblPlayers = WidgetFactory.getInstance().getStdLabel(getCamera().viewportWidth * (3f / 10f),
                getCamera().viewportHeight * (7f / 10f), WidgetFactory.mBfNormalRg, "Players:");
        float startButtonSize = getCamera().viewportWidth * (3f / 10f);
        mBtnStart = WidgetFactory.getInstance().getStdButton(startButtonSize, startButtonSize * (3f / 10f),
                WidgetFactory.mBfNormalRg, "Start");
        //mBtnStart.setVisible(false);

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
        Table gamesTable = new Table();
        WidgetGroup widgetGroup = new WidgetGroup();
        ScrollPane scrollPane = new ScrollPane(widgetGroup);
        widgetGroup.addActor();
        gamesTable.add(mLblPlayers).width(mLblPlayers.getWidth()).height(mLblPlayers.getHeight());
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
