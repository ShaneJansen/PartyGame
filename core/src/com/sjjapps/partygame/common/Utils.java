package com.sjjapps.partygame.common;

import com.sjjapps.partygame.models.Size;

/**
 * Created by Shane Jansen on 12/11/15.
 */
public class Utils {

    public static Size scaleScreenSize(float height, float width,
                                       float viewportWidth, float widthScale) {
        float ratio = height / width;
        float resizedWidth = viewportWidth * widthScale;
        float resizedHeight = resizedWidth * ratio;
        return new Size(resizedWidth, resizedHeight);
    }
}
