package org.thirdreality.horizonengine;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.lwjgl.Sys;
import org.thirdreality.horizonengine.core.game.HorizonGame;

public class HorizonApp
{
    protected LwjglApplicationConfiguration LWJGLConfig;

    // Used to call every HorizonGame below.
    protected Game caller;

    private HorizonGame game;

    public HorizonApp(String title, int width, int height)
    {
        LWJGLConfig = new LwjglApplicationConfiguration();

        LWJGLConfig.title = title;

        LWJGLConfig.width = width;
        LWJGLConfig.height = height;

        LWJGLConfig.resizable = false;

        caller = new Game()
        {
            @Deprecated
            @Override
            public void create()
            {
                // Does nothing currently. If so, it may NOT call any method of "game" as this runs asynchronously by LibGDX!
                // Especially no create-method may be called here (would execute twice as the initialization with setGame(..) is usually faster).
                // Beware of uncontrolled / arbitrary behaviour if you do so anyway!
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
            game.dispose();
        }

        // Uses the new game.
        this.game = game;

        game.create();
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
