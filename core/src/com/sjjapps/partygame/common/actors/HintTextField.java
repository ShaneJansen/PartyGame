package com.sjjapps.partygame.common.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

/**
 * Created by Shane Jansen on 12/12/15.
 */
public class HintTextField extends TextField {
    private String mHint;
    private boolean mDidClear;

    public HintTextField(String hint, TextFieldStyle style) {
        super(hint, style);
        this.mHint = hint;

        addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (actor == HintTextField.this && focused && !mDidClear) {
                    setText("");
                    getStyle().fontColor = Color.WHITE;
                    mDidClear = true;
                }
                else if (actor == HintTextField.this && !focused && getText().equals("")) {
                    setText(mHint);
                    getStyle().fontColor = Color.GRAY;
                }
                super.keyboardFocusChanged(event, actor, focused);
            }
        });
    }
}
