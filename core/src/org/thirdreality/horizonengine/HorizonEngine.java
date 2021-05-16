package org.thirdreality.horizonengine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.thirdreality.horizonengine.core.GameManager;
import org.thirdreality.horizonengine.core.screen.DefaultScreen;

public abstract class HorizonEngine
{
    public static HorizonApplication app;

    public static LwjglApplicationConfiguration LWJGLConfig;
    public static LwjglApplication LWJGLApp;

    private static class GdxApplication extends Game
    {
        @Override
        public void create()
        {
            // Everything needs to be called here (not in the constructor) due to the dependence to the Gdx resources.
            app.manager = new GameManager();

            app.pre();

            setScreen(new DefaultScreen()
            {
                @Override
                public void render(float delta)
                {
                    app.loop();
                }
            });
        }
    }

    public static void start(HorizonApplication horizonApp)
    {
        app = horizonApp;

        LWJGLConfig = new LwjglApplicationConfiguration();

        LWJGLConfig.width = 1024;
        LWJGLConfig.height = 768;

        LWJGLConfig.resizable = false;
        LWJGLConfig.fullscreen = false;
        LWJGLConfig.title = "Horizon Engine - Demo";

        LWJGLApp = new LwjglApplication(new GdxApplication(), LWJGLConfig);
    }
}
