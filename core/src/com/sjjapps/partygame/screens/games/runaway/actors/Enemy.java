package com.sjjapps.partygame.screens.games.runaway.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.models.Asset;
import com.sjjapps.partygame.managers.FilePathManager;

/**
 * Created by Shane Jansen on 1/7/16.
 */
public class Enemy extends Actor {
    private static final Asset[] mAssets = new Asset[] {
            new Asset(FilePathManager.PENCIL, Texture.class)
    };
    private static final float WIDTH = 1.5f;
    private static final float HEIGHT = 1.5f;
    private Texture mTxtBackground;

    private Body mBody;

    public static void addAssets() {
        for (Asset a: mAssets) {
            Game.ASSETS.load(a.file, a.type);
        }
    }

    public Enemy(World world) {
        mTxtBackground = Game.ASSETS.get(mAssets[0].file);

        setWidth(WIDTH);
        setHeight(HEIGHT);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getWidth() * 0.5f, getHeight() * 0.5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 1.0f;
        fixtureDef.restitution = 0;

        bodyDef.position.set(0, 0);
        mBody = world.createBody(bodyDef);
        mBody.createFixture(fixtureDef);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(new TextureRegion(mTxtBackground),
                mBody.getPosition().x - (getWidth() * .5f),
                mBody.getPosition().y - (getHeight() * .5f),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public Body getBody() {
        return mBody;
    }
}
