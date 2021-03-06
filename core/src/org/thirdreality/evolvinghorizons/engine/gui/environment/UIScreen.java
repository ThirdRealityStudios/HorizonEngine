package org.thirdreality.evolvinghorizons.engine.gui.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.GPolyButton;
import org.thirdreality.evolvinghorizons.engine.render.RenderSource;
import org.thirdreality.evolvinghorizons.engine.render.Renderer;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;

public abstract class UIScreen implements Screen
{
    private static final long serialVersionUID = Meta.serialVersionUID;

    private GComponent[] out;
    private UIScreenHandler uiScreenHandler;

    // This is the navigation speed which is used to move up,down, to the left and to the right with the camera.
    private float navigationSpeed = 0;

    public UIScreen()
    {
        out = new GComponent[0];

        uiScreenHandler = new UIScreenHandler(this);
    }

    public void setComponents(GComponent[] components)
    {
        out = components;
    }

    // Returns the first component which is focused by the cursor.
    // Makes the UI more efficient by breaking at the first component already.
    // Returns null if there is no such component.
    public GComponent getFocusedComponent(int screenX, int screenY)
    {
        GComponent firstMatch = null;

        for(GComponent selected : out)
        {
            boolean insideComponent = isFocusing(screenX, screenY, selected);

            // Returns the first component which is focused by the mouse cursor.
            if(insideComponent)
            {
                // Make sure, if the component is ignored / unfocusable it is not recognized by its click or hover behavior.
                if(selected.getLogic().isFocusable())
                {
                    firstMatch = selected;
                }

                break;
            }

        }
        // Returns the first component which is focused by the mouse cursor.
        return firstMatch;
    }

    // Tests if the cursor is on the position of a component.
    // Meaning: Tests whether the mouse cursor (relative to the Display) is inside the given component.
    // Returns 'false' if target is 'null'.
    public boolean isFocusing(int screenX, int screenY, GComponent target)
    {
        // If there is no component given or interaction is forbidden,
        // this method assumes no component was found,
        // pretending the cursor is not over a component.
        if(target == null || out == null || (target != null && !target.getLogic().isInteractionAllowed()))
        {
            return false;
        }

        switch(target.getType())
        {
            case "polybutton":
            {
                GPolyButton polyButton = (GPolyButton) target;

                Polygon transformed = new Polygon(polyButton.getPolygon().getTransformedVertices());

                transformed.scale(RenderSource.orthographicCamera.zoom);

                System.out.println("zoom: " + RenderSource.orthographicCamera.zoom);

                return transformed.contains(screenX, screenY);
            }

            default:
            {
                return target.getStyle().getBounds().contains(screenX, screenY);
            }
        }
    }

    private void drawAllComponents()
    {
        for(GComponent component : out)
        {
            if(component != null)
            {
                Renderer.drawContext(component);
            }
        }
    }

    // Sets the zoom acceleration when zooming in / out on this screen.
    public void setZoomAcceleration(float zoomAcceleration)
    {
        uiScreenHandler.zoomAcceleration = zoomAcceleration;
    }

    public void setNavigationSpeed(float speed)
    {
        navigationSpeed = speed;
    }

    public void navigateByWASD(float delta)
    {
        float x = 0;
        float y = 0;

        float navigationSpeed = this.navigationSpeed * RenderSource.orthographicCamera.zoom * delta;

        if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            y += navigationSpeed;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            y -= navigationSpeed;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            x -= navigationSpeed;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            x += navigationSpeed;
        }

        x *= delta * uiScreenHandler.zoomAcceleration;
        y *= delta * uiScreenHandler.zoomAcceleration;

        RenderSource.orthographicCamera.position.x += x;
        RenderSource.orthographicCamera.position.y += y;
    }

    public void allowFocusOnZoom(boolean allow)
    {
        uiScreenHandler.allowFocusOnZoom = allow;
    }

    public Vector2 getCursorLocation()
    {
        return new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }

    // Tells you where the cursor points to, seen from the middle of the screen.
    public Vector2 getCursorDirection()
    {
        Vector2 cursor = getCursorLocation();

        float xDiff = Gdx.graphics.getWidth() / 2 - cursor.x;
        float yDiff = Gdx.graphics.getHeight() / 2 - cursor.y;

        return new Vector2(xDiff, yDiff);
    }

    @Override
    public void render(float delta)
    {
        uiScreenHandler.delta = delta;

        navigateByWASD(delta);

        Renderer.drawBlankScreen();

        drawAllComponents();
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(uiScreenHandler);
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose()
    {
        Gdx.input.setInputProcessor(null);
    }
}
