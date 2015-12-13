package com.sjjapps.partygame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sjjapps.partygame.managers.MultiplexerManager;
import com.sjjapps.partygame.screens.mainmenu.MainMenu;

/**
 * Created by Shane Jansen on 12/4/15.
 *
 * The Game class holds a reference to the current Realm and
 * provides getters and setters for it.  Make sure to call super
 * on any overridden Game methods.  Game's render implementation
 * will automatically update and render the active Realm reference.
 */
public class Game implements ApplicationListener {
	public static final String TAG = "MyGame";
	public static final float WORLD_WIDTH = 800.0f;
	public static final float WORLD_HEIGHT = 480.0f;
	public static float PPU;
	public static boolean PAUSED = false;

	public static AssetManager ASSETS;
	public static SpriteBatch SPRITE_BATCH;
	public static ShapeRenderer SHAPE_RENDERER;
	public static MultiplexerManager MULTIPLEXER_MANAGER;
	public static final com.badlogic.gdx.Game GAME = new com.badlogic.gdx.Game() {
		@Override
		public void create() {
			PPU = Gdx.graphics.getWidth() / Game.WORLD_WIDTH;

			ASSETS =  new AssetManager();
			SPRITE_BATCH = new SpriteBatch();
			SHAPE_RENDERER = new ShapeRenderer();
			MULTIPLEXER_MANAGER = new MultiplexerManager(new InputMultiplexer());

			Gdx.input.setInputProcessor(MULTIPLEXER_MANAGER.getInputMultiplexer());
			Texture.setAssetManager(ASSETS);
			GAME.setScreen(new MainMenu()); // Initial screen
		}
	};

	public static void log(String message) {
		Gdx.app.log(TAG, message);
	}

	/**
	 * Used to initialize subsystems and load resources.
	 */
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG); // Set to Application.NONE for release
		GAME.create();
	}

	/**
	 * Used to handle setting a new screen size, which
	 * can be used to reposition UI elements or reconfigure
	 * camera objects.
	 */
	@Override
	public void resize(int width, int height) {
		PPU = Gdx.graphics.getWidth() / Game.WORLD_WIDTH;
		GAME.resize(width, height);
	}

	/**
	 * Called continuously for every game loop iteration.
	 * Used to update and render the game elements
	 */
	@Override
	public void render () {
		GAME.render();
	}

	/**
	 * Save game state when it loses focus which does
	 * not involve the actual game being puased unless
	 * specified.
	 */
	@Override
	public void pause() {
		PAUSED = true;
		GAME.pause();
	}

	/**
	 * Used to handle the game coming back from being
	 * paused and restores the game state.
	 */
	@Override
	public void resume() {
		PAUSED = false;
		GAME.resume();
	}

	/**
	 * Used to free resources and clean up.
	 */
	@Override
	public void dispose() {
		ASSETS.dispose();
		SPRITE_BATCH.dispose();
		SHAPE_RENDERER.dispose();
		GAME.dispose();
	}
}
