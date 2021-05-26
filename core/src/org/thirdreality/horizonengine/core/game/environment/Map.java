package org.thirdreality.horizonengine.core.game.environment;

import com.badlogic.gdx.math.Vector2;
import org.thirdreality.horizonengine.HorizonEngine;
import org.thirdreality.horizonengine.core.game.object.GameObject;
import org.thirdreality.horizonengine.core.game.Scene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class Map extends Scene
{
    // Used for indirect access to all GameObjects due to performance and management reasons.
    // For more information, search about "tile-based games" on the web.
    private TreeMap<String, Tile> tiles;

    public Map()
    {
        super();

        tiles = new TreeMap<String, Tile>();
    }

    private void initializeTiles()
    {
        tiles.clear();

        for(GameObject o : getObjects().values())
        {
            Vector2 position = o.getPosition();

            if(position.x < 0)
            {
                position.x -= Tile.size;
            }

            if(position.y < 0)
            {
                position.y -= Tile.size;
            }

            // Calculate which Tile the GameObject will be at.
            int kX = (int) (position.x / Tile.size);
            int kY = (int) (position.y / Tile.size);

            String key = Tile.createKey(kX, kY);

            if(!tiles.containsKey(key))
            {
                Tile createdTile = new Tile(kX, kY);

                HorizonEngine.info("Tile created: " + createdTile.getAlias());

                tiles.put(key, createdTile);
            }

            tiles.get(key).getObjects().add(o);
        }
    }

    // Initializes this Map by first reading all game objects.
    // Then it creates all necessary tiles (class 'Tile') from the objects.
    // The objects will be read 'tile-wise' during the render process and through most other processes.
    // This is simply due to performance reasons and easier management of the game Map.
    public void initialize(ArrayList<GameObject> objects)
    {
        super.initialize(objects);

        initializeTiles();
    }

    public Tile getTile(String key)
    {
        return tiles.get(key);
    }

    public Iterator<String> getKeys()
    {
        return tiles.keySet().iterator();
    }

    public Iterator<Tile> getTilesByIterator()
    {
        return tiles.values().iterator();
    }

    public boolean containsTile(String key)
    {
        return tiles.containsKey(key);
    }

    public int amountOfTiles()
    {
        return tiles.values().size();
    }
}
