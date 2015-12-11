package com.sjjapps.partygame.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Utils;
import com.sjjapps.partygame.managers.FilePathManager;
import com.sjjapps.partygame.models.Asset;
import com.sjjapps.partygame.models.Size;

/**
 * Created by Shane Jansen on 12/11/15.
 */
public class WidgetFactory {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.FONT, BitmapFont.class),
            new Asset(FilePathManager.BUTTON, Texture.class),
            new Asset(FilePathManager.BUTTON_DOWN, Texture.class)
    };

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public static TextButton stdButton(float viewportWidth, float scaleWidth, String text) {
        BitmapFont font = Game.ASSETS.get(mAssets[0].file);
        Texture textureButton = Game.ASSETS.get(mAssets[1].file);
        Texture textureButtonDown = Game.ASSETS.get(mAssets[2].file);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.up = new TextureRegionDrawable(new TextureRegion(textureButton));
        style.down = new TextureRegionDrawable(new TextureRegion(textureButtonDown));

        TextButton textButton = new TextButton(text, style);
        Size size = Utils.scaleScreenSize(textureButton.getHeight(), textureButton.getWidth(),
                viewportWidth, scaleWidth);
        textButton.setWidth(size.width);
        textButton.setHeight(size.height);
        textButton.getLabel().setFontScale(size.height / textureButton.getHeight());
        return textButton;
    }
}
