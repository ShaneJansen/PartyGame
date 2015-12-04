package com.sjjapps.partygame.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.managers.FilePathManager;

/**
 * Created by Shane Jansen on 11/17/15.
 */
public class StdTextButton {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.FONT, BitmapFont.class),
            new Asset(FilePathManager.BUTTON, Texture.class),
            new Asset(FilePathManager.BUTTON_DOWN, Texture.class)
    };
    protected TextButton mTextButton;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public StdTextButton(String text) {
        BitmapFont mFont = Game.ASSETS.get(mAssets[0].file);
        Texture mTextureButton = Game.ASSETS.get(mAssets[1].file);
        Texture mTextureButtonDown = Game.ASSETS.get(mAssets[2].file);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = mFont;
        style.up = new TextureRegionDrawable(new TextureRegion(mTextureButton));
        style.down = new TextureRegionDrawable(new TextureRegion(mTextureButtonDown));

        mTextButton = new TextButton(text, style);
    }

    public TextButton getTextButton() {
        return mTextButton;
    }
}
