package com.sjjapps.partygame.screens.mainmenu.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Dialog;
import com.sjjapps.partygame.common.actors.WidgetFactory;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public class SettingsDialog extends Dialog {
    private static final float WIDGET_SCALE = 4f / 10f;

    public static void addAssets() {
        Dialog.addAssets();
        WidgetFactory.addAssets();
    }

    public SettingsDialog(DialogInterface dialogInterface) {
        super(dialogInterface, 8f / 10f);

        // Create views
        float widgetWidth = getCamera().viewportWidth * WIDGET_SCALE;
        float widgetHeight = widgetWidth * (4f / 10f);
        TextField tfName = WidgetFactory.getInstance().getStdTextField(widgetWidth, widgetHeight,
                WidgetFactory.mBfNormalRg, "Enter your name here.");
        TextButton btnSave = WidgetFactory.getInstance().getStdButton(widgetWidth, widgetHeight,
                WidgetFactory.mBfNormalRg, "Test");

        // Create table
        Table table = new Table();
        table.add(tfName).width(tfName.getWidth()).height(tfName.getHeight());
        table.row();
        table.add(btnSave).width(btnSave.getWidth()).height(btnSave.getHeight()).padTop(20f);
        table.setFillParent(true);
        table.pack();
        addActor(table);

        // Listeners
        btnSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game.log("TEST");
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
