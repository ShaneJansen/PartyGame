package com.sjjapps.partygame.common;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.sjjapps.partygame.actors.WidgetFactory;

/**
 * Created by Shane Jansen on 12/13/15.
 */
public class AlertDialog extends Dialog {
    private static final float WIDGET_SCALE = 9f/10f;

    public static void addAssets() {
        Dialog.addAssets();
        WidgetFactory.addAssets();

    }

    public AlertDialog(DialogInterface dialogInterface, float widthScale, String text) {
        super(dialogInterface, widthScale);

        // Create views
        Label lblAlert = WidgetFactory.getStdLabel(getViewport().getScreenWidth(), WIDGET_SCALE, text);

        // Create table
        Table table = new Table();
        table.add(lblAlert).width(lblAlert.getWidth()).height(lblAlert.getHeight()).fill();
        table.setFillParent(true);
        table.pack();
        table.debug();
        addActor(table);
    }
}
