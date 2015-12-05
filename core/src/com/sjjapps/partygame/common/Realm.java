package com.sjjapps.partygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.sjjapps.partygame.Game;

/**
 * Created by Shane Jansen on 12/4/15.
 */
public abstract class Realm implements Screen {
    private Array<Controller> mControllers;

    public Realm() {
        mControllers = new Array<Controller>();
    }

    /**
     * Adds a controller to this realm.
     * The order is important because it is used in the multiplexer.
     * Add the top level click listeners first.
     * @param controller
     */
    public void addController(Controller controller) {
        mControllers.add(controller);
    }

    /**
     * Removes a controller from the realm.
     * @param controller
     */
    public void removeController(Controller controller) {
        controller.dispose();
        mControllers.removeValue(controller, true);
    }

    /**
     * Returns and array of controllers managed by this Realm.
     * @return
     */
    public Array<Controller> getControllers() {
        return mControllers;
    }

    /**
     * Adds listeners to the Multiplexer.
     * Order is important!
     */
    public void addInputListeners() {
        for (Controller c : getControllers()) {
            Game.MULTIPLEXER_MANAGER.addInput(c);
        }
    }

    @Override
    public void show() {
        for (Controller c: mControllers) {
            c.show();
        }
    }

    /**
     * Renders each controller.
     * Iterating backwards because we want the reverse order
     * of the Multiplexer.
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (int i= mControllers.size-1; i>=0; i--){
            mControllers.get(i).render(delta);
        }
    }

    /**
     * Resizes each controller.
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        for (Controller c: mControllers) {
            c.resize(width, height);
        }
    }

    @Override
    public void pause() {
        for (Controller c: mControllers) {
            c.pause();
        }
    }

    @Override
    public void resume() {
        for (Controller c: mControllers) {
            c.resume();
        }
    }

    @Override
    public void hide() {
        for (Controller c: mControllers) {
            c.hide();
        }
    }

    /**
     * Disposes each controller.
     */
    @Override
    public void dispose() {
        for (Controller c: mControllers) {
            c.dispose();
        }
    }
}
