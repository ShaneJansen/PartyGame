package com.sjjapps.partygame.screens.mainmenu.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

        // Create Views
        mBtnPlay = WidgetFactory.stdButton(getCamera().viewportWidth, BUTTON_SCALE, StringManager.BUTTON_PLAY);
        mBtnCredits = WidgetFactory.stdButton(getCamera().viewportWidth, BUTTON_SCALE, StringManager.BUTTON_CREDITS);
        mBtnSettings = WidgetFactory.stdButton(getCamera().viewportWidth, BUTTON_SCALE, StringManager.BUTTON_SETTINGS);

        mBtnPlay.setPosition(getCamera().viewportWidth * 0.5f, getCamera().viewportHeight * 0.5f);
        addActor(mBtnPlay);

        /*// Create table
        Table table = new Table();
        table.add(mBtnPlay.getTextButton());
        table.row(); // Moves to the next rows in a table
        table.add(mBtnCredits.getTextButton()).padTop(20f);
        table.row();
        table.add(mBtnSettings.getTextButton()).padTop(20f);
        table.setFillParent(true); // Sets the table to fill the entire stage
        table.pack();
        //table.right().bottom(); // Alignment is center by default
        table.debug();
        addActor(table);
        //table.addAction(fadeIn(2f));*/

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
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw() {
        super.draw();

        //TEST
        Game.SHAPE_RENDERER.setProjectionMatrix(getBatch().getProjectionMatrix());
        Game.SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
        Game.SHAPE_RENDERER.setColor(Color.YELLOW);
        Game.SHAPE_RENDERER.circle(getCamera().viewportWidth * 0.5f,
                getCamera().viewportHeight * 0.5f, 20);
        Game.SHAPE_RENDERER.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        mBtnPlay.getSkin().dispose(); // TODO: use skins instead?
    }
}
