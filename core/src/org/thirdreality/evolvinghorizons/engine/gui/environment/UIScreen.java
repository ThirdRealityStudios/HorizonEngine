package org.thirdreality.evolvinghorizons.engine.gui.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.io.MouseUtility;
import org.thirdreality.evolvinghorizons.engine.render.RenderSource;
import org.thirdreality.evolvinghorizons.engine.render.Renderer;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;

import java.util.ArrayList;

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
    public GComponent getFocusedComponent()
    {
        GComponent firstMatch = null;

        for(GComponent selected : out)
        {
            boolean insideComponent = isFocusing(selected);

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
    public boolean isFocusing(GComponent target)
    {
        // If there is no component given or interaction is forbidden,
        // this method assumes no component was found,
        // pretending the cursor is not over a component.
        if(target == null || out == null || (target != null && !target.getLogic().isInteractionAllowed()))
        {
            return false;
        }

        Vector2 position = target.getStyle().getPosition();

        Rectangle componentBackground = new Rectangle(position.x, position.y, target.getStyle().getBounds().width, target.getStyle().getBounds().height);

        return componentBackground.contains(MouseUtility.getCurrentCursorLocation());
    }

    // Checks whether the cursor is over any GUInness component.
    // Should be avoided if used too often because of performance reasons.
    private boolean isFocusingAny(ArrayList<String> exceptionalTypes)
    {
        GComponent focused = getFocusedComponent();

        boolean assigned = focused != null;

        for(String type : exceptionalTypes)
        {
            if(assigned && (focused.getType().contentEquals(type)))
            {
                return false;
            }
        }

        return assigned;
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

    // Sets the zoom speed when zooming in / out on this screen.
    public void setZoomSpeed(float zoomSpeed)
    {
        uiScreenHandler.zoomSpeed = zoomSpeed;
    }

    public void setNavigationSpeed(float speed)
    {
        navigationSpeed = speed;
    }

    public void navigateByWASD(float delta)
    {
        float x = 0;
        float y = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            y += navigationSpeed;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            y -= navigationSpeed;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            x -= navigationSpeed;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            x += navigationSpeed;
        }

        x *= delta * uiScreenHandler.zoomSpeed;
        y *= delta * uiScreenHandler.zoomSpeed;

        RenderSource.orthographicCamera.position.x += x;
        RenderSource.orthographicCamera.position.y += y;
    }

    public void allowFocusOnZoom(boolean allow)
    {
        uiScreenHandler.allowFocusOnZoom = allow;
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