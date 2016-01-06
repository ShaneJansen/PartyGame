package com.sjjapps.partygame.screens.games.runaway.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sjjapps.partygame.common.Utils;
import com.sjjapps.partygame.common.models.User;

/**
 * Created by Shane Jansen on 1/5/16.
 */
public class BoxPlayer extends Player {
    private float mWorldWidth;
    private Body mBody;

    public static void addAssets() {
        Player.addAssets();
    }

    public BoxPlayer(User user, BitmapFont bitmapFont, float worldWidth, BodyDef.BodyType bodyType, World world) {
        super(user, bitmapFont);
        mWorldWidth = worldWidth;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(getWidth() * 0.5f, getHeight() * 0.5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.8f;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.15f;

        bodyDef.position.set(0, 0);
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
        float uip = Utils.getUnitsInPixel(mWorldWidth);

        Matrix4 originalMatrix = batch.getProjectionMatrix().cpy();
        batch.setProjectionMatrix(originalMatrix.cpy().scale(uip, uip, 1));

        getBitmapFont().draw(batch, getUser().getName(),
                (mBody.getPosition().x / uip)
                        - ((getWidth() / uip) * .5f),
                (mBody.getPosition().y / uip)
                        + ((getHeight() / uip) * .5f)
                        + (getBitmapFont().getCapHeight()));

        batch.setProjectionMatrix(originalMatrix);
    }

    public Body getBody() {
        return mBody;
    }
}
