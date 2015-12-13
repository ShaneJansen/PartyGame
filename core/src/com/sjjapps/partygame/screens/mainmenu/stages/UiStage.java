package com.sjjapps.partygame.screens.mainmenu.stages;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
    private static final float BUTTON_SCALE = 3f/10f;
    private UiInterface mInterface;
    private TextButton mBtnPlay, mBtnCredits, mBtnSettings;

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
        mBtnPlay = WidgetFactory.getStdButton(getCamera().viewportWidth, BUTTON_SCALE, StringManager.BUTTON_PLAY);
        mBtnCredits = WidgetFactory.getStdButton(getCamera().viewportWidth, BUTTON_SCALE, StringManager.BUTTON_CREDITS);
        mBtnSettings = WidgetFactory.getStdButton(getCamera().viewportWidth, BUTTON_SCALE, StringManager.BUTTON_SETTINGS);

        // Create table
        Table table = new Table();
        table.add(mBtnPlay).width(mBtnPlay.getWidth()).height(mBtnPlay.getHeight());
        table.row();
        table.add(mBtnCredits).width(mBtnCredits.getWidth()).height(mBtnCredits.getHeight()).padTop(20f);
        table.row();
        table.add(mBtnSettings).width(mBtnSettings.getWidth()).height(mBtnSettings.getHeight()).padTop(20f);
        table.setFillParent(true);
        table.pack();
        //table.right().bottom(); // Alignment is center by default
        //table.debug();
        addActor(table);
        //table.addAction(Actions.fadeIn(100f)); // Not working

        // Listeners
        mBtnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mInterface.btnPlayClicked();
            }
        });
        mBtnCredits.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mInterface.btnCreditsClicked();
            }
        });
        mBtnSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mInterface.btnSettingsClicked();
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        mBtnPlay.getSkin().dispose();
        mBtnCredits.getSkin().dispose();
        mBtnSettings.getSkin().dispose();
    }
}
