package org.thirdreality.horizonengine.core.game.environment;

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

    private String createTileKey(int kX, int kY)
    {
        return kX + "_" + kY;
    }

    private void initializeTiles()
    {
        tiles.clear();

        for(GameObject o : getObjects().values())
        {
            // Calculate which Tile the GameObject will be at.
            int kX = (int) (o.getPosition().x / Tile.size);
            int kY = (int) (o.getPosition().y / Tile.size);

            String key = createTileKey(kX, kY);

            if(!tiles.containsKey(key))
            {
                Tile createdTile = new Tile(kX, kY);

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

    public Tile getTile(int kX, int kY)
    {
        String key = createTileKey(kX, kY);

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

    public int amountOfTiles()
    {
        return tiles.values().size();
    }
}
