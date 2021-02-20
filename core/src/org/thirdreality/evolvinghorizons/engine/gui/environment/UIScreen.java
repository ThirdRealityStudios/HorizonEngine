package org.thirdreality.evolvinghorizons.engine.gui.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
    private UIHandler UIHandler;
    private InputProcessor inputProcessor;

    private float zoomSpeed = 0;

    public UIScreen()
    {
        out = new GComponent[0];

        UIHandler = new UIHandler(this);

        inputProcessor = new InputProcessor()
        {
            @Override
            public boolean keyDown(int keycode)
            {
                return UIHandler.keyDown(keycode);
            }

            @Override
            public boolean keyUp(int keycode)
            {
                return UIHandler.keyUp(keycode);
            }

            @Override
            public boolean keyTyped(char character)
            {
                return UIHandler.keyTyped(character);
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button)
            {
                return UIHandler.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button)
            {
                return UIHandler.touchUp(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer)
            {
                return UIHandler.touchDragged(screenX, screenY, pointer);
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY)
            {
                return UIHandler.mouseMoved(screenX, screenY);
            }

            @Override
            public boolean scrolled(float amountX, float amountY)
            {
                float zoom = RenderSource.orthographicCamera.zoom + amountY * zoomSpeed;

                // Zoom level may not be below 100% as this causes render bugs.
                if(zoom >= 1)
                {
                    RenderSource.orthographicCamera.zoom = zoom;
                }

                return UIHandler.scrolled(amountX, amountY);
            }
        };
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

    public void setZoomSpeed(float zoomSpeed)
    {
        this.zoomSpeed = zoomSpeed;
    }

    @Override
    public void render(float delta)
    {
        Renderer.drawBlankScreen();

        drawAllComponents();
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(inputProcessor);
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
