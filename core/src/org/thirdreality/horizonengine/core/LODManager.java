package org.thirdreality.horizonengine.core;

import java.util.ArrayList;
import java.util.TreeMap;

public class LODManager
{
    private TreeMap<Float, LOD> lodObjects;

    private final float DEFAULT_ACCESS_KEY;

    public LODManager()
    {
        lodObjects = new TreeMap<Float, LOD>();

        DEFAULT_ACCESS_KEY = 0;
    }

    public LOD addObjectsDirectly(ArrayList<GameObject> objects)
    {
        LOD lod;

        if(!lodObjects.containsKey(DEFAULT_ACCESS_KEY))
        {
            lod = new LOD(DEFAULT_ACCESS_KEY);

            lod.addObjects(objects);

            lodObjects.put(DEFAULT_ACCESS_KEY, lod);
        }
        else
        {
            lod = lodObjects.get(DEFAULT_ACCESS_KEY);

            lod.addObjects(objects);

        }

        return lod;
    }

    public void add(LOD level)
    {
        lodObjects.put(level.getMinDistance(), level);
    }

    public void remove(Float minDistance)
    {
        lodObjects.remove(minDistance);
    }

    public LOD get(Float minDistance)
    {
        return lodObjects.get(minDistance);
    }
}
