package com.sjjapps.partygame.screens.games.runaway.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sjjapps.partygame.Game;
import com.sjjapps.partygame.common.WidgetFactory;
import com.sjjapps.partygame.common.models.Point;
import com.sjjapps.partygame.common.models.User;
import com.sjjapps.partygame.common.actors.BoxPlayer;
import com.sjjapps.partygame.common.actors.Player;
import com.sjjapps.partygame.network.MovablePlayer;
import com.sjjapps.partygame.screens.games.runaway.actors.Enemy;
import com.sjjapps.partygame.screens.games.runaway.actors.Wall;

import java.util.Random;

/**
 * Created by Shane Jansen on 12/21/15.
 */
public class GameStage extends Stage {
    private static final float WORLD_WIDTH = 10;
    private static final float WORLD_HEIGHT = 10;
    private static final float TOUCHPAD_SPEED = 5;
    private GameStageInterface mInterface;
    private Touchpad mTouchpad;
    private Random mRandom;
    private boolean mAlreadyStill; // True if the player is already still
    private MovablePlayer mGameUser; // Networking class
    private BoxPlayer mPlayer;
    private Array<BoxPlayer> mPlayers;
    private Enemy mEnemy;

    private World mWorld;
    private Box2DDebugRenderer mDebugRenderer;

    public static void addAssets() {
        Player.addAssets();
        Enemy.addAssets();
        WidgetFactory.addAssets();
    }

    public interface GameStageInterface {
        void playerMoved(MovablePlayer gameUser);
    }

    public GameStage(GameStageInterface gameStageInterface, Touchpad touchpad) {
        super(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT), Game.SPRITE_BATCH);
        mInterface = gameStageInterface;
        mTouchpad = touchpad;
        mRandom = new Random();
        mAlreadyStill = false;

        // Init Box2d
        mWorld = new World(new Vector2(0, 0), true);
        mDebugRenderer = new Box2DDebugRenderer();


        createEnemy();
        createPlayers();
        createWalls();

        // Initial network update
        updateNetworkClass();
    }

    public void createEnemy() {
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
        mGameUser = new MovablePlayer(thisUser.getId());
        BitmapFont bitmapFont = WidgetFactory.mBfNormalLg;
        mPlayers = new Array<BoxPlayer>();
        for (User u: Game.NETWORK_HELPER.users.getUsers()) {
            if (u.getId() != thisUser.getId()) {
                BoxPlayer player = new BoxPlayer(u, bitmapFont, WORLD_WIDTH, BodyDef.BodyType.KinematicBody, mWorld);
                player.getBody().setTransform(-WORLD_WIDTH, -WORLD_HEIGHT, player.getBody().getAngle()); // Begin off screen
                player.getBody().setFixedRotation(true);
                addActor(player);
                mPlayers.add(player);
            }
            else {
                mPlayer = new BoxPlayer(thisUser, bitmapFont, WORLD_WIDTH, BodyDef.BodyType.DynamicBody, mWorld);
                addActor(mPlayer);
                Point initialPos = randomBoundedPoint((int) WORLD_WIDTH, (int) WORLD_HEIGHT);
                mPlayer.getBody().setTransform(initialPos.x, initialPos.y, mPlayer.getBody().getAngle());
                mPlayer.getBody().setFixedRotation(true);
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
     * id is the same as the GameUser id. If there is a match,
     * update the Player's position based on gameUser.
     * @param gameUser
     */
    public void updatePlayer(final MovablePlayer gameUser) {
        for (final BoxPlayer p: mPlayers) {
            if (p.getUser().getId() == gameUser.getUserId()) {
                // Box2d transformations must take place before or after Box2d update
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        p.getBody().setTransform(gameUser.getPosX(), gameUser.getPosY(), p.getBody().getAngle());
                    }
                });
            }
        }
    }

    /**
     * Called when this player has moved and the networking
     * classes need to be updated.
     */
    private void updateNetworkClass() {
        mGameUser.setPosX(mPlayer.getBody().getPosition().x);
        mGameUser.setPosY(mPlayer.getBody().getPosition().y);
        mInterface.playerMoved(mGameUser);
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
            updateNetworkClass();
        }
        if (padX == 0 && padY == 0 && !mAlreadyStill) {
            mAlreadyStill = true;
            mPlayer.getBody().setLinearVelocity(new Vector2(0, 0));
            updateNetworkClass();
        }

        // Update Box2d
        mWorld.step(1/60f, 6, 2);
        mDebugRenderer.render(mWorld, getCamera().combined);
    }
}
