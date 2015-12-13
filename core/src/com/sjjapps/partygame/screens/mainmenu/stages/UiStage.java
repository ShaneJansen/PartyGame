package com.sjjapps.partygame.screens.mainmenu.stages;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.actors.WidgetFactory;
import com.sjjapps.partygame.managers.StringManager;

/**
 * Created by Shane Jansen on 11/27/15.
 */
public class UiStage extends Stage {
    private static final float WIDGET_SCALE = 3f/10f;
    private UiInterface mInterface;

    public static void addAssets() {
        WidgetFactory.addAssets();
    }

    public interface UiInterface {
        void btnPlayClicked();
        void btnCreditsClicked();
        void btnSettingsClicked();
    }

    public UiStage(UiInterface uiInterface) {
        super(new ScreenViewport(), Game.SPRITE_BATCH);
        this.mInterface = uiInterface;

        // Create views
        TextButton btnPlay = WidgetFactory.getStdButton(getCamera().viewportWidth, WIDGET_SCALE, StringManager.BUTTON_PLAY);
        TextButton btnCredits = WidgetFactory.getStdButton(getCamera().viewportWidth, WIDGET_SCALE, StringManager.BUTTON_CREDITS);
        TextButton btnSettings = WidgetFactory.getStdButton(getCamera().viewportWidth, WIDGET_SCALE, StringManager.BUTTON_SETTINGS);

        // Create table
        Table table = new Table();
        table.add(btnPlay).width(btnPlay.getWidth()).height(btnPlay.getHeight());
        table.row();
        table.add(btnCredits).width(btnCredits.getWidth()).height(btnCredits.getHeight()).padTop(20f);
        table.row();
        table.add(btnSettings).width(btnSettings.getWidth()).height(btnSettings.getHeight()).padTop(20f);
        table.setFillParent(true);
        table.pack();
        //table.right().bottom(); // Alignment is center by default
        //table.debug();
        addActor(table);
        //table.addAction(Actions.fadeIn(100f)); // Not working

        // Listeners
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mInterface.btnPlayClicked();
            }
        });
        btnCredits.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mInterface.btnCreditsClicked();
            }
        });
        btnSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mInterface.btnSettingsClicked();
            }
        });
    }
}
