package com.sjjapps.partygame.screens.games.runaway.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Shane Jansen on 1/6/16.
 */
public class Wall extends Actor {
    private static final float SPEED_MULTIPLIER = 0.02f;
    private float mWorldHeight, mWorldWidth;
    private Body mBody;

    public enum Movement { LEFT, RIGHT, UP, DOWN }

    public Wall(float worldWidth, float worldHeight, Movement movement, World world) {
        mWorldHeight = worldHeight;
        mWorldWidth = worldWidth;

        setWidth(1);
        setHeight(worldHeight);

        // Box2d
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

        setMovement(movement);
    }

    public void setMovement(Movement movement) {
        switch (movement) {
            case LEFT:
                mBody.setLinearVelocity(-SPEED_MULTIPLIER, 0);
                mBody.setTransform(mWorldWidth, getHeight() * 0.5f, (float) Math.toRadians(0));
                break;
            case RIGHT:
                mBody.setLinearVelocity(SPEED_MULTIPLIER, 0);
                mBody.setTransform(0, getHeight() * 0.5f, (float) Math.toRadians(0));
                break;
            case UP:
                mBody.setLinearVelocity(0, SPEED_MULTIPLIER);
                mBody.setTransform(mWorldWidth * 0.5f, 0, (float) Math.toRadians(90));
                break;
            case DOWN:
                mBody.setLinearVelocity(0, -SPEED_MULTIPLIER);
                mBody.setTransform(mWorldWidth * 0.5f, mWorldHeight, (float) Math.toRadians(90));
                break;
        }
    }

    public Body getBody() {
        return mBody;
    }
}
