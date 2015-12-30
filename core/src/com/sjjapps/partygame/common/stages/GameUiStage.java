package com.sjjapps.partygame.common.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.WidgetFactory;

/**
 * Created by Shane Jansen on 12/30/15.
 */
public class GameUiStage extends Stage {
    private Label mLblScore;

    public GameUiStage(float worldWidth, float worldHeight) {
        super(new FitViewport(worldWidth, worldHeight), Game.SPRITE_BATCH);

        // Create views
        float labelWidth = getCamera().viewportWidth * (2f / 10f);
        mLblScore = WidgetFactory.getInstance().getStdLabel(labelWidth, labelWidth * (1f / 10f),
                WidgetFactory.mBfNormalSm, "");
    }

    @Override
    public void draw() {
        super.draw();
    }
}
