package com.sjjapps.partygame.screens.mainmenu.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.actors.WidgetFactory;
import com.sjjapps.partygame.common.Dialog;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public class SettingsDialog extends Dialog {
    private static final float WIDGET_SCALE = 3f/10f;
    private TextField mTfName;
    private TextButton mBtnSave;

    public static void addAssets() {
        Dialog.addAssets();
        WidgetFactory.addAssets();
    }

    public SettingsDialog(DialogInterface dialogInterface) {
        super(dialogInterface, 8f/10f);

        // Create views
        mTfName = WidgetFactory.getStdTextField(Gdx.graphics.getWidth(), WIDGET_SCALE, "Enter your name here.");
        mBtnSave = WidgetFactory.getStdButton(Gdx.graphics.getWidth(), WIDGET_SCALE, "Test");

        // Create table
        Table table = new Table();
        table.add(mTfName).width(mTfName.getWidth()).height(mTfName.getHeight());
        table.row();
        table.add(mBtnSave).width(mBtnSave.getWidth()).height(mBtnSave.getHeight()).padTop(20f);
        table.setFillParent(true);
        table.pack();
        table.debug();
        //table.setPosition(0, 0);
        addActor(table);

        // Listeners
        mBtnSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.log("TEST");
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        mBtnSave.getSkin().dispose();
    }
}
