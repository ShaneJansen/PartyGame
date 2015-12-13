package com.sjjapps.partygame.screens.mainmenu.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.actors.WidgetFactory;
import com.sjjapps.partygame.common.Dialog;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public class SettingsDialog extends Dialog {
    private static final float BUTTON_SCALE = 3f/10f;
    private TextButton mBtnTest;

    public static void addAssets() {
        Dialog.addAssets();
        WidgetFactory.addAssets();
    }

    public SettingsDialog(DialogInterface dialogInterface) {
        super(dialogInterface, 8f/10f);

        // Create views
        mBtnTest = WidgetFactory.getStdButton(Gdx.graphics.getWidth(), BUTTON_SCALE, "Test");

        // Create table
        Table table = new Table();
        table.add(mBtnTest).width(mBtnTest.getWidth()).height(mBtnTest.getHeight());
        table.setFillParent(true);
        table.pack();
        table.debug();
        table.setPosition(0, 0);
        addActor(table);

        // Listeners
        mBtnTest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.log("TEST");
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        mBtnTest.getSkin().dispose();
    }
}
