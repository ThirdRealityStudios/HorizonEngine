package org.thirdreality.horizonengine.core.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.thirdreality.horizonengine.HorizonEngine;
import org.thirdreality.horizonengine.core.game.ui.StrategyScreen;

public abstract class HorizonGame extends Game
{
    public StrategyScreen strategyScreen;

    public abstract void init();

    @Override
    public void create()
    {
        strategyScreen = new StrategyScreen();

        setScreen(strategyScreen);

        init();

        HorizonEngine.info("Initialized HorizonGame!");
    }
}