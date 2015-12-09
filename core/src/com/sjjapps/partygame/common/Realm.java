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

        for (int i=mStages.size-1; i>=0; i--){
            mStages.get(i).getViewport().apply();
            if (!Game.PAUSED) mStages.get(i).act(delta);
            mStages.get(i).draw();
        }
    }

    /**
     * Resizes each stage.
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        for (Stage s: mStages) {
            s.getViewport().update(width, height);
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

    /**
     * Disposes each controller.
     */
    @Override
    public void dispose() {
        for (Stage s: mStages) {
            s.dispose();
        }
    }
}
