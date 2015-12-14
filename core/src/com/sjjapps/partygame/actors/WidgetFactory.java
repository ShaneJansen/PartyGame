package com.sjjapps.partygame.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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
            new Asset(FilePathManager.BUTTON_DOWN, Texture.class),
            new Asset(FilePathManager.EDIT_TEXT, Texture.class)
    };

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public static TextButton getStdButton(float width, String text) {
        Skin skin = new Skin();
        skin.add("font", Game.ASSETS.get(mAssets[0].file));
        skin.add("up", Game.ASSETS.get(mAssets[1].file));
        skin.add("down", Game.ASSETS.get(mAssets[2].file));

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = skin.get("font", BitmapFont.class);
        style.up = new TextureRegionDrawable(new TextureRegion(skin.get("up", Texture.class)));
        style.down = new TextureRegionDrawable(new TextureRegion(skin.get("down", Texture.class)));

        TextButton textButton = new TextButton(text, style);
        Size size = Utils.scaleScreenSize(textButton.getHeight(), textButton.getWidth(), width);
        textButton.getLabel().setFontScale(size.height / textButton.getHeight());
        textButton.setWidth(size.width);
        textButton.setHeight(size.height);
        return textButton;
    }

    public static HintTextField getStdTextField(float width, String hint) {
        Skin skin = new Skin();
        skin.add("font", Game.ASSETS.get(mAssets[0].file));
        skin.add("background", Game.ASSETS.get(mAssets[3].file));

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = skin.get("font", BitmapFont.class);
        style.background = new TextureRegionDrawable(new TextureRegion(skin.get("background", Texture.class)));
        style.fontColor = Color.GRAY;

        HintTextField textField = new HintTextField(hint, style);
        Size size = Utils.scaleScreenSize(textField.getHeight(), textField.getWidth(), width);
        //textField.getLabel().setFontScale(size.height / textField.getHeight());
        textField.setAlignment(Align.center);
        textField.setWidth(size.width);
        textField.setHeight(size.height);
        return textField;
    }

    public static Label getStdLabel(float width, float height, float fontScale, String text) {
        Skin skin = new Skin();
        skin.add("font", Game.ASSETS.get(mAssets[0].file));

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = skin.get("font", BitmapFont.class);
        style.fontColor = Color.WHITE;

        Label label = new Label(text, style);
        label.setWrap(true);
        label.setFontScale(fontScale);
        label.setWidth(width);
        label.setHeight(height);
        return label;
    }
}
