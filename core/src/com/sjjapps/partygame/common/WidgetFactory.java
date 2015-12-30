package com.sjjapps.partygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.actors.HintTextField;
import com.sjjapps.partygame.common.models.Asset;
import com.sjjapps.partygame.managers.FilePathManager;

/**
 * Created by Shane Jansen on 12/11/15.
 */
public class WidgetFactory {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.BUTTON, Texture.class),
            new Asset(FilePathManager.BUTTON_DOWN, Texture.class),
            new Asset(FilePathManager.EDIT_TEXT, Texture.class),
            new Asset(FilePathManager.TOUCHPAD_BACKGROUND, Texture.class),
            new Asset(FilePathManager.TOUCHPAD_KNOB, Texture.class)
    };
    private static WidgetFactory INSTANCE;
    public static BitmapFont mBfNormalLg, mBfNormalRg, mBfNormalSm;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public static void initialize(float viewportWidth) {
        INSTANCE = new WidgetFactory();

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(FilePathManager.FONT));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = (int) (viewportWidth * 30f / 500f);
        mBfNormalLg = fontGenerator.generateFont(parameter);
        parameter.size = (int) (viewportWidth * 15f / 500f);
        mBfNormalRg = fontGenerator.generateFont(parameter);
        parameter.size = (int) (viewportWidth * 8f / 500f);
        mBfNormalSm = fontGenerator.generateFont(parameter);
    }

    public static WidgetFactory getInstance() {
        if (INSTANCE != null) return INSTANCE;
        else throw new NullPointerException("WidgetFactory has not been initialized yet.");
    }

    public TextButton getStdButton(float width, float height, BitmapFont font, String text) {
        Skin skin = new Skin();
        skin.add("font", font);
        skin.add("up", Game.ASSETS.get(mAssets[0].file));
        skin.add("down", Game.ASSETS.get(mAssets[1].file));

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = skin.get("font", BitmapFont.class);
        style.up = new TextureRegionDrawable(new TextureRegion(skin.get("up", Texture.class)));
        style.down = new TextureRegionDrawable(new TextureRegion(skin.get("down", Texture.class)));

        TextButton textButton = new TextButton("", style);
        textButton.setWidth(width);
        textButton.setHeight(height);
        textButton.setText(text); // Set text only after default button is calculated
        return textButton;
    }

    public HintTextField getStdTextField(float width, float height, BitmapFont font, String hint) {
        Skin skin = new Skin();
        skin.add("font", font);
        skin.add("background", Game.ASSETS.get(mAssets[2].file));

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = skin.get("font", BitmapFont.class);
        style.background = new TextureRegionDrawable(new TextureRegion(skin.get("background", Texture.class)));
        style.fontColor = Color.GRAY;

        HintTextField textField = new HintTextField(hint, style);
        textField.setAlignment(Align.center);
        textField.setWidth(width);
        textField.setHeight(height);
        textField.setText(hint);
        return textField;
    }

    public Label getStdLabel(float width, float height, BitmapFont font, String text) {
        Skin skin = new Skin();
        skin.add("font", font);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = skin.get("font", BitmapFont.class);
        style.fontColor = Color.WHITE;

        Label label = new Label("", style);
        label.setWrap(true);
        label.setAlignment(Align.center);
        label.setWidth(width);
        label.setHeight(height);
        label.setText(text);
        return label;
    }

    public Touchpad getStdTouchpad(float width, float height) {
        Skin skin = new Skin();
        skin.add("background", Game.ASSETS.get(mAssets[3].file, Texture.class));
        skin.add("knob", Game.ASSETS.get(mAssets[4].file, Texture.class));

        Touchpad.TouchpadStyle style = new Touchpad.TouchpadStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(skin.get("background", Texture.class)));
        TextureRegionDrawable knob = new TextureRegionDrawable(new TextureRegion(skin.get("knob", Texture.class)));
        knob.setMinWidth(width * 0.5f);
        knob.setMinHeight(height * 0.5f);
        style.knob = knob;

        Touchpad touchpad = new Touchpad(10f, style);
        touchpad.setWidth(width);
        touchpad.setHeight(height);
        return touchpad;
    }
}
