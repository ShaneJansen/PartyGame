package com.sjjapps.partygame.screens.mainmenu.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.screens.mainmenu.actors.Pencil;

import java.util.Random;

/**
 * Created by Shane Jansen on 11/28/15.
 */
public class PencilStage extends Stage {
    private Random mRandom;

    public static void addAssets() {
        Pencil.addAssets();
    }

    public PencilStage() {
        super(new ScalingViewport(Scaling.fill, Game.WORLD_WIDTH, Game.WORLD_HEIGHT), Game.SPRITE_BATCH);
        /*super(new ExtendViewport(Game.WORLD_WIDTH, Game.WORLD_HEIGHT,
                        new OrthographicCamera(Game.WORLD_WIDTH, Game.WORLD_HEIGHT)),
                Game.SPRITE_BATCH);*/
        mRandom = new Random();
        //getViewport().setScreenPosition((int) (Game.WORLD_WIDTH / 2), (int) (Game.WORLD_HEIGHT / 2));
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (Actor a: getActors()) {
            if (a instanceof Pencil) {
                // Check if a pencil touches a wall
                if (a.getX() >= Gdx.graphics.getWidth() * 0.5f ||
                        a.getX() <= -Gdx.graphics.getWidth() * 0.5f) {
                    ((Pencil) a).bounce(true, false);
                }
                if (a.getY() >= Gdx.graphics.getHeight() * 0.5f ||
                        a.getY() <= -Gdx.graphics.getHeight() * 0.5f) {
                    ((Pencil) a).bounce(false, true);
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
            p = new Pencil(200, 10);
            p.setScale(2.0f);
            p.setPosition(0, 0);
        }
        else {
            p = new Pencil(velocity, radius);
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
        addActor(p);
    }
}
