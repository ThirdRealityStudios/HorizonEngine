package org.thirdreality.evolvinghorizons.engine.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
        Rectangle rect = c.getStyle().getBounds();

        RenderSource.getShapeRenderer(c.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);

        RenderSource.getShapeRenderer(c.isZoomable()).setColor(c.getStyle().getColor());
        RenderSource.getShapeRenderer(c.isZoomable()).rect(rect.x, rect.y, rect.width, rect.height);

        RenderSource.getShapeRenderer(c.isZoomable()).end();
    }

    @Deprecated
    private static void drawDescription(GComponent c)
    {
        GDescription description = (GDescription) c;

        Vector2 position = new Vector2(description.getStyle().getBounds().getX(), description.getStyle().getBounds().getY());

        description.getStyle().setBounds(description.createBoundsAt(position));

        Font font = description.getStyle().getFont();

        RenderSource.getSpriteBatch(c.isZoomable()).begin();
        font.getBitmapFont().draw(RenderSource.getSpriteBatch(c.isZoomable()), description.getText(), description.getStyle().getBounds().x + (description.getStyle().getBounds().width / 2 - description.getGlyphLayout().width / 2), description.getStyle().getBounds().y + (description.getStyle().getBounds().height / 2 - description.getStyle().getFont().getBitmapFont().getData().xHeight / 2));
        RenderSource.getSpriteBatch(c.isZoomable()).end();
    }

    private static void drawImage(GComponent c)
    {
        // Represents simply the outer bounds of the component.
        Rectangle bounds = c.getStyle().getBounds();

        RenderSource.getSpriteBatch(c.isZoomable()).begin();
        RenderSource.getSpriteBatch(c.isZoomable()).draw(c.getStyle().getTextureRegion(), bounds.x, bounds.y, bounds.width, bounds.height);
        RenderSource.getSpriteBatch(c.isZoomable()).end();
    }

    // Needs to be updated with offset and scale ability from the Viewports settings.
    // Not working currently! Will be replaced soon by another better method which will just draw or fill polygons with multiple overlappings, intersections or joins.
    @Deprecated
    private static void drawPath(GComponent c){}

    @Deprecated
    private static void drawCheckbox(GComponent c)
    {
        GCheckbox checkbox = (GCheckbox) c;

        RenderSource.getShapeRenderer(c.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);

        // Represents simply the outer bounds of the component.
        Rectangle background = new Rectangle(c.getStyle().getBounds());

        {
            Vector2 bgPosition = new Vector2(background.x, background.y);

            background.setPosition(bgPosition);
        }

        float borderThicknessPx = c.getStyle().getBorderProperties().getBorderThicknessPx();

        Rectangle foreground = new Rectangle(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.width - 2*borderThicknessPx);

        RenderSource.getShapeRenderer(c.isZoomable()).setColor(ColorScheme.checkboxBg);
        RenderSource.getShapeRenderer(c.isZoomable()).rect(background.x, background.y, background.width, background.width);

        RenderSource.getShapeRenderer(c.isZoomable()).setColor(getUpdatedForegroundColor(c));
        RenderSource.getShapeRenderer(c.isZoomable()).rect(foreground.x, foreground.y, foreground.width, foreground.width);

        RenderSource.getShapeRenderer(c.isZoomable()).end();

        if(checkbox.isChecked())
        {
            Texture checkSymbol = c.getStyle().getTextureRegion().getTexture();

            // Simply the square size of the image.
            // The image is saved with square dimensions,
            // so it doesn't matter if you take the width or height (see package core.gui.image.icon for "check_sign.png").
            float sizePx = foreground.width;

            RenderSource.getSpriteBatch(c.isZoomable()).begin();
            RenderSource.getSpriteBatch(c.isZoomable()).draw(checkSymbol, foreground.x, foreground.y, sizePx, sizePx);
            RenderSource.getSpriteBatch(c.isZoomable()).end();
        }
    }

    private static void drawRectangle(Rectangle bounds, float borderThicknessPx, boolean isZoomable)
    {
        RenderSource.getShapeRenderer(isZoomable).begin(ShapeRenderer.ShapeType.Filled);
        RenderSource.getShapeRenderer(isZoomable).setColor(ColorScheme.selectionBoxBg);
        RenderSource.getShapeRenderer(isZoomable).rect(bounds.x, bounds.y, bounds.width, bounds.height);
        RenderSource.getShapeRenderer(isZoomable).end();

        Rectangle foreground = new Rectangle(bounds.x + borderThicknessPx, bounds.y + borderThicknessPx, bounds.width - 2*borderThicknessPx, bounds.height - 2*borderThicknessPx);

        RenderSource.getShapeRenderer(isZoomable).begin(ShapeRenderer.ShapeType.Filled);
        RenderSource.getShapeRenderer(isZoomable).setColor(ColorScheme.selectionBoxFg);
        RenderSource.getShapeRenderer(isZoomable).rect(foreground.x, foreground.y, foreground.width, foreground.height);
        RenderSource.getShapeRenderer(isZoomable).end();
    }

    @Deprecated
    // Work on this (text displaying)!
    private static void drawSelectionBox(GComponent c)
    {
        GTickBoxList selectionBox = (GTickBoxList) c;

        drawRectangle(selectionBox.getStyle().getBounds(), selectionBox.getStyle().getBorderProperties().getBorderThicknessPx(), c.isZoomable());

        for(int i = 0; i < selectionBox.size(); i++)
        {
            Rectangle tickBox = selectionBox.getOption(i).getTickBox();

            drawRectangle(tickBox, selectionBox.getStyle().getBorderProperties().getBorderThicknessPx(), c.isZoomable());

            Rectangle textBounds = selectionBox.getOption(i).getTextBox();

            float x = textBounds.x;
            float y = textBounds.y + (textBounds.height + textBounds.height) / 2;

            RenderSource.getSpriteBatch(c.isZoomable()).begin();
            selectionBox.getStyle().getFont().getBitmapFont().draw(RenderSource.getSpriteBatch(c.isZoomable()), selectionBox.getText(i), x, y);
            RenderSource.getSpriteBatch(c.isZoomable()).end();

            float borderThicknessPx = selectionBox.getStyle().getBorderProperties().getBorderThicknessPx();

            // Simply the square size of the image.
            float sizePx = tickBox.width - 2*borderThicknessPx;

            Color hoverColor = selectionBox.getOption(i).getBackgroundColor();

            if(hoverColor != null)
            {
                RenderSource.getShapeRenderer(c.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);
                RenderSource.getShapeRenderer(c.isZoomable()).setColor(hoverColor);
                RenderSource.getShapeRenderer(c.isZoomable()).rect(tickBox.x + borderThicknessPx, tickBox.y + borderThicknessPx, sizePx, sizePx);
                RenderSource.getShapeRenderer(c.isZoomable()).end();
            }

            if(selectionBox.isSelected(i))
            {
                Texture tickSymbol = c.getStyle().getTextureRegion().getTexture();

                RenderSource.getSpriteBatch(c.isZoomable()).begin();
                RenderSource.getSpriteBatch(c.isZoomable()).draw(tickSymbol, tickBox.x + borderThicknessPx, tickBox.y + borderThicknessPx, sizePx, sizePx);
                RenderSource.getSpriteBatch(c.isZoomable()).end();
            }
        }
    }

    @Deprecated
    protected static void drawPolyButton(GComponent c)
    {
        GPolyButton polyButton = (GPolyButton) c;

        PolygonSpriteBatch polygonSpriteBatch = RenderSource.getPolygonSpriteBatch(c.isZoomable());

        polygonSpriteBatch.begin();
        polyButton.getUpdatedPolygonSprite().draw(polygonSpriteBatch);
        polygonSpriteBatch.end();
    }

    private static void drawButton(GComponent c)
    {
        GButton button = (GButton) c;

        String value = button.getTitle();

        Vector2 position = new Vector2(button.getStyle().getBounds().getX(), button.getStyle().getBounds().getY());

        Rectangle background = button.createBoundsAt(position);

        RenderSource.getShapeRenderer(c.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);
        RenderSource.getShapeRenderer(c.isZoomable()).setColor(ColorScheme.buttonBg);
        RenderSource.getShapeRenderer(c.isZoomable()).rect(background.x, background.y, background.width, background.height);

        float borderThicknessPx = button.getStyle().getBorderProperties().getBorderThicknessPx();

        float padding = button.getStyle().getPadding();

        Rectangle foreground = new Rectangle(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.height - 2*borderThicknessPx);

        RenderSource.getShapeRenderer(c.isZoomable()).setColor(getUpdatedForegroundColor(c));
        RenderSource.getShapeRenderer(c.isZoomable()).rect(foreground.x, foreground.y, foreground.width, foreground.height );
        RenderSource.getShapeRenderer(c.isZoomable()).end();

        //float fontSize = textfield.getStyle().getFont().getFontSize();

        Font font = button.getStyle().getFont();

        RenderSource.getSpriteBatch(c.isZoomable()).begin();
        font.getBitmapFont().draw(RenderSource.getSpriteBatch(c.isZoomable()), value, background.x + borderThicknessPx + padding, background.y + (background.height + button.getGlyphLayout().height) / 2);
        RenderSource.getSpriteBatch(c.isZoomable()).end();
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

            RenderSource.getShapeRenderer(c.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);
            RenderSource.getShapeRenderer(c.isZoomable()).setColor(ColorScheme.buttonBg);
            RenderSource.getShapeRenderer(c.isZoomable()).rect(background.x, background.y, background.width, background.height);

            // Use the color defined in the scheme when there is no specific color set directly by the button.
            if(component.getStyle().getColor() == null)
            {
                RenderSource.getShapeRenderer(c.isZoomable()).setColor(ColorScheme.buttonFg);
            }
            else
            {
                RenderSource.getShapeRenderer(c.isZoomable()).setColor(component.getStyle().getColor());
            }

            RenderSource.getShapeRenderer(c.isZoomable()).rect(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.height - 2*borderThicknessPx);
            RenderSource.getShapeRenderer(c.isZoomable()).end();
             */
    }

    @Deprecated
    private static void drawTextfield(GComponent c)
    {
        GTextfield textfield = (GTextfield) c;

        String value = textfield.getInputValue();

        Vector2 position = new Vector2(textfield.getStyle().getBounds().getX(), textfield.getStyle().getBounds().getY());

        Rectangle background = textfield.getStyle().getBounds();

        RenderSource.getShapeRenderer(c.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);
        RenderSource.getShapeRenderer(c.isZoomable()).setColor(ColorScheme.textfieldBg);
        RenderSource.getShapeRenderer(c.isZoomable()).rect(background.x, background.y, background.width, background.height);

        float borderThicknessPx = textfield.getStyle().getBorderProperties().getBorderThicknessPx();

        float padding = textfield.getStyle().getPadding();

        Rectangle foreground = new Rectangle(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.height - 2*borderThicknessPx);

        RenderSource.getShapeRenderer(c.isZoomable()).setColor(ColorScheme.textfieldFg);
        RenderSource.getShapeRenderer(c.isZoomable()).rect(foreground.x, foreground.y, foreground.width, foreground.height );
        RenderSource.getShapeRenderer(c.isZoomable()).end();

        Font font = textfield.getStyle().getFont();

        RenderSource.getSpriteBatch(c.isZoomable()).begin();
        font.getBitmapFont().draw(RenderSource.getSpriteBatch(c.isZoomable()), value, background.x + borderThicknessPx + padding, background.y + (background.height + textfield.getGlyphLayout().height) / 2);
        RenderSource.getSpriteBatch(c.isZoomable()).end();
    }

    @Deprecated
    public void drawWindow(GComponent c)
    {
        GWindow window = (GWindow) c;
    }

}
