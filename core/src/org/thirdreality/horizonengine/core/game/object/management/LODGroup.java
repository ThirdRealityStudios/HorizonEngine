package org.thirdreality.horizonengine.core.game.object.management;

import java.util.Iterator;
import java.util.TreeMap;

public class LODGroup
{
    private TreeMap<Float, LOD> lods;

    public LODGroup()
    {
        lods = new TreeMap<Float, LOD>();
    }

    public void addLOD(LOD lod)
    {
        // Make sure the old LOD can be overwritten (generally) because duplicate keys are not allowed!
        if(lods.containsKey(lod.getMinDistance()))
        {
            lods.remove(lod.getMinDistance());
        }

        lods.put(lod.getMinDistance(), lod);
    }

    // Removes the given LOD by its 'minimum distance' as the key.
    public void removeLOD(LOD lod)
    {
        lods.remove(lod.getMinDistance());
    }

    public LOD getLOD(float minDistance)
    {
        return lods.get(minDistance);
    }

    public Iterator<LOD> getLODsByIterator()
    {
        return lods.values().iterator();
    }
}
