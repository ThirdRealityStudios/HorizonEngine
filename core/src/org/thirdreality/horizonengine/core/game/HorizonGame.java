package org.thirdreality.horizonengine.core.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.thirdreality.horizonengine.HorizonEngine;
import org.thirdreality.horizonengine.core.game.management.GameManager;

public abstract class HorizonGame extends Game
{
    private Viewport viewport;
    private OrthographicCamera camera;

    private GameManager gameManager;

    public HorizonGame()
    {
        // Everything needs to be called here (not in the constructor) due to the dependence to the Gdx resources.
        gameManager = new GameManager();
    }

    @Override
    public void create()
    {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        HorizonEngine.info("Created game");

        //viewport = new StretchViewport();

        /*
        setScreen(new GameScreen()
        {
            @Override
            public void render(float delta)
            {

            }
        });

         */
    }

    public GameManager getGameManager()
    {
        return gameManager;
    }
}
