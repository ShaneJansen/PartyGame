package com.sjjapps.partygame.common;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.sjjapps.partygame.common.actors.WidgetFactory;

/**
 * Created by Shane Jansen on 12/13/15.
 */
public class AlertDialog extends Dialog {
    private static final float WIDGET_SCALE = 8f / 10f;

    public static void addAssets() {
        Dialog.addAssets();
        WidgetFactory.addAssets();
    }

    public AlertDialog(DialogInterface dialogInterface, BitmapFont font, String text) {
        super(dialogInterface, 5f / 10f);

        // Create views
        float widgetWidth = getCamera().viewportWidth * WIDGET_SCALE;
        float widgetHeight = getCamera().viewportHeight * WIDGET_SCALE;
        Label lblAlert = WidgetFactory.getInstance().getStdLabel(widgetWidth, widgetHeight, font, text);

        // Create table
        Table table = new Table();
        table.add(lblAlert).width(lblAlert.getWidth()).height(lblAlert.getHeight());
        table.setFillParent(true);
        table.pack();
        table.debug();
        addActor(table);
    }

    public AlertDialog(DialogInterface dialogInterface, String text) {
        this(dialogInterface, WidgetFactory.mBfNormalLg, text);
    }
}
