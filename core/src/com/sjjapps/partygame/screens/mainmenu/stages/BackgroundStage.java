package com.sjjapps.partygame.screens.mainmenu.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.models.Asset;
import com.sjjapps.partygame.managers.FilePathManager;

/**
 * Created by Shane Jansen on 11/27/15.
 */
public class BackgroundStage extends Stage {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.MAIN_MENU, Texture.class)
    };
    private BackgroundInterface mInterface;
    private Texture mTxtBackground;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public interface BackgroundInterface {
        void backgroundClicked(int posX, int posY);
    }

    public BackgroundStage(BackgroundInterface backgroundInterface) {
        super(new FillViewport(Game.WORLD_WIDTH, Game.WORLD_HEIGHT,
                        new OrthographicCamera(Game.WORLD_WIDTH, Game.WORLD_HEIGHT)),
                Game.SPRITE_BATCH);
        this.mInterface = backgroundInterface;
        mTxtBackground = Game.ASSETS.get(mAssets[0].file);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mInterface.backgroundClicked(screenX, screenY);
        return true;
    }

    @Override
    public void draw() {
        super.draw();
        Game.SPRITE_BATCH.setProjectionMatrix(getViewport().getCamera().combined);
        Game.SPRITE_BATCH.begin();
        Game.SPRITE_BATCH.draw(mTxtBackground,
                getViewport().getWorldWidth() * 0.5f - mTxtBackground.getWidth() * 0.5f,
                getViewport().getWorldHeight() * 0.5f - mTxtBackground.getHeight() * 0.5f);
        Game.SPRITE_BATCH.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        mTxtBackground.dispose();
    }
}
