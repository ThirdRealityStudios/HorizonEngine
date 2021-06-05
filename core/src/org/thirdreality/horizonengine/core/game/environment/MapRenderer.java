package org.thirdreality.horizonengine.core.game.environment;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.thirdreality.horizonengine.HorizonEngine;
import org.thirdreality.horizonengine.core.tools.render.Clipping;

import java.util.ArrayList;

public class MapRenderer
{
    private ArrayList<Tile> nearTiles = new ArrayList<Tile>();

    private Map map;

    public MapRenderer(Map map)
    {
        this.map = map;
    }

    public void setMap(Map map)
    {
        this.map = map;
    }

    public Map getMap()
    {
        return map;
    }

    private ArrayList<String> getNearKeys(Viewport viewport, Camera screen)
    {
        Rectangle clipRect = Clipping.getClippingBounds(viewport, screen);

        int kXMin = (int) (clipRect.x / Tile.size);
        int kYMin = (int) (clipRect.y / Tile.size);

        float kWidth = clipRect.width / Tile.size;
        float kHeight = clipRect.height / Tile.size;

        float kXMax = (int) (kXMin + kWidth);
        float kYMax = (int) (kYMin + kHeight);

        ArrayList<String> keys = new ArrayList<String>();

        for(int x = kXMin; x <= kXMax; x++)
        {
            for(int y = kYMin; y <= kYMax; y++)
            {
                String key = Tile.createKey(x, y);

                if(map.containsTile(key))
                {
                    keys.add(key);
                }
            }
        }

        return keys;
    }

    // Returns all Tiles which are seen inside the given bounds on screen.
    // Helps to improve performance if there is a lot of game content.
    private ArrayList<Tile> getNearTiles(Viewport viewport, Camera screen)
    {
        nearTiles.clear();

        for(String key : getNearKeys(viewport, screen))
        {
            nearTiles.add(map.getTile(key));
        }

        return nearTiles;
    }

    public void render(Batch batch, Viewport viewport, Camera screen)
    {
        if(map != null)
        {
            ArrayList<Tile> nearTiles = getNearTiles(viewport, screen);

            HorizonEngine.info("Tiles near: " + nearTiles.size());

            for(Tile tile : nearTiles)
            {
                tile.render(batch);
            }



            /*
            Iterator<Tile> tileIterator = map.getTilesByIterator();

            while(tileIterator.hasNext())
            {
                tileIterator.next().render(batch);
            }

             */


        }
    }
}
