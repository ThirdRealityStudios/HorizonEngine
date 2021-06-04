package org.thirdreality.horizonengine.core.game.environment;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

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

    private ArrayList<String> getNearKeys(Rectangle clipRect)
    {
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
    private ArrayList<Tile> getNearTiles(Rectangle clipRect)
    {
        nearTiles.clear();

        for(String key : getNearKeys(clipRect))
        {
            nearTiles.add(map.getTile(key));
        }

        return nearTiles;
    }

    public void render(Batch batch, Rectangle clippingBounds)
    {
        if(map != null)
        {
            ArrayList<Tile> nearTiles = getNearTiles(clippingBounds);

            for(Tile tile : nearTiles)
            {
                tile.render(batch);
            }
        }
    }
}
