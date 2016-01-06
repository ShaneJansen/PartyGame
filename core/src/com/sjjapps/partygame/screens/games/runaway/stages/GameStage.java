package com.sjjapps.partygame.screens.games.runaway.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
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
    private Array<BoxPlayer> mPlayers;

    private World mWorld;
    private Box2DDebugRenderer mDebugRenderer;

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

        // Init Box2d
        mWorld = new World(new Vector2(0, 0), true);
        mDebugRenderer = new Box2DDebugRenderer();

        // Create players
        User thisUser = Game.NETWORK_HELPER.findThisUser();
        mGameUser = new GameUser(thisUser.getId());
        BitmapFont bitmapFont = WidgetFactory.mBfNormalLg;
        mPlayers = new Array<BoxPlayer>();
        for (User u: Game.NETWORK_HELPER.users.getUsers()) {
            if (u.getId() != thisUser.getId()) {
                BoxPlayer player = new BoxPlayer(u, bitmapFont, mWorld);
                player.getBody().setTransform(-WORLD_WIDTH, -WORLD_HEIGHT, player.getBody().getAngle()); // Start off screen
                player.getBody().setFixedRotation(true);
                addActor(player);
                mPlayers.add(player);
            }
            else {
                mPlayer = new BoxPlayer(thisUser, bitmapFont, mWorld);
                addActor(mPlayer);
                Point initialPos = randomBoundedPoint((int) WORLD_WIDTH, (int) WORLD_HEIGHT);
                mPlayer.getBody().setTransform(initialPos.x, initialPos.y, mPlayer.getBody().getAngle());
                mPlayer.getBody().setFixedRotation(true);
            }
        }

        // Initial network update
        updateNetworkClass();
    }

    /**
     * Loop through each Player and check to see if the User
     * id is the same as the GameUser id. If there is a match,
     * update the Player's position based on gameUser.
     * @param gameUser
     */
    public void updatePlayer(final GameUser gameUser) {
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

    private void updateNetworkClass() {
        mGameUser.setPosX(mPlayer.getBody().getPosition().x);
        mGameUser.setPosY(mPlayer.getBody().getPosition().y);
        mInterface.playerMoved(mGameUser);
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

        updateNetworkClass();

        // Update Box2d
        mWorld.step(1/60f, 6, 2);
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
