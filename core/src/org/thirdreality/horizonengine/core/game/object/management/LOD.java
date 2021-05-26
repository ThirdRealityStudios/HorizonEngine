package org.thirdreality.horizonengine.core.game.object.management;

import com.badlogic.gdx.graphics.Texture;
import org.thirdreality.horizonengine.core.tools.math.Polygon;

public class LOD
{
    // Contains a polygon describing the local (!) shape of the corresponding GameObject.
    // Remind yourself that the polygon does NOT describe the final position on the map later!
    // If you want to set the global position you need to do it in the game object ( method "setPosition(..)" ).
    private Polygon polygon;

    // The texture will appear exactly at the position of the polygons bounds.
    private Texture texture;

    // Tells the engine when to use this LOD (minimum distance from the camera).
    private final float minDistance;

    public LOD(float minDistance)
    {
        this.minDistance = minDistance;
    }

    public Polygon getPolygon()
    {
        return polygon;
    }

    public void setPolygon(Polygon polygon)
    {
        this.polygon = polygon;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public void setTexture(Texture texture)
    {
        this.texture = texture;
    }

    public float getMinDistance()
    {
        return minDistance;
    }
}
