package org.thirdreality.horizonengine.core.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.thirdreality.horizonengine.core.game.Metrics;
import org.thirdreality.horizonengine.core.game.environment.Map;
import org.thirdreality.horizonengine.core.game.environment.Tile;
import org.thirdreality.horizonengine.core.game.object.action.ActionTrigger;
import org.thirdreality.horizonengine.core.tools.render.Clipping;

import java.util.ArrayList;

// The Screen which is used in-game (having loaded a game with a map).
public class StrategyScreen implements Screen
{
    private Viewport viewport;
    private OrthographicCamera camera;

    private ActionTrigger actionTrigger;

    private SpriteBatch batch;

    private Map map;

    private Texture textureMapBackground, textureCamera, textureSquare;

    private Vector3 origin;

    public StrategyScreen()
    {
        float worldSize = Metrics.EARTH_EQUATOR_LENGTH_KM;

        camera = new OrthographicCamera(worldSize, worldSize);

        viewport = new FitViewport(worldSize, worldSize, camera);
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
        pixmapMap.setColor(Color.GREEN);
        pixmapMap.fillRectangle(0,0,1, 1);

        textureMapBackground = new Texture(pixmapMap);


        Pixmap pixmapCamera = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pixmapCamera.setColor(Color.RED);
        pixmapCamera.fillRectangle(0,0,1, 1);

        textureCamera = new Texture(pixmapCamera);


        Pixmap pixmapSquare = new Pixmap(1,1, Pixmap.Format.RGB888);
        pixmapSquare.setColor(Color.BLUE);
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

    public ArrayList<Tile> getNearTiles(Viewport viewport)
    {


        return null;
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

    public void drawSquare(Rectangle object)
    {
        batch.begin();
        batch.draw(textureSquare, object.x, object.y, object.width, object.height);//, Metrics.EARTH_EQUATOR_LENGTH_KM, Metrics.EARTH_EQUATOR_LENGTH_KM);
        batch.end();
    }

    private void renderUI()
    {
        Rectangle clipRect = Clipping.getClippingBounds(viewport, 720, 400);

        drawMapBackground();
        drawClippingRect(clipRect);

        Vector2 position = new Vector2(0,0);
        float size = 2000;

        Rectangle object = new Rectangle(position.x, position.y, size, size);

        if(!Clipping.isClippable(clipRect, object))
        {
            drawSquare(object);
        }
    }

    private void clearScreen()
    {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render(float delta)
    {
        if(batch == null)
        {
            return;
        }

        clearScreen();

        map.render(viewport);

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
