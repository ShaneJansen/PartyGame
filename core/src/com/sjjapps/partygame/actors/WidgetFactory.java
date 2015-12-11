package com.sjjapps.partygame.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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

    public static Skin getButtonSkin() {
        Skin skin = new Skin();
        skin.add("font", Game.ASSETS.get(mAssets[0].file));
        skin.add("up", Game.ASSETS.get(mAssets[1].file));
        skin.add("down", Game.ASSETS.get(mAssets[2].file));
        return skin;
    }

    public static TextButton getStdButton(float viewportWidth, float scaleWidth, String text) {
        Skin skin = getButtonSkin();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = skin.get("font", BitmapFont.class);
        style.up = new TextureRegionDrawable(new TextureRegion(skin.get("up", Texture.class)));
        style.down = new TextureRegionDrawable(new TextureRegion(skin.get("down", Texture.class)));

        TextButton textButton = new TextButton(text, style);
        Size size = Utils.scaleScreenSize(textButton.getHeight(), textButton.getWidth(),
                viewportWidth, scaleWidth);
        textButton.getLabel().setFontScale(size.height / textButton.getHeight());
        textButton.setWidth(size.width);
        textButton.setHeight(size.height);
        return textButton;
    }
}
