package com.sjjapps.partygame.screens.mainmenu.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.screens.mainmenu.actors.Pencil;

import java.util.Random;

/**
 * Created by Shane Jansen on 11/28/15.
 */
public class PencilStage extends Stage {
    private static final float PENCIL_SCALE = 2f/10f;
    private Random mRandom;

    public static void addAssets() {
        Pencil.addAssets();
    }

    public PencilStage() {
        super(new ScreenViewport(), Game.SPRITE_BATCH);
        mRandom = new Random();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (Actor a: getActors()) {
            if (a instanceof Pencil) {
                // Check if a pencil touches a wall
                if (a.getX() >= getCamera().viewportWidth) {
                    ((Pencil) a).bounce(true, false);
                    a.setPosition(a.getX() - 1, a.getY());
                }
                else if (a.getX() <= 0) {
                    ((Pencil) a).bounce(true, false);
                    a.setPosition(a.getX() + 1, a.getY());
                }
                if (a.getY() >= getCamera().viewportHeight) {
                    ((Pencil) a).bounce(false, true);
                    a.setPosition(a.getX(), a.getY() - 1);
                }
                else if (a.getY() <= 0) {
                    ((Pencil) a).bounce(false, true);
                    a.setPosition(a.getX(), a.getY() + 1);
                }
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Actor a: getActors()) {
            if (a instanceof Disposable) ((Disposable) a).dispose();
        }
    }

    public void addPencil(int posX, int posY, boolean initialPencil) {
        Vector3 vector = new Vector3(posX, posY, 0);
        getViewport().getCamera().unproject(vector);
        Pencil p;

        // Randomize the new pencil
        int velocity, radius;
        float scale;
        switch (mRandom.nextInt(4)) {
            case 0:
                velocity = 50;
                break;
            case 1:
                velocity = 100;
                break;
            case 2:
                velocity = 150;
                break;
            default:
                velocity = 200;
                break;
        }
        switch (mRandom.nextInt(4)) {
            case 0:
                scale = 0.25f;
                radius = 2;
                break;
            case 1:
                scale = 0.5f;
                radius = 3;
                break;
            case 2:
                scale = 0.75f;
                radius = 4;
                break;
            default:
                scale = 1.0f;
                radius = 5;
        }
        if (initialPencil) {
            p = new Pencil(getCamera().viewportWidth, PENCIL_SCALE, 200, 10);
            p.setScale(1.0f);
            p.setPosition(getCamera().viewportWidth * 0.5f, getCamera().viewportHeight * 0.5f);
        }
        else {
            p = new Pencil(getCamera().viewportWidth, PENCIL_SCALE, velocity, radius);
            p.setScale(scale);
            p.setPosition(vector.x, vector.y);
        }
        switch (mRandom.nextInt(4)) {
            case 0:
                p.setColor(Color.BLUE);
                break;
            case 1:
                p.setColor(Color.GREEN);
                break;
            case 2:
                p.setColor(Color.RED);
                break;
            case 3:
                p.setColor(Color.YELLOW);
                break;
        }
        p.setRotation(mRandom.nextInt(360));
        p.bounce(false, false);
        addActor(p);
    }
}
