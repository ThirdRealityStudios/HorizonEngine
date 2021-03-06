package org.thirdreality.evolvinghorizons.engine.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.thirdreality.evolvinghorizons.engine.settings.Settings;

public class RenderSource
{
    public static final OrthographicCamera orthographicCamera = createOrthographicCamera();

    private static final ShapeRenderer shapeRenderer_zoom = createShapeRenderer(true);
    private static final SpriteBatch spriteBatch_zoom = createSpriteBatch(true);
    private static final PolygonSpriteBatch polygonSpriteBatch_zoom = createPolygonSpriteBatch(true);

    private static final ShapeRenderer shapeRenderer = createShapeRenderer(false);
    private static final SpriteBatch spriteBatch = createSpriteBatch(false);
    private static final PolygonSpriteBatch polygonSpriteBatch = createPolygonSpriteBatch(false);

    // They both will be steadily updated.
    private static Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
    private static Texture texture = new Texture(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);

    private static OrthographicCamera createOrthographicCamera()
    {
        OrthographicCamera orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        orthographicCamera.position.x += Gdx.graphics.getWidth() / 2;
        orthographicCamera.position.y += Gdx.graphics.getHeight() / 2;
        orthographicCamera.update();

        return orthographicCamera;
    }

    private static ShapeRenderer createShapeRenderer(boolean zoomEnabled)
    {
        ShapeRenderer shapeRenderer = new ShapeRenderer();

        if(zoomEnabled)
        {
            shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
        }

        return shapeRenderer;
    }

    private static PolygonSpriteBatch createPolygonSpriteBatch(boolean zoomEnabled)
    {
        PolygonSpriteBatch polygonSpriteBatch = new PolygonSpriteBatch();

        if(zoomEnabled)
        {
            polygonSpriteBatch.setProjectionMatrix(orthographicCamera.combined);
        }

        return polygonSpriteBatch;
    }

    private static SpriteBatch createSpriteBatch(boolean zoomEnabled)
    {
        SpriteBatch spriteBatch = new SpriteBatch();

        if(zoomEnabled)
        {
            spriteBatch.setProjectionMatrix(orthographicCamera.combined);
        }

        return spriteBatch;
    }

    public static void update()
    {
        // See if the window resolution has changed. If so, update the pixmap and texture with the recently changed window size.
        if(pixmap.getWidth() != Gdx.graphics.getWidth() || pixmap.getHeight() != Gdx.graphics.getHeight())
        {
            pixmap = new Pixmap(Settings.SCREEN_SIZE.width, Settings.SCREEN_SIZE.height, Pixmap.Format.RGBA8888);

            texture = new Texture(pixmap.getWidth(), pixmap.getHeight(), pixmap.getFormat());
        }
    }

    public static ShapeRenderer getShapeRenderer(boolean zoomEnabled)
    {
        if(zoomEnabled)
        {
            orthographicCamera.update();
            shapeRenderer_zoom.setProjectionMatrix(orthographicCamera.combined);

            return shapeRenderer_zoom;
        }

        return shapeRenderer;
    }

    public static SpriteBatch getSpriteBatch(boolean zoomEnabled)
    {
        if(zoomEnabled)
        {
            orthographicCamera.update();
            spriteBatch_zoom.setProjectionMatrix(orthographicCamera.combined);

            return spriteBatch_zoom;
        }

        return spriteBatch;
    }

    public static PolygonSpriteBatch getPolygonSpriteBatch(boolean zoomEnabled)
    {
        if(zoomEnabled)
        {
            orthographicCamera.update();

            polygonSpriteBatch_zoom.setProjectionMatrix(orthographicCamera.combined);

            return polygonSpriteBatch_zoom;
        }

        return polygonSpriteBatch;
    }

    public static Pixmap getPixmap()
    {
        return pixmap;
    }

    public static Texture getTexture()
    {
        return texture;
    }
}
