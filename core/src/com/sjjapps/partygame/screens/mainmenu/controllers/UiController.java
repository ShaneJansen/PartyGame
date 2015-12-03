package com.sjjapps.partygame.screens.mainmenu.controllers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.controllers.SjjController;
import com.sjjapps.partygame.managers.StringManager;
import com.sjjapps.partygame.models.Asset;
import com.sjjapps.partygame.models.StdTextButton;

/**
 * Created by Shane Jansen on 11/27/15.
 */
public class UiController extends Stage implements SjjController {

    public UiController(Camera camera) {
        super(new FitViewport(Game.WORLD_WIDTH, Game.WORLD_HEIGHT, camera));

        // Load Assets
        for (Asset a: StdTextButton.getNeededAssets()) Game.ASSETS.load(a.file, a.type);
    }

    @Override
    public void update(float deltaTime) {
        getViewport().apply();
        act(deltaTime);
        draw();
    }

    @Override
    public void didFinishLoading() {
        // Create Views
        StdTextButton btnPlay = new StdTextButton(StringManager.BUTTON_PLAY);
        StdTextButton btnCustomWords = new StdTextButton(StringManager.BUTTON_CUSTOM_WORDS);
        StdTextButton btnSettings = new StdTextButton(StringManager.BUTTON_SETTINGS);

        // Create table
        Table table = new Table();
        table.add(btnPlay.getModel()); // Table add returns a cell which can be used to adjust size, padding, etc
        table.row(); // Moves to the next rows in a table
        table.add(btnCustomWords.getModel()).padTop(20f);
        table.row();
        table.add(btnSettings.getModel()).padTop(20f);
        table.setFillParent(true); // Sets the table to fill the entire stage
        table.pack();
        //table.right().bottom(); // Alignment is center by default
        //table.setDebug(true);
        addActor(table);
        //table.addAction(fadeIn(2f));

        // Listeners
        btnPlay.getModel().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.log("Clicked play.");
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Asset a: StdTextButton.getNeededAssets()) Game.ASSETS.unload(a.file);
    }
}
