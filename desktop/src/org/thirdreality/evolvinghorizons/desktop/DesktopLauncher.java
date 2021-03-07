package org.thirdreality.evolvinghorizons.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.thirdreality.evolvinghorizons.SampleApplication;
import org.thirdreality.evolvinghorizons.engine.settings.Settings;

public class DesktopLauncher
{
	public static LwjglApplicationConfiguration CONFIG;
	public static LwjglApplication APP;

	public static void main (String[] arg)
	{
		CONFIG = new LwjglApplicationConfiguration();

		CONFIG.width = Settings.SCREEN_SIZE.width;
		CONFIG.height = Settings.SCREEN_SIZE.height;

		CONFIG.resizable = false;
		CONFIG.fullscreen = false;
		CONFIG.title = "Horizon Engine";

		APP = new LwjglApplication(new SampleApplication(), CONFIG);
	}
}
