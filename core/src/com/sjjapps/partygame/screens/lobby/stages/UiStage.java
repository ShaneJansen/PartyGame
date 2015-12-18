package com.sjjapps.partygame.screens.lobby.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.actors.WidgetFactory;

/**
 * Created by Shane Jansen on 12/17/15.
 */
public class UiStage extends Stage {
    private UiInterface mUiInterface;

    public static void addAssets() {
        WidgetFactory.addAssets();
    }

    public interface UiInterface {
        void btnBackClicked();
    }

    public UiStage(UiInterface uiInterface, String ipAddress) {
        super(new ScreenViewport(), Game.SPRITE_BATCH);
        this.mUiInterface = uiInterface;

        // Create views
        Label lblAddress = WidgetFactory.getInstance().getStdLabel(getCamera().viewportWidth * (4f / 10f),
                getCamera().viewportHeight * (1f / 10f), WidgetFactory.mBfNormalRg, "Server IP: " + ipAddress);

        // Create table
        Table table = new Table();
        table.add(lblAddress).width(lblAddress.getWidth()).height(lblAddress.getHeight());
        table.setFillParent(true);
        table.setDebug(true);
        table.pack();
        addActor(table);
    }
}
