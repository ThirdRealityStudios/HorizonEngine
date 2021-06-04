package org.thirdreality.horizonengine.core.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.*;
import org.thirdreality.horizonengine.core.game.environment.Map;
import org.thirdreality.horizonengine.core.game.environment.MapRenderer;
import org.thirdreality.horizonengine.core.game.object.action.ActionTrigger;
import org.thirdreality.horizonengine.core.tools.render.Clipping;
import org.thirdreality.horizonengine.settings.Settings;

// The Screen which is used in-game (having loaded a game with a map).
public class StrategyScreen implements Screen
{
    private OrthographicCamera mapCamera, screenCamera;
    private FitViewport viewport;

    private ActionTrigger actionTrigger;

    private SpriteBatch mapBatch, screenBatch;

    private MapRenderer mapRenderer;

    private Texture textureMapBackground, textureCamera, textureSquare;

    public StrategyScreen(Map map)
    {
        this.mapRenderer = new MapRenderer(map);

        mapCamera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), mapCamera);
        viewport.apply();

        mapBatch = new SpriteBatch();

        screenCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screenBatch = new SpriteBatch();

        actionTrigger = new ActionTrigger();

        Gdx.input.setInputProcessor(actionTrigger);

        initSampleTexture();
    }

    private void initSampleTexture()
    {
        Pixmap pixmapMap = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pixmapMap.setColor(Color.DARK_GRAY);
        pixmapMap.fillRectangle(0,0,1, 1);

        textureMapBackground = new Texture(pixmapMap);


        Pixmap pixmapCamera = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pixmapCamera.setColor(Color.GRAY);
        pixmapCamera.fillRectangle(0,0,1, 1);

        textureCamera = new Texture(pixmapCamera);


        Pixmap pixmapSquare = new Pixmap(1,1, Pixmap.Format.RGB888);
        pixmapSquare.setColor(Color.LIGHT_GRAY);
        pixmapSquare.fill();

        textureSquare = new Texture(pixmapSquare);
    }

    private void updateView()
    {
        mapBatch.setProjectionMatrix(mapCamera.projection);
        mapCamera.zoom *= 1.01f;
        mapCamera.update();

        screenBatch.setProjectionMatrix(screenCamera.projection);
        screenCamera.update();
    }

    @Override
    public void show()
    {

    }

    private void clearScreen()
    {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
    }

    private void drawMapBackground()
    {
        screenBatch.draw(textureMapBackground, -viewport.getWorldWidth() / 2, -viewport.getWorldHeight() / 2, viewport.getWorldWidth(), viewport.getWorldHeight());//, Metrics.EARTH_EQUATOR_LENGTH_KM, Metrics.EARTH_EQUATOR_LENGTH_KM);
    }

    private void drawClippingRect(Rectangle clipRect)
    {
        screenBatch.draw(textureCamera, clipRect.x, clipRect.y, clipRect.width, clipRect.height);//, Metrics.EARTH_EQUATOR_LENGTH_KM, Metrics.EARTH_EQUATOR_LENGTH_KM);
    }

    @Override
    public void render(float delta)
    {
        if(mapBatch == null)
        {
            return;
        }

        updateView();

        Rectangle clippingBounds = Clipping.getClippingBounds(viewport, Settings.CLIPPING_BOUNDS.width, Settings.CLIPPING_BOUNDS.height);

        clearScreen();

        screenBatch.begin();
        drawMapBackground();
        drawClippingRect(clippingBounds);
        screenBatch.end();

        mapBatch.begin();
        mapRenderer.render(mapBatch, clippingBounds);
        mapBatch.end();
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
