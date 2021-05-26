package org.thirdreality.horizonengine.core.game.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.horizonengine.HorizonEngine;
import org.thirdreality.horizonengine.core.game.object.GameObject;
import org.thirdreality.horizonengine.core.game.object.management.LOD;
import org.thirdreality.horizonengine.core.tools.math.Polygon;

import java.util.ArrayList;

// A rectangular tile to organize and manage the game map in a better way.
public class Tile extends GameObject
{
    public static final float size = 500;

    private ArrayList<GameObject> objects;

    public Tile(int kX, int kY)
    {
        super();

        setAlias(createKey(kX, kY));

        this.objects = new ArrayList<GameObject>(24);

        LOD lod = new LOD(0);
        lod.setPolygon(createShape(kX, kY));
        lod.setTexture(createTexture());

        getLODGroup().addLOD(lod);
    }

    private Polygon createShape(int kX, int kY)
    {
        float x = kX*size, y = kY*size;

        return new Polygon(new float[]{x, y, x + size, y, x + size, y + size, x, y + size}, true);
    }

    private Texture createTexture()
    {
        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGB888);
        pixmap.setColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1));
        pixmap.fill();

        return new Texture(pixmap);
    }

    public static String createKey(int kX, int kY)
    {
        return kX + "_" + kY;
    }

    public ArrayList<GameObject> getObjects()
    {
        return objects;
    }

    public void render(Batch batch)
    {
        LOD lod = getLODGroup().getLOD(0);

        Rectangle rect = lod.getPolygon().getBoundingRectangle();

        batch.begin();
        batch.draw(lod.getTexture(), rect.x, rect.y, rect.width, rect.height);
        batch.end();
    }
}