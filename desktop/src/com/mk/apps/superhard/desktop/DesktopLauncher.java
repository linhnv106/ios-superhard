package com.mk.apps.superhard.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.mk.apps.superhard.MyGdxGame;
import com.mk.apps.superm.MainStarter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 768;
//        Texture.setEnforcePotImages(false);

		new LwjglApplication(new MainStarter(), config);
	}
}
