package com.sjjapps.partygame.common;

import com.badlogic.gdx.Gdx;
import com.sjjapps.partygame.common.models.MiniGame;
import com.sjjapps.partygame.common.models.Size;
import com.sjjapps.partygame.common.realms.Realm;
import com.sjjapps.partygame.screens.games.runaway.RunAway;

/**
 * Created by Shane Jansen on 12/11/15.
 */
public class Utils {

    public static Size scaleUniformly(float height, float width, float desiredWidth) {
        float ratio = height / width;
        float resizedHeight = desiredWidth * ratio;
        return new Size(desiredWidth, resizedHeight);
    }

    public static Realm createRealmFromName(MiniGame miniGame) {
        String name = miniGame.getName();
        if (name.equals("Run Away")) return new RunAway();
        return null;
    }

    public static float getUnitsInPixel(float worldWidth) {
        return worldWidth / (float) Gdx.graphics.getWidth();
    }
}
