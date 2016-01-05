package com.sjjapps.partygame.screens.games.runaway.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.WidgetFactory;
import com.sjjapps.partygame.common.models.Point;
import com.sjjapps.partygame.common.models.User;
import com.sjjapps.partygame.screens.games.runaway.actors.BoxPlayer;
import com.sjjapps.partygame.screens.games.runaway.actors.Player;
import com.sjjapps.partygame.screens.games.runaway.models.GameUser;

import java.util.Random;

/**
 * Created by Shane Jansen on 12/21/15.
 */
public class GameStage extends Stage {
    private static final float WORLD_WIDTH = 10;
    private static final float WORLD_HEIGHT = 10;
    private static final float SPEED_MULTIPLIER = 5;
    private GameStageInterface mInterface;
    private Touchpad mTouchpad;
    private Random mRandom;
    private GameUser mGameUser;
    private BoxPlayer mPlayer;
    private Array<Player> mPlayers;

    // Box2d
    private World mWorld;
    private Box2DDebugRenderer mDebugRenderer;
    //private Body mBody;

    public static void addAssets() {
        Player.addAssets();
        WidgetFactory.addAssets();
    }

    public interface GameStageInterface {
        void playerMoved(GameUser gameUser);
    }

    public GameStage(GameStageInterface gameStageInterface, Touchpad touchpad) {
        super(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT), Game.SPRITE_BATCH);
        mInterface = gameStageInterface;
        mTouchpad = touchpad;
        mRandom = new Random();
        User thisUser = Game.NETWORK_HELPER.findThisUser();
        mGameUser = new GameUser(thisUser.getId());

        // TODO: Box2d
        mWorld = new World(new Vector2(0, 0), true);
        mDebugRenderer = new Box2DDebugRenderer();
        /*BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(1, 1);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.8f;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.15f;
        bodyDef.position.set(2, 2);
        mBody = mWorld.createBody(bodyDef);
        mBody.createFixture(fixtureDef);*/

        // Create players
        BitmapFont bitmapFont = WidgetFactory.mBfNormalLg;
        mPlayer = new BoxPlayer(thisUser, bitmapFont, mWorld);
        addActor(mPlayer);
        Point initialPos = randomBoundedPoint((int) WORLD_WIDTH, (int) WORLD_HEIGHT);
        mPlayer.setPosition(initialPos.x, initialPos.y);
        mPlayers = new Array<Player>();
        for (User u: Game.NETWORK_HELPER.users.getUsers()) {
            Player player = new Player(u, bitmapFont);
            player.setPosition(-WORLD_WIDTH, -WORLD_HEIGHT); // Start off screen
            addActor(player);
            mPlayers.add(player);
        }

        // Initial update
        mGameUser.setPosY(mPlayer.getY());
        mGameUser.setPosX(mPlayer.getX());
        mInterface.playerMoved(mGameUser);
    }

    /**
     * Loop through each Player and check to see if the User
     * id is the same as the GameUser id. If there is a match,
     * update the Player's position based on gameUser.
     * @param gameUser
     */
    public void updatePlayer(GameUser gameUser) {
        for (Player p: mPlayers) {
            if (p.getUser().getId() == gameUser.getUserId()) {
                p.setPosition(gameUser.getPosX(), gameUser.getPosY());
            }
        }
    }

    public Point randomBoundedPoint(int maxX, int maxY) {
        return new Point(mRandom.nextInt(maxX), mRandom.nextInt(maxY));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Set the velocity of the body
        //mBody.applyForceToCenter(mTouchpad.getKnobPercentX(), mTouchpad.getKnobPercentY(), true);
        mPlayer.getBody().setLinearVelocity(mTouchpad.getKnobPercentX() * SPEED_MULTIPLIER,
                mTouchpad.getKnobPercentY() * SPEED_MULTIPLIER);
        if (mTouchpad.getKnobPercentX() == 0 && mTouchpad.getKnobPercentY() == 0) {
            mPlayer.getBody().setLinearVelocity(new Vector2(0, 0));
        }

        // Update the network class
        mGameUser.setPosX(mPlayer.getX());
        mGameUser.setPosY(mPlayer.getY());
        mInterface.playerMoved(mGameUser);

        // Update Box2d
        mWorld.step( 1/ 60f, 6, 2);
        mDebugRenderer.render(mWorld, getCamera().combined);
    }

    @Override
    public void draw() {
        // Test circles
        /*Game.SHAPE_RENDERER.setProjectionMatrix(getViewport().getCamera().combined);
        Game.SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
        Game.SHAPE_RENDERER.setColor(Color.YELLOW);
        Game.SHAPE_RENDERER.circle(getViewport().getWorldWidth(), getViewport().getWorldHeight(), 1);
        Game.SHAPE_RENDERER.circle(0, 0, 30);
        Game.SHAPE_RENDERER.end();*/

        super.draw();
    }
}
