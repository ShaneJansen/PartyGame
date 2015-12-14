package com.sjjapps.partygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.managers.FilePathManager;
import com.sjjapps.partygame.models.Asset;
import com.sjjapps.partygame.models.Size;

/**
 * Created by Shane Jansen on 12/11/15.
 */
public abstract class Dialog extends Stage {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.DIALOG_BACKGROUND, Texture.class),
            new Asset(FilePathManager.BUTTON_X, Texture.class)
    };
    private DialogInterface mDialogInterface;
    private FillViewport mVpBackground;
    private Texture mTxtBackground;
    private Button mBtnX;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public interface DialogInterface {
        void btnExitPressed();
    }

    public Dialog(DialogInterface dialogInterface, float widthScale) {
        super(new ScreenViewport(), Game.SPRITE_BATCH);
        this.mDialogInterface = dialogInterface;
        mVpBackground = new FillViewport(Game.WORLD_WIDTH, Game.WORLD_HEIGHT);
        mTxtBackground = Game.ASSETS.get(mAssets[0].file);
        Texture txtButtonX = Game.ASSETS.get(mAssets[1].file);

        // Set dialog size
        Size mainSize = Utils.scaleScreenSize(Gdx.graphics.getHeight(), Gdx.graphics.getWidth(),
                Gdx.graphics.getWidth() * widthScale);
        getViewport().setScreenSize((int) mainSize.width, (int) mainSize.height);
        getViewport().setScreenPosition((int) (Gdx.graphics.getWidth() * 0.5f - getViewport().getScreenWidth() * 0.5f),
                (int) (Gdx.graphics.getHeight() * 0.5f - getViewport().getScreenHeight() * 0.5f));

        // Set background size
        mVpBackground.setScreenSize((int) mainSize.width, (int) mainSize.height);
        mVpBackground.setScreenPosition((int) (Gdx.graphics.getWidth() * 0.5f - mVpBackground.getScreenWidth() * 0.5f),
                (int) (Gdx.graphics.getHeight() * 0.5f - mVpBackground.getScreenHeight() * 0.5f));

        // Set exit button size and style
        Size szExitButton = Utils.scaleScreenSize(txtButtonX.getHeight(), txtButtonX.getWidth(),
                Gdx.graphics.getWidth() * 1f / 10f);
        Skin skin = new Skin();
        skin.add("up", txtButtonX);
        skin.add("down", txtButtonX);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(skin.get("up", Texture.class)));
        style.down = new TextureRegionDrawable(new TextureRegion(skin.get("down", Texture.class)));
        mBtnX = new Button(style);
        mBtnX.setSize(szExitButton.width, szExitButton.height);
        mBtnX.setPosition(0, getViewport().getWorldHeight() - szExitButton.height);
        addActor(mBtnX);

        // Listeners
        mBtnX.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mDialogInterface.btnExitPressed();
            }
        });
    }

    @Override
    public void draw() {
        // Background
        mVpBackground.apply(true);
        getBatch().setProjectionMatrix(mVpBackground.getCamera().combined);
        getBatch().begin();
        getBatch().draw(mTxtBackground,
                mVpBackground.getWorldWidth() * 0.5f - mTxtBackground.getWidth() * 0.5f,
                mVpBackground.getWorldHeight() * 0.5f - mTxtBackground.getHeight() * 0.5f);
        getBatch().end();

        // Test circles
        /*Game.SHAPE_RENDERER.setProjectionMatrix(mVpBackground.getCamera().combined);
        Game.SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
        Game.SHAPE_RENDERER.setColor(Color.YELLOW);
        Game.SHAPE_RENDERER.circle(mVpBackground.getWorldWidth(), mVpBackground.getWorldHeight(), 30);
        Game.SHAPE_RENDERER.circle(0, 0, 30);
        Game.SHAPE_RENDERER.end();*/

        super.draw();
    }

    /*@Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 vector = new Vector3(screenX, screenY, 0);
        getViewport().getCamera().unproject(vector, getViewport().getScreenX(), getViewport().getScreenY(),
                getViewport().getScreenWidth(), getViewport().getScreenHeight());
        Rectangle textureBounds = new Rectangle(mSprButtonX.getX(), mSprButtonX.getY(),
                mSprButtonX.getWidth(), mSprButtonX.getHeight());
        if (textureBounds.contains(vector.x, vector.y)) {
            mDialogInterface.btnExitPressed();
            return true;
        }
        return false;
        super.touchUp(screenX, screenY, pointer, button);
    }*/

    @Override
    public void dispose() {
        super.dispose();
        mTxtBackground.dispose();
        mBtnX.getSkin().dispose();
    }
}
