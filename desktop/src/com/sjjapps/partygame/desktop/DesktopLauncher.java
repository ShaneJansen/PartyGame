package com.sjjapps.partygame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sjjapps.partygame.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 1000; //700
		config.height = 500; //380
		//config.resizable = false;

		new LwjglApplication(new Game(), config);
	}
}
