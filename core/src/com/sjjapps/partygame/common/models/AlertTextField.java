package com.sjjapps.partygame.common.models;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.sjjapps.partygame.common.Dialog;
import com.sjjapps.partygame.common.actors.WidgetFactory;

/**
 * Created by Shane Jansen on 12/14/15.
 */
public class AlertTextField extends Dialog {
    private static final float WIDGET_SCALE = 5f / 10f;

    public static void addAssets() {
        Dialog.addAssets();
        WidgetFactory.addAssets();
    }

    public AlertTextField(DialogInterface dialogInterface, float fontScale, String hint) {
        super(dialogInterface, 5f / 10f);

        // Create views
        float widgetWidth = getCamera().viewportWidth * WIDGET_SCALE;
        int fontSize = (int) (widgetWidth * fontScale);
        TextField etAlert = WidgetFactory.INSTANCE.getStdTextField(widgetWidth, fontSize, hint);

        // Create table
        Table table = new Table();
        table.add(etAlert).width(etAlert.getWidth()).height(etAlert.getHeight());
        table.setFillParent(true);
        table.pack();
        table.debug();
        addActor(table);
    }

    public AlertTextField(DialogInterface dialogInterface, String hint) {
        this(dialogInterface, 8f / 100f, hint);
    }
}
