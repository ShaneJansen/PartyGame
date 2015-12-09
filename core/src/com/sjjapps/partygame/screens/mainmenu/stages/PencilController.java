package com.sjjapps.partygame.screens.mainmenu.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.Controller;
import com.sjjapps.partygame.models.Point;
import com.sjjapps.partygame.screens.mainmenu.actors.Pencil;

import java.util.Random;

/**
 * Created by Shane Jansen on 11/28/15.
 */
public class PencilController implements Controller {
    private Viewport mViewport;
    private Random mRandom;
    private Array<Pencil> mPencils;

    public static void addAssets() {
        Pencil.addAssets();
    }

    public PencilController() {
        mViewport = new ScreenViewport();
        mRandom = new Random();
        mPencils = new Array<Pencil>();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        mViewport.apply();

        // Draw Pencil Lines
        Game.SHAPE_RENDERER.setProjectionMatrix(mViewport.getCamera().combined);
        Game.SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
        for (Pencil p: mPencils) {
            Game.SHAPE_RENDERER.setColor(p.getColor());
            for (int i=0; i<p.getPoints().size; i++) {
                if (i == p.getPoints().size - 1) {
                    Game.SHAPE_RENDERER.rectLine(p.getPoints().get(i).x, p.getPoints().get(i).y,
                            p.getSprite().getX(), p.getSprite().getY(), p.getRadius());
                }
                else {
                    Game.SHAPE_RENDERER.rectLine(p.getPoints().get(i).x, p.getPoints().get(i).y,
                            p.getPoints().get(i + 1).x, p.getPoints().get(i + 1).y, p.getRadius());
                }
            }
        }
        Game.SHAPE_RENDERER.end();

        // Draw Pencils
        Game.SPRITE_BATCH.setProjectionMatrix(mViewport.getCamera().combined);
        Game.SPRITE_BATCH.begin();
        for (Pencil p: mPencils) {
            Sprite s = p.getSprite();
            if (s.getX() >= Gdx.graphics.getWidth() * 0.5f ||
                    s.getX() <= -Gdx.graphics.getWidth() * 0.5f) {
                p.setVelocityX(p.getVelocityX() * -1);
                p.getPoints().add(new Point(s.getX(), s.getY()));
            }
            if (s.getY() >= Gdx.graphics.getHeight() * 0.5f ||
                    s.getY() <= -Gdx.graphics.getHeight() * 0.5f) {
                p.setVelocityY(p.getVelocityY() * -1);
                p.getPoints().add(new Point(s.getX(), s.getY()));
            }
            float newX = s.getX() + (delta * p.getVelocityX());
            float newY = s.getY() + (delta * p.getVelocityY());
            if (!Game.PAUSED) {
                s.setPosition(newX, newY);
            }
            s.draw(Game.SPRITE_BATCH);
        }
        Game.SPRITE_BATCH.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height);
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
        for (Pencil p: mPencils) {
            p.dispose();
        }
    }

    public void addPencil(int posX, int posY, boolean initialPencil) {
        Vector3 vector = new Vector3(posX, posY, 0);
        mViewport.getCamera().unproject(vector);

        Pencil p = new Pencil();
        p.getSprite().setPosition(vector.x, vector.y);
        p.getPoints().add(new Point(vector.x, vector.y)); // Add initial point
        p.getSprite().setScale(Game.PPU);

        // Randomize the new pencil
        p.getSprite().setRotation(mRandom.nextInt(360));
        p.getSprite().setOrigin(0, 0);
        switch (mRandom.nextInt(4)) {
            case 0:
                p.setVelocityX((int)(Game.PPU * 50));
                p.setVelocityY((int)(Game.PPU * 50));
                break;
            case 1:
                p.setVelocityX((int)(Game.PPU * 100));
                p.setVelocityY((int)(Game.PPU * 100));
                break;
            case 2:
                p.setVelocityX((int)(Game.PPU * 150));
                p.setVelocityY((int)(Game.PPU * 150));
                break;
            case 3:
                p.setVelocityX((int)(Game.PPU * 200));
                p.setVelocityY((int)(Game.PPU * 200));
                break;
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
        switch (mRandom.nextInt(4)) {
            case 0:
                p.setVelocityX(p.getVelocityX() * -1);
                break;
            case 1:
                p.setVelocityY(p.getVelocityY() * -1);
                break;
            case 2:
                p.setVelocityX(p.getVelocityX() * -1);
                p.setVelocityY(p.getVelocityY() * -1);
                break;
        }
        switch (mRandom.nextInt(4)) {
            case 0:
                p.getSprite().scale(0.25f);
                p.setRadius(2);
                break;
            case 1:
                p.getSprite().scale(0.5f);
                p.setRadius(3);
                break;
            case 2:
                p.getSprite().scale(0.75f);
                p.setRadius(4);
                break;
            default:
                p.setRadius(5);
        }
        if (initialPencil) {
            p.setVelocityX((int)(Game.PPU * 200));
            p.setVelocityY((int)(Game.PPU * 200));
            p.getSprite().scale(2);
            p.setRadius(10);
        }
        mPencils.add(p);
    }
}