package com.sjjapps.partygame.screens.mainmenu.dialogs;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.common.Dialog;
import com.sjjapps.partygame.managers.StringManager;
import com.sjjapps.partygame.models.StdTextButton;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public class SettingsDialog extends Dialog {
    private Stage mStage;
    private StdTextButton mBtnSave;

    public static void addAssets() {
        Dialog.addAssets();
        StdTextButton.addAssets();
    }

    public SettingsDialog(DialogInterface dialogInterface) {
        super(dialogInterface);
        mStage = new Stage(new ScreenViewport());
        ((OrthographicCamera) mStage.getViewport().getCamera()).zoom += ZOOM_AMOUNT;

        // Create views
        mBtnSave = new StdTextButton(StringManager.BUTTON_SAVE);

        // Create table
        Table table = new Table();
        table.add(mBtnSave.getTextButton()).width(mBtnSave.getTextButton().getWidth());
        table.setFillParent(true);
        table.pack();
        table.debug();
        mStage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        mStage.getViewport().apply();

        mStage.act(delta);
        mStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        mStage.getViewport().setScreenSize(mViewport.getScreenWidth(), mViewport.getScreenHeight());
        mStage.getViewport().setScreenPosition(mViewport.getScreenX(), mViewport.getScreenY());
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
        mStage.dispose();
        mBtnSave.dispose();
    }
}
