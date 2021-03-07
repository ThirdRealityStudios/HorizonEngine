package org.thirdreality.evolvinghorizons.engine.gui.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.GPolyButton;
import org.thirdreality.evolvinghorizons.engine.gui.layer.GLayer;
import org.thirdreality.evolvinghorizons.engine.render.RenderSource;
import org.thirdreality.evolvinghorizons.engine.render.Renderer;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;

import java.util.Arrays;

public abstract class UIScreen implements Screen
{
    private static final long serialVersionUID = Meta.serialVersionUID;

    protected GLayer[] layers;
    private UIScreenHandler uiScreenHandler;

    // This is the navigation speed which is used to move up,down, to the left and to the right with the camera.
    private float navigationSpeed = 0;

    public UIScreen()
    {
        layers = new GLayer[0];

        uiScreenHandler = new UIScreenHandler(this);
    }

    // Checks whether there is a layer in this UIScreen with the same priority.
    private boolean isDoublePriority(GLayer compared)
    {
        for(GLayer layer : layers)
        {
            if(layer.getPriority() == compared.getPriority() && !layer.equals(compared))
            {
                return true;
            }
        }

        return false;
    }

    private GLayer[] copy(GLayer[] array)
    {
        GLayer[] layers = new GLayer[array.length];

        for(int i = 0; i < layers.length; i++)
        {
            layers[i] = array[i];
        }

        return layers;
    }

    public boolean setLayers(GLayer[] source)
    {
        GLayer[] backup = layers;

        layers = copy(source);

        for(GLayer layer : layers)
        {
            if(isDoublePriority(layer))
            {
                layers = backup;

                System.out.println("DOUBLE PRIORITY!");

                // Tell the programmer something went wrong, meaning that (at least) two layers overlap each other.
                return false;
            }
        }

        // Sorts the layers by their priority.
        Arrays.sort(layers);

        return true;
    }

    // Returns the first component which is focused by the cursor.
    // Makes the UI more efficient by breaking at the first component already.
    // Returns null if there is no such component.
    public GComponent getFocusedComponent(int screenX, int screenY)
    {
        GComponent focused = null;

        for(GLayer layer : layers)
        {
            GComponent firstMatch = null;

            for(GComponent selected : layer.getComponents())
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

            if(firstMatch != null)
            {
                // Returns the first component which is focused by the mouse cursor.
                return firstMatch;
            }
        }

        // Returns nothing if of course no component was found.
        return null;
    }

    private Vector2 getProjectedCursor()
    {
        Vector3 projected = RenderSource.orthographicCamera.project(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        return new Vector2(projected.x, projected.y);
    }

    // Tests if the cursor is on the position of a component.
    // Meaning: Tests whether the mouse cursor (relative to the Display) is inside the given component.
    // Returns 'false' if target is 'null'.
    public boolean isFocusing(int screenX, int screenY, GComponent target)
    {
        // Only recalculate the cursor position on screen if the component is zoomed out / -in.
        if(target.isZoomable())
        {
            Vector3 vect = RenderSource.orthographicCamera.unproject(new Vector3(screenX, screenY, 0));

            screenX = (int) vect.x;
            screenY = (int) vect.y;
        }
        else
        {
            screenY = Gdx.graphics.getHeight() - screenY;
        }

        // If there is no component given or interaction is forbidden,
        // this method assumes no component was found,
        // pretending the cursor is not over a component.
        if(target == null || layers == null || (target != null && !target.getLogic().isInteractionAllowed()))
        {
            return false;
        }

        switch(target.getType())
        {
            case "polybutton":
            {
                GPolyButton polyButton = (GPolyButton) target;

                return new Polygon(polyButton.getPolygon().getTransformedVertices()).contains(screenX, screenY);
            }

            default:
            {
                return target.getStyle().getBounds().contains(screenX, screenY);
            }
        }
    }

    private void drawAllComponents(GComponent[] components)
    {
        for(GComponent component : components)
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

    public void navigateByKeyboard(float delta)
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

        navigateByKeyboard(delta);

        Renderer.drawBlankScreen();

        for(int i = layers.length - 1; i >= 0; i--)
        {
            GLayer layer = layers[i];

            //System.out.println("> " + layer.getPriority());
            drawAllComponents(layer.getComponents());
        }
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
