package org.thirdreality.horizonengine.core.tools.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Clipping
{
    // Returns a Rectangle describing the part on the Map which the user currently looks at.
    // Will automatically "shrink" the Rectangle if it is outside the bounds of the Map (see 'worldWidth' and 'worldHeight' of Viewport).
    public static Rectangle getClippingBounds(Viewport viewport, float screenWidth, float screenHeight)
    {
        Vector3 origin = new Vector3(viewport.getCamera().position);

        float width = (screenWidth / Gdx.graphics.getWidth()) * viewport.getWorldWidth();

        float height = (screenHeight / Gdx.graphics.getHeight()) * viewport.getWorldHeight();

        Rectangle clipRect = new Rectangle(origin.x - width / 2, origin.y - height / 2, width, height);

        /*
        float widthUnprojected = clipCornerUnprojected.x - clipOriginUnprojected.x, heightUnprojected = clipCornerUnprojected.y - clipOriginUnprojected.y;

        Rectangle clipRect = new Rectangle(clipOriginUnprojected.x, clipOriginUnprojected.y - heightUnprojected, widthUnprojected, heightUnprojected);
         */

        //HorizonEngine.info("clip rect: " + clipRect);

        return clipRect;
    }

    public static boolean isClippable(Rectangle clippingBounds, Rectangle object)
    {
        return !clippingBounds.overlaps(object);
    }
}
