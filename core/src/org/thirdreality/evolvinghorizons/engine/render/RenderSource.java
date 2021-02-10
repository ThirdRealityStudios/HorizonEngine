package org.thirdreality.evolvinghorizons.engine.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.thirdreality.evolvinghorizons.Settings;

public class RenderSource
{
    private static final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private static final SpriteBatch spriteBatch = new SpriteBatch();
    private static final PolygonSpriteBatch polygonSpriteBatch = new PolygonSpriteBatch();

    // They both will be steadily updated.
    private static Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
    private static Texture texture = new Texture(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);

    public static void updateSources()
    {
        // See if the window resolution has changed. If so, update the pixmap and texture with the recently changed window size.
        if(pixmap.getWidth() != Gdx.graphics.getWidth() || pixmap.getHeight() != Gdx.graphics.getHeight())
        {
            pixmap = new Pixmap(Settings.SCREEN_SIZE.width, Settings.SCREEN_SIZE.height, Pixmap.Format.RGBA8888);

            texture = new Texture(pixmap.getWidth(), pixmap.getHeight(), pixmap.getFormat());
        }
    }

    public static ShapeRenderer getShapeRenderer()
    {
        return shapeRenderer;
    }

    public static SpriteBatch getSpriteBatch()
    {
        return spriteBatch;
    }

    public static PolygonSpriteBatch getPolygonSpriteBatch()
    {
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
