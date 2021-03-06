package com.sjjapps.partygame.managers;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.IdentityMap;

/**
 * Created by Shane Jansen on 11/26/15.
 */
public class MultiplexerManager {
    private final InputMultiplexer multiplexer;
    private final IdentityMap<Screen, GestureDetector> detectors;

    public MultiplexerManager(InputMultiplexer multiplexer) {
        this.multiplexer = multiplexer;
        this.detectors = new IdentityMap<Screen, GestureDetector>();
    }

    public void addInput(Object controller) {
        if (controller instanceof InputProcessor) {
            multiplexer.addProcessor((InputProcessor) controller);
        }
    }

    public void removeInput(Object controller) {
        if (controller instanceof InputProcessor) {
            multiplexer.removeProcessor((InputProcessor) controller);
        }
    }

    public InputMultiplexer getInputMultiplexer() {
        return multiplexer;
    }

    public void clear() {
        multiplexer.clear();
        detectors.clear();
    }
}
