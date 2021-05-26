package org.thirdreality.horizonengine.core.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;
import org.checkerframework.checker.units.qual.A;
import org.thirdreality.horizonengine.HorizonEngine;
import org.thirdreality.horizonengine.core.game.HorizonGame;
import org.thirdreality.horizonengine.core.game.Metrics;
import org.thirdreality.horizonengine.core.game.environment.Map;
import org.thirdreality.horizonengine.core.game.environment.Tile;
import org.thirdreality.horizonengine.core.game.object.action.ActionTrigger;
import org.thirdreality.horizonengine.core.tools.render.Clipping;
import org.thirdreality.horizonengine.settings.Settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

// The Screen which is used in-game (having loaded a game with a map).
public class StrategyScreen implements Screen
{
    private Viewport viewport;
    private OrthographicCamera camera;

    private ActionTrigger actionTrigger;

    private SpriteBatch batch;

    private Map map;

    private Texture textureMapBackground, textureCamera, textureSquare;

    public StrategyScreen(Map map)
    {
        this.map = map;

        float worldSize = Metrics.EARTH_EQUATOR_LENGTH_KM;

        float ratio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();

        viewport = new FitViewport(worldSize * ratio, worldSize);
        viewport.apply();

        batch = new SpriteBatch();

        actionTrigger = new ActionTrigger();

        Gdx.input.setInputProcessor(actionTrigger);

        initSampleTexture();

        map = new Map();
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

    private void updateCamera()
    {
        viewport.getCamera().update();

        batch.setProjectionMatrix(viewport.getCamera().combined);
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
        batch.begin();
        batch.draw(textureMapBackground, -viewport.getWorldWidth() / 2, -viewport.getWorldHeight() / 2, viewport.getWorldWidth(), viewport.getWorldHeight());//, Metrics.EARTH_EQUATOR_LENGTH_KM, Metrics.EARTH_EQUATOR_LENGTH_KM);
        batch.end();
    }

    private void drawClippingRect(Rectangle clipRect)
    {
        batch.begin();
        batch.draw(textureCamera, clipRect.x, clipRect.y, clipRect.width, clipRect.height);//, Metrics.EARTH_EQUATOR_LENGTH_KM, Metrics.EARTH_EQUATOR_LENGTH_KM);
        batch.end();
    }

    private void drawSquare(Rectangle object)
    {
        batch.begin();
        batch.draw(textureSquare, object.x, object.y, object.width, object.height);//, Metrics.EARTH_EQUATOR_LENGTH_KM, Metrics.EARTH_EQUATOR_LENGTH_KM);
        batch.end();
    }

    private void renderUI()
    {

    }

    private ArrayList<String> getNearKeys(Rectangle clipRect)
    {
        int kXMin = (int) (clipRect.x / Tile.size);
        int kYMin = (int) (clipRect.y / Tile.size);

        float kWidth = clipRect.width / Tile.size;
        float kHeight = clipRect.height / Tile.size;

        float kXMax = (int) (kXMin + kWidth);
        float kYMax = (int) (kYMin + kHeight);

        ArrayList<String> keys = new ArrayList<String>();

        for(int x = kXMin; x <= kXMax; x++)
        {
            for(int y = kYMin; y <= kYMax; y++)
            {
                String key = Tile.createKey(x, y);

                if(map.containsTile(key))
                {
                    keys.add(key);
                }
            }
        }

        return keys;
    }

    private ArrayList<Tile> nearTiles = new ArrayList<Tile>();

    // Returns all Tiles which are seen inside the given bounds on screen.
    // Helps to improve performance if there is a lot of game content.
    private ArrayList<Tile> getNearTiles(Rectangle clipRect)
    {
        nearTiles.clear();

        for(String key : getNearKeys(clipRect))
        {
            nearTiles.add(map.getTile(key));
        }

        return nearTiles;
    }

    private void renderMap(Rectangle clipRect)
    {
        if(map != null)
        {
            ArrayList<Tile> nearTiles = getNearTiles(clipRect);

            for(Tile tile : nearTiles)
            {
                tile.render(batch);
            }

            /*
            drawMapBackground();
            drawClippingRect(clipRect);

            Vector2 position = new Vector2(0,0);
            float size = 2000;

            Rectangle object = new Rectangle(position.x, position.y, size, size);

            if(!Clipping.isClippable(clipRect, object))
            {
                drawSquare(object);
            }
             */
        }
    }

    @Override
    public void render(float delta)
    {
        if(batch == null)
        {
            return;
        }

        Rectangle clipRect = Clipping.getClippingBounds(viewport, Settings.CLIPPING_BOUNDS.width, Settings.CLIPPING_BOUNDS.height);

        clearScreen();

        drawMapBackground();

        drawClippingRect(clipRect);

        renderMap(clipRect);

        renderUI();

        updateCamera();
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
