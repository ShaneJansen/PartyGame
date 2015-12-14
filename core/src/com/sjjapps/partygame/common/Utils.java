package com.sjjapps.partygame.common;

import com.sjjapps.partygame.common.models.Size;

/**
 * Created by Shane Jansen on 12/11/15.
 */
public class Utils {

    public static Size scaleScreenSize(float height, float width, float desiredWidth) {
        float ratio = height / width;
        float resizedHeight = desiredWidth * ratio;
        return new Size(desiredWidth, resizedHeight);
    }
}
