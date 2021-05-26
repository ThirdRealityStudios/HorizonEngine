package org.thirdreality.horizonengine.core.game;

import com.badlogic.gdx.Game;
import org.thirdreality.horizonengine.HorizonEngine;
import org.thirdreality.horizonengine.core.game.environment.Map;
import org.thirdreality.horizonengine.core.game.ui.StrategyScreen;

public abstract class HorizonGame extends Game
{
    private StrategyScreen strategyScreen;

    private Map map;

    public abstract void init();

    @Override
    public void create()
    {
        init();

        strategyScreen = new StrategyScreen(map);

        setScreen(strategyScreen);

        HorizonEngine.info("Initialized HorizonGame!");
    }

    // Sets the game map.
    public void setMap(Map map)
    {
        this.map = map;
    }

    // Gets the game map.
    public Map getMap()
    {
        return map;
    }
}