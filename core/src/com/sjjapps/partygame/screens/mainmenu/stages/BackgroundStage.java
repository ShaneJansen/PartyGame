package com.sjjapps.partygame.screens.mainmenu.stages;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Controller;
import com.sjjapps.partygame.managers.FilePathManager;
import com.sjjapps.partygame.models.Asset;
import com.sjjapps.partygame.screens.mainmenu.MainMenu;

/**
 * Created by Shane Jansen on 11/27/15.
 */
public class BackgroundStage extends Stage {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.MAIN_MENU, Texture.class)
    };
    private BackgroundInterface mInterface;
    private Texture mBackground;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public BackgroundStage(BackgroundInterface backgroundInterface) {
        super(new FillViewport(Game.WORLD_WIDTH, Game.WORLD_HEIGHT), Game.SPRITE_BATCH);
        this.mInterface = backgroundInterface;
        mBackground = Game.ASSETS.get(mAssets[0].file);
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
        Game.SPRITE_BATCH.draw(mBackground,
                getViewport().getWorldWidth() * 0.5f - mBackground.getWidth() * 0.5f,
                getViewport().getWorldHeight() * 0.5f - mBackground.getHeight() * 0.5f);
        Game.SPRITE_BATCH.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        mBackground.dispose();
    }

    public interface BackgroundInterface {
        void backgroundClicked(int posX, int posY);
    }
}
