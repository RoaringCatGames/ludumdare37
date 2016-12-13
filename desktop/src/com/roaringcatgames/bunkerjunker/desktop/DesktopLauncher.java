package com.roaringcatgames.bunkerjunker.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.roaringcatgames.bunkerjunker.BunkerJunker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 960;
		config.height = 640;
		//config.fullscreen = true;

		new LwjglApplication(new BunkerJunker(), config);
	}
}
