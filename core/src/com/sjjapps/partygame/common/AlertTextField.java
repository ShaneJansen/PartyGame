package com.sjjapps.partygame.common;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sjjapps.partygame.common.actors.HintTextField;
import com.sjjapps.partygame.common.actors.WidgetFactory;

/**
 * Created by Shane Jansen on 12/14/15.
 */
public class AlertTextField extends Dialog {
    private static final float WIDGET_SCALE = 4f / 10f;
    private HintTextField mTf;
    private TextButton mBtnContinue;

    public static void addAssets() {
        Dialog.addAssets();
        WidgetFactory.addAssets();
    }

    public interface AlertTextFieldInterface {
        void btnContinueClicked(String tfText);
    }

    public AlertTextField(DialogInterface dialogInterface, boolean cancelable,
                          final AlertTextFieldInterface textFieldInterface, BitmapFont font, String hint) {
        super(dialogInterface, 5f / 10f, cancelable);

        // Create views
        float widgetWidth = getCamera().viewportWidth * WIDGET_SCALE;
        float widgetHeight = widgetWidth * (4f / 10f);
        mTf = WidgetFactory.getInstance().getStdTextField(widgetWidth * 2, widgetHeight, font, hint);
        mBtnContinue = WidgetFactory.getInstance().getStdButton(widgetWidth, widgetHeight, font, "Continue");

        // Create table
        Table table = new Table();
        table.add(mTf).width(mTf.getWidth()).height(mTf.getHeight());
        table.row();
        table.add(mBtnContinue).width(mBtnContinue.getWidth()).height(mBtnContinue.getHeight()).padTop(20f);
        table.setFillParent(true);
        table.pack();
        addActor(table);

        // Listeners
        mBtnContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (mTf.didClear()) textFieldInterface.btnContinueClicked(mTf.getText());
                else textFieldInterface.btnContinueClicked("");
            }
        });
    }

    public AlertTextField(DialogInterface dialogInterface, AlertTextFieldInterface textFieldInterface,
                          String hint) {
        this(dialogInterface, true, textFieldInterface, WidgetFactory.mBfNormalLg, hint);
    }

    public HintTextField getTf() {
        return mTf;
    }

    public TextButton getBtnContinue() {
        return mBtnContinue;
    }
}
