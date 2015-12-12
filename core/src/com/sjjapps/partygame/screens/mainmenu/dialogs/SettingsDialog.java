package com.sjjapps.partygame.screens.mainmenu.dialogs;

import com.sjjapps.partygame.actors.WidgetFactory;
import com.sjjapps.partygame.common.Dialog;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public class SettingsDialog extends Dialog {

    public static void addAssets() {
        Dialog.addAssets();
        WidgetFactory.addAssets();
    }

    public SettingsDialog() {
        super(8f/10f);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
