package com.sjjapps.partygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.sjjapps.partygame.Game;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public abstract class Realm implements Screen {
    private Array<Stage> mStages;

    public Realm() {
        mStages = new Array<Stage>();
    }

    /**
     * Adds a stage to this realm.
     * The order is important because it is used in the multiplexer.
     * Add the top level click listeners first.
     * @param stage
     */
    public void addStage(Stage stage) {
        mStages.add(stage);
    }

    /**
     * Removes a stage from the realm.
     * @param stage
     */
    public void removeStage(Stage stage) {
        stage.dispose();
        mStages.removeValue(stage, true);
    }

    /**
     * Returns and array of stages managed by this Realm.
     * @return
     */
    public Array<Stage> getStages() {
        return mStages;
    }

    /**
     * Adds listeners to the Multiplexer.
     * Order is important!
     */
    public void addInputListeners() {
        for (Stage s : getStages()) {
            Game.MULTIPLEXER_MANAGER.addInput(s);
        }
    }

    public void changeRealm(Realm nextRealm) {
        Game.MULTIPLEXER_MANAGER.clear();
        nextRealm.addInputListeners();
        Game.GAME.setScreen(nextRealm);
    }

    @Override
    public void show() {

    }

    /**
     * Calls act and draw on each stage.
     * Iterating backwards because we want the reverse order
     * of the Multiplexer.
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (int i=mStages.size-1; i>=0; i--) {
            mStages.get(i).getViewport().apply(true);
            Game.SPRITE_BATCH.setProjectionMatrix(mStages.get(i).getCamera().combined);
            if (!Game.PAUSED) mStages.get(i).act(delta);
            mStages.get(i).draw();
        }
    }

    /**
     * Resizes each stage.
     * Passing true when updating the viewport changes the camera
     * position so it is centered on the stage, making 0,0 the bottom
     * left corner. This is useful for UIs, where the camera position
     * is not usually changed. When managing the camera position
     * yourself, pass false or omit the boolean. If the stage position
     * is not set, by default 0,0 will be in the center of the screen.
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        for (Stage s: mStages) {
            s.getViewport().update(width, height, true);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
