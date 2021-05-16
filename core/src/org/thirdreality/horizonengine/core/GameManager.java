package org.thirdreality.horizonengine.core;

import com.badlogic.gdx.Gdx;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.thirdreality.horizonengine.core.action.ActionTrigger;

import java.util.ArrayList;
import java.util.Collection;

public class GameManager
{
    private ActionTrigger actionTrigger;

    // A kind of database which can manage (e.g. sort data or delete null values) all game objects efficiently during run-time.
    private Multimap<Integer, GameObject> gameObjectsReady;

    // A temporary list of the objects which were added recently but not processed yet.
    private ArrayList<GameObject> gameObjectsUnsorted;

    // A temporary list of the layers which have not been processed yet (when adding new ones).
    private ArrayList<GameLayer> gameLayersUnprocessed;

    private static final int MAX_Z_INDEX = 64;

    public GameManager()
    {
        actionTrigger = new ActionTrigger();

        Gdx.input.setInputProcessor(actionTrigger);

        gameObjectsReady = ArrayListMultimap.create();
        gameObjectsUnsorted = new ArrayList<GameObject>(24);
        gameLayersUnprocessed = new ArrayList<GameLayer>(24);
    }

    // Will take care of newly added objects.
    private void update(ArrayList<GameObject> unsortedObjects, int zIndex)
    {
        for(int object = 0; object < unsortedObjects.size(); object++)
        {
            GameObject unsortedObject = unsortedObjects.get(object);

            if(unsortedObject instanceof GameLayer)
            {
                GameLayer unprocessedGameLayer = (GameLayer) unsortedObject;

                gameLayersUnprocessed.add(unprocessedGameLayer);
            }
            else if(!gameObjectsReady.containsEntry(zIndex, unsortedObject))
            {
                gameObjectsReady.put(zIndex, unsortedObject);
            }
        }
    }

    public void update()
    {
        if(!gameObjectsUnsorted.isEmpty())
        {
            update(gameObjectsUnsorted, 0);

            for (int layer = 0; layer < gameLayersUnprocessed.size(); layer++)
            {
                GameLayer currentGameLayer = gameLayersUnprocessed.get(layer);

                update(currentGameLayer.getMembers(), currentGameLayer.getZIndex());
            }
        }
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
        System.out.println("---");
        System.out.println("Size: " + gameObjectsReady.size());

        for(int i = 0; i < gameObjectsReady.size(); i++)
        {
            Collection<GameObject> objects = gameObjectsReady.get(i);

            for(GameObject object : objects)
            {
                Gdx.app.log("Ready objects", "[zIndex: " + i + "] " + object.getAlias());
            }
        }
    }

    public void addGameObjects(ArrayList<GameObject> objects)
    {
        gameObjectsUnsorted.addAll(objects);
    }
}
