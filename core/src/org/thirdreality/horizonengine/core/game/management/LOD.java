package org.thirdreality.horizonengine.core.game.management;

import com.badlogic.gdx.Gdx;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.thirdreality.horizonengine.core.game.environment.GameLayer;
import org.thirdreality.horizonengine.core.game.environment.GameObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

// A class to create a level-of-detail.
// The LOD defines itself by a range when it should go active (measured by the distance of the camera).
// It just contains GameObjects which are used then.
public class LOD
{
    // A kind of database which can manage (e.g. sort data or delete null values) all game objects efficiently during run-time.
    private Multimap<Integer, GameObject> gameObjectsReady;

    // A temporary list of the objects which were added recently but not processed yet.
    private ArrayList<GameObject> gameObjectsUnsorted;

    // A temporary list of the layers which have not been processed yet (when adding new ones).
    private ArrayList<GameLayer> gameLayersUnprocessed;

    private final float minDistance;

    public LOD(float minDistance)
    {
        this.minDistance = minDistance;

        gameObjectsReady = ArrayListMultimap.create();
        gameObjectsUnsorted = new ArrayList<GameObject>(24);
        gameLayersUnprocessed = new ArrayList<GameLayer>(24);
    }

    public void addObjects(ArrayList<GameObject> objects)
    {
        gameObjectsUnsorted.addAll(objects);

        update();
    }

    public Collection<GameObject> getObjects(int zIndex)
    {
        return gameObjectsReady.asMap().get(zIndex);
    }

    public Set<Integer> getZIndices()
    {
        return gameObjectsReady.asMap().keySet();
    }

    private void update(Iterator<GameObject> unsortedObjects, int zIndex)
    {
        while(unsortedObjects.hasNext())
        {
            GameObject unsortedObject = unsortedObjects.next();

            if(unsortedObject instanceof GameLayer)
            {
                GameLayer unprocessedGameLayer = (GameLayer) unsortedObject;

                gameLayersUnprocessed.add(unprocessedGameLayer);
            }
            else if(!gameObjectsReady.containsValue(unsortedObject))
            {
                gameObjectsReady.put(zIndex, unsortedObject);
            }
        }
    }

    protected void update()
    {
        if(!gameObjectsUnsorted.isEmpty())
        {
            update(gameObjectsUnsorted.iterator(), 0);

            for (int layer = 0; layer < gameLayersUnprocessed.size(); layer++)
            {
                GameLayer currentGameLayer = gameLayersUnprocessed.get(layer);

                update(currentGameLayer.getMembersIterated(), currentGameLayer.getZIndex());
            }
        }
    }

    public float getMinDistance()
    {
        return minDistance;
    }

    public void printUnsortedGameObjects()
    {
        System.out.println("---");
        System.out.println("Size: " + gameObjectsUnsorted.size());

        for(GameObject o : gameObjectsUnsorted)
        {
            Gdx.app.log("Unsorted objects", o.getAlias());
        }
    }

    public void printUnprocessedGameLayers()
    {
        System.out.println("---");
        System.out.println("Size: " + gameLayersUnprocessed.size());

        for(GameObject o : gameLayersUnprocessed)
        {
            Gdx.app.log("Unprocessed game layers", "[zIndex: " + ((GameLayer) o).getZIndex() + "] " + o.getAlias());
        }
    }

    public void printReadyGameObjects()
    {
        System.out.println("--- (LOD: " + getMinDistance() + ") ---");

        Iterator iterator = gameObjectsReady.keySet().iterator();

        while(iterator.hasNext())
        {
            Integer key = (Integer) iterator.next();

            Collection<GameObject> objects = gameObjectsReady.get(key);

            for(GameObject object : objects)
            {
                Gdx.app.log("Ready objects", "[zIndex: " + key + "] " + object.getAlias());
            }
        }
    }
}
