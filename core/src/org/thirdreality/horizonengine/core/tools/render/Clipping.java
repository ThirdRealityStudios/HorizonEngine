package org.thirdreality.horizonengine.core.tools.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;

public class Clipping
{
    public static Rectangle unprojectRectangle(Rectangle rect, Camera camera)
    {
        Vector3 origin = camera.unproject(new Vector3(rect.x, rect.y, 0f));
        Vector3 corner = camera.unproject(new Vector3(rect.x + rect.width, rect.y + rect.height, 0f));

        float width = corner.x - origin.x;
        float height = origin.y - corner.y;

        Rectangle unprojected = new Rectangle(origin.x - width / 2, origin.y, width, height);

        return unprojected;
    }

    // Returns a Rectangle describing the part on the Map which the user currently looks at.
    // Will automatically "shrink" the Rectangle if it is outside the bounds of the Map (see 'worldWidth' and 'worldHeight' of Viewport).
    public static Rectangle getClippingBoundsRender(Viewport viewport)
    {
        Dimension clippingSize = new Dimension((int) (Gdx.graphics.getWidth() * 0.75f), (int) (Gdx.graphics.getHeight() * 0.75f));

        float width = (clippingSize.width / (float) Gdx.graphics.getWidth()) * viewport.getWorldWidth();

        float height = (clippingSize.height / (float) Gdx.graphics.getHeight()) * viewport.getWorldHeight();

        // These two values were retrieved by figuring them out.. (actually without any mathematical way)
        final float widthRatio = 0.125f;
        final float heightRatio = 0.5f;

        Rectangle clipRect = new Rectangle(viewport.getWorldWidth() - viewport.getWorldWidth() * widthRatio - width / 2 - viewport.getCamera().position.x,  viewport.getWorldHeight() * heightRatio + height / 2 + viewport.getCamera().position.y, width, height);

        //Rectangle clipRect = new Rectangle(viewport.getCamera().position.x - width / 2, viewport.getCamera().position.y - height / 2, width, height);

        Rectangle unprojectedRectangle = unprojectRectangle(clipRect, viewport.getCamera());

        return unprojectedRectangle;
    }

    // Returns a Rectangle describing the part on the Map which the user currently looks at.
    // Will automatically "shrink" the Rectangle if it is outside the bounds of the Map (see 'worldWidth' and 'worldHeight' of Viewport).
    public static Rectangle getClippingBounds(Viewport viewport)
    {
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();

        //Rectangle clipRect = Clipping.getClippingBounds(viewport);

        final float WIDTH = Gdx.graphics.getWidth();
        final float HEIGHT = Gdx.graphics.getHeight();

        float x = camera.position.x;
        float y = camera.position.y;

        Rectangle clipRect = new Rectangle(x, y, WIDTH, HEIGHT);

        return clipRect;
    }

    public static boolean isClippable(Rectangle clippingBounds, Rectangle object)
    {
        return !clippingBounds.overlaps(object);
    }
}
