package com.sjjapps.partygame.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.managers.FilePathManager;

/**
 * Created by Shane Jansen on 11/17/15.
 */
public class StdTextButton implements Disposable {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.FONT, BitmapFont.class),
            new Asset(FilePathManager.BUTTON, Texture.class),
            new Asset(FilePathManager.BUTTON_DOWN, Texture.class)
    };
    protected TextButton mTextButton;
    private BitmapFont mFont;
    private Texture mTextureButton, mTextureButtonDown;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public StdTextButton(String text) {
        mFont = Game.ASSETS.get(mAssets[0].file);
        mTextureButton = Game.ASSETS.get(mAssets[1].file);
        mTextureButtonDown = Game.ASSETS.get(mAssets[2].file);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = mFont;
        style.up = new TextureRegionDrawable(new TextureRegion(mTextureButton));
        style.down = new TextureRegionDrawable(new TextureRegion(mTextureButtonDown));

        mTextButton = new TextButton(text, style);
    }

    public TextButton getTextButton() {
        return mTextButton;
    }

    @Override
    public void dispose() {
        for (Asset a: mAssets) {
            Game.ASSETS.unload(a.file);
        }
        mFont.dispose();
        mTextureButton.dispose();
        mTextureButtonDown.dispose();
    }
}
