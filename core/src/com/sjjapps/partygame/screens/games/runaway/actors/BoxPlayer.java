package com.sjjapps.partygame.screens.games.runaway.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sjjapps.partygame.common.models.User;

/**
 * Created by Shane Jansen on 1/5/16.
 */
public class BoxPlayer extends Player {
    private Body mBody;

    public static void addAssets() {
        Player.addAssets();
    }

    public BoxPlayer(User user, BitmapFont bitmapFont, World world) {
        super(user, bitmapFont);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getWidth() * 0.5f, getHeight() * 0.5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.8f;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.15f;

        bodyDef.position.set(5, 5);
        mBody = world.createBody(bodyDef);
        mBody.createFixture(fixtureDef);
    }

    @Override
    public void drawPlayer(Batch batch) {
        batch.draw(new TextureRegion(getTxtBackground()),
                mBody.getPosition().x - (getWidth() * .5f),
                mBody.getPosition().y - (getHeight() * .5f),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void drawPlayerName(Batch batch) {
        Matrix4 originalMatrix = batch.getProjectionMatrix().cpy();
        batch.setProjectionMatrix(originalMatrix.cpy().scale(getUnitsInPixel(), getUnitsInPixel(), 1));

        float uip = getUnitsInPixel();
        getBitmapFont().draw(batch, getUser().getName(),
                (mBody.getPosition().x / uip)
                        - ((getWidth() / uip) * .5f),
                (mBody.getPosition().y / uip)
                        + ((getHeight() / uip) * .5f)
                        + (getBitmapFont().getCapHeight()));

        batch.setProjectionMatrix(originalMatrix);
    }

    private float getUnitsInPixel() {
        return 10.0f / (float) Gdx.graphics.getWidth();
    }

    public Body getBody() {
        return mBody;
    }
}
