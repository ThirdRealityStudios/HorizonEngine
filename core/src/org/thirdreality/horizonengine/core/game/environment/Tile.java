package org.thirdreality.horizonengine.core.game.environment;

import org.thirdreality.horizonengine.core.game.object.GameObject;

import java.util.ArrayList;

// A rectangular tile to organize and manage the game map in a better way.
public class Tile
{
    public static final float size = 20;

    private int kX, kY;
    private ArrayList<GameObject> objects;

    public Tile(int kX, int kY)
    {
        this.kX = kX;
        this.kY = kY;

        this.objects = new ArrayList<GameObject>(24);
    }

    public int getKX()
    {
        return kX;
    }

    public int getKY()
    {
        return kY;
    }

    public ArrayList<GameObject> getObjects()
    {
        return objects;
    }
}