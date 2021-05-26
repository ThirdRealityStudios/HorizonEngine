package org.thirdreality.horizonengine;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.thirdreality.horizonengine.core.game.HorizonGame;
import org.thirdreality.horizonengine.settings.Settings;

public class HorizonApp
{
    protected LwjglApplicationConfiguration LWJGLConfig;

    // Used to call every HorizonGame below.
    protected Game caller;

    private HorizonGame game;

    private boolean isLibGDXInitialized = false;

    public HorizonApp(String title, int width, int height)
    {
        LWJGLConfig = new LwjglApplicationConfiguration();

        LWJGLConfig.title = title;

        LWJGLConfig.width = width;
        LWJGLConfig.height = height;

        LWJGLConfig.resizable = false;
        LWJGLConfig.fullscreen = false;

        caller = new Game()
        {
            private void initSettings()
            {
                Settings.SYSTEM_SCREEN.width = width;
                Settings.SYSTEM_SCREEN.height = height;

                Settings.CLIPPING_BOUNDS.width = (int) (width * 0.75f);
                Settings.CLIPPING_BOUNDS.height = (int) (height * 0.75f);
            }

            @Deprecated
            @Override
            public void create()
            {
                // Does nothing currently. If so, it may NOT call any method of "game" as this runs asynchronously by LibGDX!
                // Especially no create-method may be called here (would execute twice as the initialization with setGame(..) is usually faster).
                // Beware of uncontrolled / arbitrary behaviour if you do so anyway!

                isLibGDXInitialized = true;

                initSettings();

                boolean gameNotInitializedBefore = game != null;

                if(gameNotInitializedBefore)
                {
                    game.create();
                }
            }

            @Override
            public void dispose()
            {
                if(game != null)
                {
                    game.dispose();
                }
            }

            @Override
            public void pause()
            {
                if(game != null)
                {
                    game.pause();
                }
            }

            @Override
            public void resume()
            {
                if(game != null)
                {
                    game.resume();
                }
            }

            @Override
            public void render()
            {
                if(game != null)
                {
                    game.render();
                }
            }

            @Override
            public void resize(int width, int height)
            {
                if(game != null)
                {
                    initSettings();

                    game.resize(width, height);
                }
            }
        };
    }

    public void setGame(HorizonGame game)
    {
        // Make sure the last game saves everything and shuts down correctly.
        if(this.game != null)
        {
            this.game.dispose();
        }

        if(isLibGDXInitialized)
        {
            game.create();
        }

        // Uses the new game.
        this.game = game;
    }

    public HorizonGame getGame()
    {
        return game;
    }

    public LwjglApplicationConfiguration getGLConfig()
    {
        return LWJGLConfig;
    }
}
