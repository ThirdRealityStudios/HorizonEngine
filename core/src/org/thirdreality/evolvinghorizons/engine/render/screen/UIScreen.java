package org.thirdreality.evolvinghorizons.engine.render.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.layer.GLayer;
import org.thirdreality.evolvinghorizons.engine.io.ComponentHandler;
import org.thirdreality.evolvinghorizons.engine.io.MouseUtility;
import org.thirdreality.evolvinghorizons.engine.render.RenderSource;
import org.thirdreality.evolvinghorizons.engine.render.Renderer;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.settings.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class UIScreen implements Screen
{
    private static final long serialVersionUID = Meta.serialVersionUID;

    private GComponent[] out;

    private ComponentHandler componentHandler;

    private boolean isRenderingActive = false;

    public UIScreen()
    {
        out = new GComponent[0];

        componentHandler = new ComponentHandler(this);
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

    @Override
    public void render(float delta)
    {
        Renderer.drawBlankScreen();

        drawAllComponents();
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(componentHandler);
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
