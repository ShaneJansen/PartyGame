package com.sjjapps.partygame.screens.lobby.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.actors.WidgetFactory;
import com.sjjapps.partygame.network.NetworkHelper;
import com.sjjapps.partygame.network.User;

/**
 * Created by Shane Jansen on 12/17/15.
 */
public class UiStage extends Stage {
    private UiInterface mUiInterface;
    private Label mLblAddress;
    private Label mLblPlayers;

    public static void addAssets() {
        WidgetFactory.addAssets();
    }

    public interface UiInterface {
        void btnBackClicked();
    }

    public UiStage(UiInterface uiInterface) {
        super(new ScreenViewport(), Game.SPRITE_BATCH);
        this.mUiInterface = uiInterface;

        // Create views
        mLblAddress = WidgetFactory.getInstance().getStdLabel(getCamera().viewportWidth * (4f / 10f),
                getCamera().viewportHeight * (1f / 10f), WidgetFactory.mBfNormalRg, "");
        mLblPlayers = WidgetFactory.getInstance().getStdLabel(getCamera().viewportWidth * (5f / 10f),
                getCamera().viewportHeight, WidgetFactory.mBfNormalRg, "Players:");

        // Create ipTable
        Table ipTable = new Table();
        ipTable.add(mLblAddress).width(mLblAddress.getWidth()).height(mLblAddress.getHeight());
        ipTable.right().bottom();
        ipTable.setFillParent(true);
        ipTable.pack();
        addActor(ipTable);

        // Create playersTable
        Table playersTable = new Table();
        playersTable.add(mLblPlayers).width(mLblPlayers.getWidth()).height(mLblPlayers.getHeight());
        playersTable.setFillParent(true);
        playersTable.pack();
        addActor(playersTable);
    }

    public void updateIpAddress(String ipAddress) {
        mLblAddress.setText("Server IP: " + ipAddress);
    }

    public void updatePlayerList() {
        String players = "Players:\n";
        for (User u: Game.NETWORK_HELPER.getNetworkUsers().users) {
            players += u.getName() + "\n";
        }
        mLblPlayers.setText(players);
    }
}
