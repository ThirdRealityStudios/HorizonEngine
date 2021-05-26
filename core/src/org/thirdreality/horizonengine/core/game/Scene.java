package org.thirdreality.horizonengine.core.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.thirdreality.horizonengine.core.game.object.GameObject;

import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Scene
{
    // Used for direct access by the user, e.g. to make direct changes to the object.
    protected TreeMap<String, GameObject> objects;

    public Scene()
    {
        objects = new TreeMap<String, GameObject>();
    }

    private void saveObjects(ArrayList<GameObject> objects)
    {
        for(GameObject o : objects)
        {
            this.objects.put(o.getAlias(), o);
        }
    }

    public void initialize(ArrayList<GameObject> objects)
    {
        saveObjects(objects);
    }

    public abstract void render(Viewport viewport);
}
