package com.sjjapps.partygame.screens.games.runaway.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.WidgetFactory;
import com.sjjapps.partygame.managers.DataManager;

/**
 * Created by Shane Jansen on 12/28/15.
 */
public class UiStage extends Stage {
    private Touchpad mTouchpad;

    public static void addAssets() {
        WidgetFactory.addAssets();
    }

    public UiStage() {
        super(new ScreenViewport(), Game.SPRITE_BATCH);

        float touchpadSize = getCamera().viewportWidth * (2f / 10f);
        mTouchpad = WidgetFactory.getInstance().getStdTouchpad(touchpadSize, touchpadSize);

        Table table = new Table();
        table.add(mTouchpad).width(mTouchpad.getWidth()).height(mTouchpad.getHeight());;
        table.setFillParent(true);
        table.bottom().left().pack();
        if (DataManager.DEBUG) table.debug();
        addActor(table);
    }

    public Touchpad getTouchpad() {
        return mTouchpad;
    }
}
