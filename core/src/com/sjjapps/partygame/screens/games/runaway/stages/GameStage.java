package com.sjjapps.partygame.screens.games.runaway.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.WidgetFactory;
import com.sjjapps.partygame.common.actors.BoxPlayer;
import com.sjjapps.partygame.common.actors.Player;
import com.sjjapps.partygame.common.models.Point;
import com.sjjapps.partygame.common.models.User;
import com.sjjapps.partygame.network.NetPlayer;
import com.sjjapps.partygame.screens.games.runaway.actors.Enemy;
import com.sjjapps.partygame.screens.games.runaway.actors.Wall;
import com.sjjapps.partygame.screens.games.runaway.network.NetEnemy;

import java.util.Random;

/**
 * Created by Shane Jansen on 12/21/15.
 */
public class GameStage extends Stage {
    private static final float WORLD_WIDTH = 10;
    private static final float WORLD_HEIGHT = 10;
    private static final float TOUCHPAD_SPEED = 5;
    private static final float ENEMY_SPEED = 1.5f;
    private GameStageInterface mInterface;
    private Touchpad mTouchpad;
    private Random mRandom;
    private boolean mAlreadyStill; // True if the player is already still
    private NetPlayer mNetPlayer;
    private NetEnemy mNetEnemy;
    private BoxPlayer mPlayer;
    private Array<BoxPlayer> mPlayers;
    private Array<BoxPlayer> mAllPlayers;
    private Enemy mEnemy;

    private World mWorld;
    private Box2DDebugRenderer mDebugRenderer;

    public static void addAssets() {
        Player.addAssets();
        Enemy.addAssets();
        WidgetFactory.addAssets();
    }

    public interface GameStageInterface {
        void playerMoved(NetPlayer netPlayer);
        void enemyMoved(NetEnemy netEnemy);
    }

    public GameStage(GameStageInterface gameStageInterface, Touchpad touchpad) {
        super(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT), Game.SPRITE_BATCH);
        mInterface = gameStageInterface;
        mTouchpad = touchpad;
        mRandom = new Random();
        mAlreadyStill = false;

        initBox2d();
        createEnemy();
        createPlayers();
        //createWalls();

        // Initial network update
        updateNetworkPlayer();
    }

    /**
     * Initialize Box2d.
     */
    public void initBox2d() {
        mWorld = new World(new Vector2(0, 0), true);
        mDebugRenderer = new Box2DDebugRenderer();
        mWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();
                if (bodyA == mEnemy.getBody() || bodyB == mEnemy.getBody()) {
                    for (BoxPlayer p : mAllPlayers) {
                        if (bodyA == p.getBody() || bodyB == p.getBody()) {
                            Game.log("ENEMY COLLISION: " + p.getUser().getName());
                        }
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }

    /**
     * Create the game's enemy.
     */
    public void createEnemy() {
        mNetEnemy = new NetEnemy();
        mEnemy = new Enemy(mWorld);
        mEnemy.getBody().setTransform(WORLD_WIDTH * 0.5f, WORLD_HEIGHT * 0.5f, mEnemy.getBody().getAngle());
        mEnemy.getBody().setFixedRotation(true);
        addActor(mEnemy);
    }

    /**
     * Create the game's players.
     */
    private void createPlayers() {
        User thisUser = Game.NETWORK_HELPER.findThisUser();
        mNetPlayer = new NetPlayer(thisUser.getId());
        BitmapFont bitmapFont = WidgetFactory.mBfNormalLg;
        mPlayers = new Array<BoxPlayer>();
        mAllPlayers = new Array<BoxPlayer>();
        for (User u: Game.NETWORK_HELPER.users.getUsers()) {
            if (u.getId() != thisUser.getId()) {
                BoxPlayer player = new BoxPlayer(u, bitmapFont, WORLD_WIDTH, BodyDef.BodyType.KinematicBody, mWorld);
                player.getBody().setTransform(-WORLD_WIDTH, -WORLD_HEIGHT, player.getBody().getAngle()); // Begin off screen
                player.getBody().setFixedRotation(true);
                addActor(player);
                mPlayers.add(player);
                mAllPlayers.add(player);
            }
            else {
                mPlayer = new BoxPlayer(thisUser, bitmapFont, WORLD_WIDTH, BodyDef.BodyType.DynamicBody, mWorld);
                addActor(mPlayer);
                Point initialPos = randomBoundedPoint((int) WORLD_WIDTH, (int) WORLD_HEIGHT);
                mPlayer.getBody().setTransform(initialPos.x, initialPos.y, mPlayer.getBody().getAngle());
                mPlayer.getBody().setFixedRotation(true);
                mAllPlayers.add(mPlayer);
            }
        }
    }

    /**
     * Creates four moving walls.
     */
    private void createWalls() {
        Wall leftWall = new Wall(WORLD_WIDTH, WORLD_HEIGHT, Wall.Movement.RIGHT, mWorld);
        addActor(leftWall);
        Wall rightWall = new Wall(WORLD_WIDTH, WORLD_HEIGHT, Wall.Movement.LEFT, mWorld);
        addActor(rightWall);
        Wall upWall = new Wall(WORLD_WIDTH, WORLD_HEIGHT, Wall.Movement.UP, mWorld);
        addActor(upWall);
        Wall downWall = new Wall(WORLD_WIDTH, WORLD_HEIGHT, Wall.Movement.DOWN, mWorld);
        addActor(downWall);
    }

    /**
     * Loop through each Player and check to see if the User
     * id is the same as the NetUser id. If there is a match,
     * update the Player's position based on netUser.
     * @param netPlayer
     */
    public void playerReceived(final NetPlayer netPlayer) {
        for (final BoxPlayer p: mPlayers) {
            if (p.getUser().getId() == netPlayer.getUserId()) {
                // Box2d transformations must take place before or after Box2d update
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        p.getBody().setTransform(netPlayer.getPosX(), netPlayer.getPosY(), p.getBody().getAngle());
                    }
                });
            }
        }
    }

    /**
     * Called when the new position for the game's enemy
     * has been received.
     * @param netEnemy
     */
    public void enemyReceived(final NetEnemy netEnemy) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                mEnemy.getBody().setTransform(netEnemy.getPosX(), netEnemy.getPosY(), mEnemy.getBody().getAngle());
            }
        });
    }

    /**
     * Called when this player has moved and the networking
     * classes need to be updated.
     */
    private void updateNetworkPlayer() {
        mNetPlayer.setPosX(mPlayer.getBody().getPosition().x);
        mNetPlayer.setPosY(mPlayer.getBody().getPosition().y);
        mInterface.playerMoved(mNetPlayer);
    }

    /**
     * Called when the enemy has moved and the networking
     * classes need to be updated.
     */
    private void updateNetworkEnemy() {
        mNetEnemy.setPosX(mEnemy.getBody().getPosition().x);
        mNetEnemy.setPosY(mEnemy.getBody().getPosition().y);
        mInterface.enemyMoved(mNetEnemy);
    }

    /**
     * Creates a random bounded point.
     * @param maxX
     * @param maxY
     * @return
     */
    public Point randomBoundedPoint(int maxX, int maxY) {
        return new Point(mRandom.nextInt(maxX), mRandom.nextInt(maxY));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Set the velocity of the player
        //mBody.applyForceToCenter(mTouchpad.getKnobPercentX(), mTouchpad.getKnobPercentY(), true);
        float padX = mTouchpad.getKnobPercentX();
        float padY = mTouchpad.getKnobPercentY();
        if (padX != 0 || padY != 0) {
            mAlreadyStill = false;
            mPlayer.getBody().setLinearVelocity(padX * TOUCHPAD_SPEED,
                    padY * TOUCHPAD_SPEED);
            updateNetworkPlayer();
        }
        if (padX == 0 && padY == 0 && !mAlreadyStill) {
            mAlreadyStill = true;
            mPlayer.getBody().setLinearVelocity(new Vector2(0, 0));
            updateNetworkPlayer();
        }

        // Server - Move the enemy towards the closest player
        if (Game.NETWORK_HELPER.isServer()) {
            float closest = -1;
            BoxPlayer closestPlayer = mPlayer;
            for (BoxPlayer p: mAllPlayers) {
                float distance = mEnemy.getBody().getPosition().dst(p.getBody().getPosition());
                if (closest == -1 || distance < closest) {
                    closest = distance;
                    closestPlayer = p;
                }
            }
            Vector2 velocity = new Vector2();
            velocity.x = closestPlayer.getBody().getPosition().x - mEnemy.getBody().getPosition().x;
            velocity.y = closestPlayer.getBody().getPosition().y - mEnemy.getBody().getPosition().y;
            velocity.nor();
            mEnemy.getBody().setLinearVelocity(
                    velocity.x * ENEMY_SPEED,
                    velocity.y * ENEMY_SPEED);
            updateNetworkEnemy();
        }


        // Update Box2d
        mWorld.step(1/60f, 6, 2);
        mDebugRenderer.render(mWorld, getCamera().combined);
    }
}
