package com.sjjapps.partygame.screens.mainmenu.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Controller;
import com.sjjapps.partygame.managers.StringManager;
import com.sjjapps.partygame.models.StdTextButton;

/**
 * Created by Shane Jansen on 11/27/15.
 */
public class UiController extends Stage implements Controller {
    private UiInterface mInterface;
    private StdTextButton mBtnPlay, mBtnCustomWords, mBtnSettings;

    public static void addAssets() {
        StdTextButton.addAssets();
    }

    public UiController(UiInterface uiInterface) {
        super(new FitViewport(Game.WORLD_WIDTH, Game.WORLD_HEIGHT));
        this.mInterface = uiInterface;

        // Create Views
        mBtnPlay = new StdTextButton(StringManager.BUTTON_PLAY);
        mBtnCustomWords = new StdTextButton(StringManager.BUTTON_CUSTOM_WORDS);
        mBtnSettings = new StdTextButton(StringManager.BUTTON_SETTINGS);

        // Create table
        Table table = new Table();
        table.add(mBtnPlay.getTextButton()); // Table add returns a cell which can be used to adjust size, padding, etc
        table.row(); // Moves to the next rows in a table
        table.add(mBtnCustomWords.getTextButton()).padTop(20f);
        table.row();
        table.add(mBtnSettings.getTextButton()).padTop(20f);
        table.setFillParent(true); // Sets the table to fill the entire stage
        table.pack();
        //table.right().bottom(); // Alignment is center by default
        //table.setDebug(true);
        addActor(table);
        //table.addAction(fadeIn(2f));

        // Listeners
        mBtnPlay.getTextButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mInterface.btnPlayClicked();
            }
        });
        mBtnCustomWords.getTextButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mInterface.btnCustomWordsClicked();
            }
        });
        mBtnSettings.getTextButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mInterface.btnSettingsClicked();
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        getViewport().apply();
        act(delta);
        draw();
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
        mBtnPlay.dispose();
        mBtnCustomWords.dispose();
        mBtnSettings.dispose();
    }
}
