package com.sjjapps.partygame.common;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.sjjapps.partygame.common.actors.WidgetFactory;

/**
 * Created by Shane Jansen on 12/14/15.
 */
public class AlertTextField extends Dialog {
    private static final float WIDGET_SCALE = 4f / 10f;

    public static void addAssets() {
        Dialog.addAssets();
        WidgetFactory.addAssets();
    }

    public AlertTextField(DialogInterface dialogInterface, BitmapFont font, String hint) {
        super(dialogInterface, 5f / 10f);

        // Create views
        float widgetWidth = getCamera().viewportWidth * WIDGET_SCALE;
        float widgetHeight = widgetWidth * (4f / 10f);
        TextField etAlert = WidgetFactory.getInstance().getStdTextField(widgetWidth * 2, widgetHeight, font, hint);
        TextButton btnOk = WidgetFactory.getInstance().getStdButton(widgetWidth, widgetHeight, font, "Continue");

        // Create table
        Table table = new Table();
        table.add(etAlert).width(etAlert.getWidth()).height(etAlert.getHeight());
        table.row();
        table.add(btnOk).width(btnOk.getWidth()).height(btnOk.getHeight()).padTop(20f);
        table.setFillParent(true);
        table.pack();
        addActor(table);
    }

    public AlertTextField(DialogInterface dialogInterface, String hint) {
        this(dialogInterface, WidgetFactory.mBfNormalLg, hint);
    }
}
