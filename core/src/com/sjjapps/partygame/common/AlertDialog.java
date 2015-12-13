package com.sjjapps.partygame.common;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.sjjapps.partygame.actors.WidgetFactory;

/**
 * Created by Shane Jansen on 12/13/15.
 */
public class AlertDialog extends Dialog {
    private static final float WIDGET_SCALE = 3f/10f;
    private Label mLblAlert;

    public static void addAssets() {
        Dialog.addAssets();
        WidgetFactory.addAssets();
    }

    public AlertDialog(DialogInterface dialogInterface, float widthScale) {
        super(dialogInterface, widthScale);
    }


}
