package org.thirdreality.horizonengine.core.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.thirdreality.horizonengine.HorizonEngine;
import org.thirdreality.horizonengine.core.game.ui.StrategyScreen;

public abstract class HorizonGame extends Game
{
    public StrategyScreen strategyScreen;

    // Returns a Rectangle describing the part on the Map which the user currently looks at.
    // Will automatically "shrink" the Rectangle if it is outside the bounds of the Map (see 'worldWidth' and 'worldHeight' of Viewport).
    public static Rectangle getClippedBounds(Viewport viewport, float screenWidth, float screenHeight)
    {
        Vector3 origin = new Vector3(viewport.getCamera().position);

        float width = (screenWidth / Gdx.graphics.getWidth()) * viewport.getWorldWidth();

        float height = (screenHeight / Gdx.graphics.getHeight()) * viewport.getWorldHeight();

        Rectangle clipRect = new Rectangle(origin.x - width / 2, origin.y - height / 2, width, height);

        /*
        float widthUnprojected = clipCornerUnprojected.x - clipOriginUnprojected.x, heightUnprojected = clipCornerUnprojected.y - clipOriginUnprojected.y;

        Rectangle clipRect = new Rectangle(clipOriginUnprojected.x, clipOriginUnprojected.y - heightUnprojected, widthUnprojected, heightUnprojected);
         */

        //HorizonEngine.info("clip rect: " + clipRect);

        return clipRect;
    }

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