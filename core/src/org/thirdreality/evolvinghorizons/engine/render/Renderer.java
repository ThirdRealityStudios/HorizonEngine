package org.thirdreality.evolvinghorizons.engine.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.gui.ColorScheme;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.input.GTextfield;
import org.thirdreality.evolvinghorizons.engine.gui.component.placeholder.GWindow;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.GCheckbox;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.GTickBoxList;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.GButton;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.GDescription;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.GPolyButton;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class Renderer
{
    private Vector2 offset;
    private Vector2 origin;

    // Every design has its own draw method in order to know how to draw each component.
    // This is a "pre-defined method".
    // Also note! The Viewport given here is only used in order to check things like, whether the context is drawn in a GWindow etc.
    // Simulated viewports are hereby very restricted, especially if it's about the ability of whether a component is movable or not.
    // The draw adapter doesn't care then because this feature is only supported within the Displays Viewport.
    public static void drawContext(GComponent c)
    {
        // For the case there is an image supplied to the GComponent object,
        // it is considered to be rendered.
        // The programmer needs to know how to use the features GComponent delivers and has to ensure
        // a supplied image will not get in conflict with other settings.
        switch(c.getType())
        {
            case "image":
            {
                drawImage(c);

                break;
            }

            case "polybutton":
            {
                drawPolyButton(c);

                break;
            }

            case "description":
            {
                drawDescription(c);

                break;
            }

            case "path":
            {
                //drawPath(c);

                break;
            }

            case "textfield":
            {
                drawTextfield(c);

                break;
            }

            case "checkbox":
            {
                drawCheckbox(c);

                break;
            }

            case "selectionbox":
            {
                drawSelectionBox(c);

                break;
            }

            case "rectangle":
            {
                drawRectangle(c);

                break;
            }

            case "button":
            {
                drawButton(c);

                break;
            }

            case "window":
            {
                //drawWindow(c);
            }

            default:
            {

            }
        }
    }

    @Deprecated
    // Will return the corresponding foreground color defined in the ColorScheme when there was no color defined in the component itself.
    // Uses: to get the correct color for the component when a user clicks it or hovers across it.
    private static Color getUpdatedForegroundColor(GComponent component)
    {
        Color componentFg = null;

        switch(component.getType())
        {
            case "button":
            {
                componentFg = ColorScheme.buttonFg;

                break;
            }

            case "textfield":
            {
                componentFg = ColorScheme.textfieldFg;

                break;
            }

            case "checkbox":
            {
                componentFg = ColorScheme.checkboxFg;

                break;
            }
        }

        return (component.getStyle().getColor() == null) ? componentFg : component.getStyle().getColor();
    }

    // In the beginning this will just draw a blank screen which will erase the content of the last render cycle.
    public static void drawBlankScreen()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Deprecated
    private static void drawRectangle(GComponent c)
    {
        Rectangle rect = new Rectangle(c.getStyle().getBounds());

        RenderSource.getPixmap().drawRectangle((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height);
        RenderSource.getTexture().draw(RenderSource.getPixmap(), 0, 0);
    }

    @Deprecated
    private static void drawDescription(GComponent c)
    {
        GDescription description = (GDescription) c;

        Vector2 position = new Vector2(description.getStyle().getBounds().getX(), description.getStyle().getBounds().getY());

        description.getStyle().setBounds(description.createBoundsAt(position));

        Font font = description.getStyle().getFont();

        RenderSource.getSpriteBatch().begin();
        font.getBitmapFont().draw(RenderSource.getSpriteBatch(), description.getText(), description.getStyle().getBounds().x + (description.getStyle().getBounds().width / 2 - description.getGlyphLayout().width / 2), description.getStyle().getBounds().y + (description.getStyle().getBounds().height / 2 - description.getStyle().getFont().getBitmapFont().getData().xHeight / 2));
        RenderSource.getSpriteBatch().end();
    }

    private static void drawImage(GComponent c)
    {
        // Represents simply the outer bounds of the component.
        Rectangle bounds = c.getStyle().getBounds();

        RenderSource.getSpriteBatch().begin();
        RenderSource.getSpriteBatch().draw(c.getStyle().getImage(), bounds.x, bounds.y, bounds.width, bounds.height);
        RenderSource.getSpriteBatch().end();
    }

    // Needs to be updated with offset and scale ability from the Viewports settings.
    // Not working currently! Will be replaced soon by another better method which will just draw or fill polygons with multiple overlappings, intersections or joins.
    @Deprecated
    private static void drawPath(GComponent c){}

    @Deprecated
    private static void drawCheckbox(GComponent c)
    {
        GCheckbox checkbox = (GCheckbox) c;

        RenderSource.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);

        // Represents simply the outer bounds of the component.
        Rectangle background = new Rectangle(c.getStyle().getBounds());

        {
            Vector2 bgPosition = new Vector2(background.x, background.y);

            background.setPosition(bgPosition);
        }

        float borderThicknessPx = c.getStyle().getBorderProperties().getBorderThicknessPx();

        Rectangle foreground = new Rectangle(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.width - 2*borderThicknessPx);

        RenderSource.getShapeRenderer().setColor(ColorScheme.checkboxBg);
        RenderSource.getShapeRenderer().rect(background.x, background.y, background.width, background.width);

        RenderSource.getShapeRenderer().setColor(getUpdatedForegroundColor(c));
        RenderSource.getShapeRenderer().rect(foreground.x, foreground.y, foreground.width, foreground.width);

        RenderSource.getShapeRenderer().end();

        if(checkbox.isChecked())
        {
            Texture checkSymbol = c.getStyle().getImage();

            // Simply the square size of the image.
            // The image is saved with square dimensions,
            // so it doesn't matter if you take the width or height (see package core.gui.image.icon for "check_sign.png").
            float sizePx = foreground.width;

            RenderSource.getSpriteBatch().begin();
            RenderSource.getSpriteBatch().draw(checkSymbol, foreground.x, foreground.y, sizePx, sizePx);
            RenderSource.getSpriteBatch().end();
        }
    }

    private static void drawRectangle(Rectangle bounds, float borderThicknessPx)
    {
        RenderSource.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        RenderSource.getShapeRenderer().setColor(ColorScheme.selectionBoxBg);
        RenderSource.getShapeRenderer().rect(bounds.x, bounds.y, bounds.width, bounds.height);
        RenderSource.getShapeRenderer().end();

        Rectangle foreground = new Rectangle(bounds.x + borderThicknessPx, bounds.y + borderThicknessPx, bounds.width - 2*borderThicknessPx, bounds.height - 2*borderThicknessPx);

        RenderSource.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        RenderSource.getShapeRenderer().setColor(ColorScheme.selectionBoxFg);
        RenderSource.getShapeRenderer().rect(foreground.x, foreground.y, foreground.width, foreground.height);
        RenderSource.getShapeRenderer().end();
    }

    @Deprecated
    // Work on this (text displaying)!
    private static void drawSelectionBox(GComponent c)
    {
        GTickBoxList selectionBox = (GTickBoxList) c;

        drawRectangle(selectionBox.getStyle().getBounds(), selectionBox.getStyle().getBorderProperties().getBorderThicknessPx());

        for(int i = 0; i < selectionBox.size(); i++)
        {
            Rectangle tickBox = selectionBox.getOption(i).getTickBox();

            drawRectangle(tickBox, selectionBox.getStyle().getBorderProperties().getBorderThicknessPx());

            Rectangle textBounds = selectionBox.getOption(i).getTextBox();

            float x = textBounds.x;
            float y = textBounds.y + (textBounds.height + textBounds.height) / 2;

            RenderSource.getSpriteBatch().begin();
            selectionBox.getStyle().getFont().getBitmapFont().draw(RenderSource.getSpriteBatch(), selectionBox.getText(i), x, y);
            RenderSource.getSpriteBatch().end();

            float borderThicknessPx = selectionBox.getStyle().getBorderProperties().getBorderThicknessPx();

            // Simply the square size of the image.
            float sizePx = tickBox.width - 2*borderThicknessPx;

            Color hoverColor = selectionBox.getOption(i).getBackgroundColor();

            if(hoverColor != null)
            {
                RenderSource.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
                RenderSource.getShapeRenderer().setColor(hoverColor);
                RenderSource.getShapeRenderer().rect(tickBox.x + borderThicknessPx, tickBox.y + borderThicknessPx, sizePx, sizePx);
                RenderSource.getShapeRenderer().end();
            }

            if(selectionBox.isSelected(i))
            {
                Texture tickSymbol = c.getStyle().getImage();

                RenderSource.getSpriteBatch().begin();
                RenderSource.getSpriteBatch().draw(tickSymbol, tickBox.x + borderThicknessPx, tickBox.y + borderThicknessPx, sizePx, sizePx);
                RenderSource.getSpriteBatch().end();
            }
        }

    }

    @Deprecated
    protected static void drawPolyButton(GComponent c)
    {
        GPolyButton polyButton = (GPolyButton) c;

        RenderSource.getPolygonSpriteBatch().begin();
        polyButton.getPolygonSprite().draw(RenderSource.getPolygonSpriteBatch());
        RenderSource.getPolygonSpriteBatch().end();
    }

    private static void drawButton(GComponent component)
    {
        GButton button = (GButton) component;

        String value = button.getTitle();

        Vector2 position = new Vector2(button.getStyle().getBounds().getX(), button.getStyle().getBounds().getY());

        Rectangle background = button.createBoundsAt(position);

        RenderSource.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        RenderSource.getShapeRenderer().setColor(ColorScheme.buttonBg);
        RenderSource.getShapeRenderer().rect(background.x, background.y, background.width, background.height);

        float borderThicknessPx = button.getStyle().getBorderProperties().getBorderThicknessPx();

        float padding = button.getStyle().getPadding();

        Rectangle foreground = new Rectangle(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.height - 2*borderThicknessPx);

        RenderSource.getShapeRenderer().setColor(getUpdatedForegroundColor(component));
        RenderSource.getShapeRenderer().rect(foreground.x, foreground.y, foreground.width, foreground.height );
        RenderSource.getShapeRenderer().end();

        //float fontSize = textfield.getStyle().getFont().getFontSize();

        Font font = button.getStyle().getFont();

        RenderSource.getSpriteBatch().begin();
        font.getBitmapFont().draw(RenderSource.getSpriteBatch(), value, background.x + borderThicknessPx + padding, background.y + (background.height + button.getGlyphLayout().height) / 2);
        RenderSource.getSpriteBatch().end();
            /*
            GButton button = (GButton) component;

            String value = button.getTitle();

            Rectangle background = new Rectangle(component.getStyle().getBounds());

            Vector2 position = new Vector2(background.x, background.y);
            position.add(viewport.getOrigin());

            if(component.getStyle().isMovableForViewport())
            {
                position.add(viewport.getOffset());
            }

            int borderThicknessPx = 1;

            RenderSource.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
            RenderSource.getShapeRenderer().setColor(ColorScheme.buttonBg);
            RenderSource.getShapeRenderer().rect(background.x, background.y, background.width, background.height);

            // Use the color defined in the scheme when there is no specific color set directly by the button.
            if(component.getStyle().getColor() == null)
            {
                RenderSource.getShapeRenderer().setColor(ColorScheme.buttonFg);
            }
            else
            {
                RenderSource.getShapeRenderer().setColor(component.getStyle().getColor());
            }

            RenderSource.getShapeRenderer().rect(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.height - 2*borderThicknessPx);
            RenderSource.getShapeRenderer().end();
             */
    }

    @Deprecated
    private static void drawTextfield(GComponent component)
    {
        GTextfield textfield = (GTextfield) component;

        String value = textfield.getInputValue();

        Vector2 position = new Vector2(textfield.getStyle().getBounds().getX(), textfield.getStyle().getBounds().getY());

        Rectangle background = textfield.getStyle().getBounds();

        RenderSource.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        RenderSource.getShapeRenderer().setColor(ColorScheme.textfieldBg);
        RenderSource.getShapeRenderer().rect(background.x, background.y, background.width, background.height);

        float borderThicknessPx = textfield.getStyle().getBorderProperties().getBorderThicknessPx();

        float padding = textfield.getStyle().getPadding();

        Rectangle foreground = new Rectangle(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.height - 2*borderThicknessPx);

        RenderSource.getShapeRenderer().setColor(ColorScheme.textfieldFg);
        RenderSource.getShapeRenderer().rect(foreground.x, foreground.y, foreground.width, foreground.height );
        RenderSource.getShapeRenderer().end();

        Font font = textfield.getStyle().getFont();

        RenderSource.getSpriteBatch().begin();
        font.getBitmapFont().draw(RenderSource.getSpriteBatch(), value, background.x + borderThicknessPx + padding, background.y + (background.height + textfield.getGlyphLayout().height) / 2);
        RenderSource.getSpriteBatch().end();
    }

    @Deprecated
    public void drawWindow(GComponent c)
    {
        GWindow window = (GWindow) c;
    }

}
