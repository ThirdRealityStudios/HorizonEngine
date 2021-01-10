package org.thirdreality.evolvinghorizons.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.thirdreality.evolvinghorizons.Main;
import org.thirdreality.evolvinghorizons.Settings;

import java.awt.*;

public class DesktopLauncher {

	public static LwjglApplicationConfiguration CONFIG;
	public static LwjglApplication APP;

	public static void main (String[] arg) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		CONFIG = new LwjglApplicationConfiguration();

		CONFIG.width = Settings.SCREEN_SIZE.width;
		CONFIG.height = Settings.SCREEN_SIZE.height;

		CONFIG.resizable = false;

		APP = new LwjglApplication(new Main(), CONFIG);
	}
}
