package org.thirdreality.evolvinghorizons.engine.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.gui.ColorScheme;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.image.GImage;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.path.GPath;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.rectangle.GRectangle;
import org.thirdreality.evolvinghorizons.engine.gui.component.input.textfield.GTextfield;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.checkbox.GCheckbox;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.tickbox.GTickBoxList;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.button.GButton;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.description.GDescription;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.polybutton.GPolyButton;
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
        if(c instanceof GImage)
        {
            drawImage(c);
        }
        else if(c instanceof GPolyButton)
        {
            drawPolyButton(c);
        }
        else if(c instanceof GDescription)
        {
            drawDescription(c);
        }
        else if(c instanceof GPath)
        {
            //drawPath(c);
        }
        else if(c instanceof GTextfield)
        {
            drawTextfield(c);
        }
        else if(c instanceof GCheckbox)
        {
            drawCheckbox(c);
        }
        else if(c instanceof GTickBoxList)
        {
            drawSelectionBox(c);
        }
        else if(c instanceof GRectangle)
        {
            drawRectangle(c);
        }
        else if(c instanceof GButton)
        {
            drawButton(c);
        }
    }

    @Deprecated
    // Will return the corresponding foreground color defined in the ColorScheme when there was no color defined in the component itself.
    // Uses: to get the correct color for the component when a user clicks it or hovers across it.
    private static Color getUpdatedForegroundColor(GComponent component)
    {
        if(component instanceof GButton)
        {
            GButton button = (GButton) component;

            return (button.getStyle().getColor() == null) ? ColorScheme.buttonFg : button.getStyle().getColor();
        }
        else if(component instanceof GTextfield)
        {
            GTextfield textfield = (GTextfield) component;

            return (textfield.getStyle().getColor() == null) ? ColorScheme.textfieldFg : textfield.getStyle().getColor();
        }
        else if(component instanceof GCheckbox)
        {
            GCheckbox checkbox = (GCheckbox) component;

            return (checkbox.getStyle().getColor() == null) ? ColorScheme.checkboxFg : checkbox.getStyle().getColor();
        }

        // No color found for the component type (unsupported).
        return null;
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
        GRectangle rectangle = (GRectangle) c;

        Rectangle rect = rectangle.getStyle().getBounds();

        RenderSource.getShapeRenderer(rectangle.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);

        RenderSource.getShapeRenderer(rectangle.isZoomable()).setColor(rectangle.getStyle().getColor());
        RenderSource.getShapeRenderer(rectangle.isZoomable()).rect(rect.x, rect.y, rect.width, rect.height);

        RenderSource.getShapeRenderer(rectangle.isZoomable()).end();
    }

    @Deprecated
    private static void drawDescription(GComponent c)
    {
        GDescription description = (GDescription) c;

        Vector2 position = new Vector2(description.getStyle().getBounds().x, description.getStyle().getBounds().y);

        Font font = description.getStyle().getFont();

        RenderSource.getSpriteBatch(c.isZoomable()).begin();
        font.getBitmapFont().draw(RenderSource.getSpriteBatch(c.isZoomable()), description.getText(), position.x + (description.getStyle().getBounds().width / 2 - description.getStyle().getGlyphLayout().width / 2), position.y + (description.getStyle().getBounds().height / 2 - description.getStyle().getFont().getBitmapFont().getData().xHeight / 2));
        RenderSource.getSpriteBatch(c.isZoomable()).end();
    }

    private static void drawImage(GComponent c)
    {
        GImage image = (GImage) c;

        // Represents simply the outer bounds of the component.
        Rectangle bounds = image.getStyle().getBounds();

        RenderSource.getSpriteBatch(image.isZoomable()).begin();
        RenderSource.getSpriteBatch(image.isZoomable()).draw(image.getStyle().getTextureRegion(), bounds.x, bounds.y, bounds.width, bounds.height);
        RenderSource.getSpriteBatch(image.isZoomable()).end();
    }

    // Needs to be updated with offset and scale ability from the Viewports settings.
    // Not working currently! Will be replaced soon by another better method which will just draw or fill polygons with multiple overlappings, intersections or joins.
    @Deprecated
    private static void drawPath(GComponent c){}

    @Deprecated
    private static void drawCheckbox(GComponent c)
    {
        GCheckbox checkbox = (GCheckbox) c;

        RenderSource.getShapeRenderer(checkbox.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);

        // Represents simply the outer bounds of the component.
        Rectangle background = new Rectangle(checkbox.getStyle().getBounds());

        {
            Vector2 bgPosition = new Vector2(background.x, background.y);

            background.setPosition(bgPosition);
        }

        float borderThicknessPx = checkbox.getStyle().getBorderProperties().getBorderThicknessPx();

        Rectangle foreground = new Rectangle(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.width - 2*borderThicknessPx);

        RenderSource.getShapeRenderer(checkbox.isZoomable()).setColor(ColorScheme.checkboxBg);
        RenderSource.getShapeRenderer(checkbox.isZoomable()).rect(background.x, background.y, background.width, background.width);

        RenderSource.getShapeRenderer(checkbox.isZoomable()).setColor(getUpdatedForegroundColor(c));
        RenderSource.getShapeRenderer(checkbox.isZoomable()).rect(foreground.x, foreground.y, foreground.width, foreground.width);

        RenderSource.getShapeRenderer(checkbox.isZoomable()).end();

        if(checkbox.isChecked())
        {
            Texture checkSymbol = checkbox.getStyle().getTextureRegion().getTexture();

            // Simply the square size of the image.
            // The image is saved with square dimensions,
            // so it doesn't matter if you take the width or height (see package core.gui.image.icon for "check_sign.png").
            float sizePx = foreground.width;

            RenderSource.getSpriteBatch(checkbox.isZoomable()).begin();
            RenderSource.getSpriteBatch(checkbox.isZoomable()).draw(checkSymbol, foreground.x, foreground.y, sizePx, sizePx);
            RenderSource.getSpriteBatch(checkbox.isZoomable()).end();
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

            Rectangle textBounds = selectionBox.getOption(i).getBounds();

            float x = textBounds.x;
            float y = textBounds.y + (textBounds.height + textBounds.height) / 2;

            RenderSource.getSpriteBatch(selectionBox.isZoomable()).begin();
            selectionBox.getStyle().getFont().getBitmapFont().draw(RenderSource.getSpriteBatch(c.isZoomable()), selectionBox.getText(i), x, y);
            RenderSource.getSpriteBatch(selectionBox.isZoomable()).end();

            float borderThicknessPx = selectionBox.getStyle().getBorderProperties().getBorderThicknessPx();

            // Simply the square size of the image.
            float sizePx = tickBox.width - 2*borderThicknessPx;

            Color hoverColor = selectionBox.getOption(i).getBackgroundColor();

            if(hoverColor != null)
            {
                RenderSource.getShapeRenderer(selectionBox.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);
                RenderSource.getShapeRenderer(selectionBox.isZoomable()).setColor(hoverColor);
                RenderSource.getShapeRenderer(selectionBox.isZoomable()).rect(tickBox.x + borderThicknessPx, tickBox.y + borderThicknessPx, sizePx, sizePx);
                RenderSource.getShapeRenderer(selectionBox.isZoomable()).end();
            }

            if(selectionBox.isSelected(i))
            {
                Texture tickSymbol = selectionBox.getStyle().getTextureRegion().getTexture();

                RenderSource.getSpriteBatch(selectionBox.isZoomable()).begin();
                RenderSource.getSpriteBatch(selectionBox.isZoomable()).draw(tickSymbol, tickBox.x + borderThicknessPx, tickBox.y + borderThicknessPx, sizePx, sizePx);
                RenderSource.getSpriteBatch(selectionBox.isZoomable()).end();
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

        Rectangle background = button.getStyle().getBounds();

        RenderSource.getShapeRenderer(c.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);
        RenderSource.getShapeRenderer(c.isZoomable()).setColor(ColorScheme.buttonBg);
        RenderSource.getShapeRenderer(c.isZoomable()).rect(background.x, background.y, background.width, background.height);

        float borderThicknessPx = button.getStyle().getBorderProperties().getBorderThicknessPx();

        float padding = button.getStyle().getPadding();

        Rectangle foreground = new Rectangle(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.height - 2*borderThicknessPx);

        RenderSource.getShapeRenderer(c.isZoomable()).setColor(getUpdatedForegroundColor(c));
        RenderSource.getShapeRenderer(c.isZoomable()).rect(foreground.x, foreground.y, foreground.width, foreground.height );
        RenderSource.getShapeRenderer(c.isZoomable()).end();

        Font font = button.getStyle().getFont();

        RenderSource.getSpriteBatch(c.isZoomable()).begin();
        font.getBitmapFont().draw(RenderSource.getSpriteBatch(c.isZoomable()), value, background.x + borderThicknessPx + padding, background.y + (background.height + button.getGlyphLayout().height) / 2);
        RenderSource.getSpriteBatch(c.isZoomable()).end();
    }

    @Deprecated
    private static void drawTextfield(GComponent c)
    {
        GTextfield textfield = (GTextfield) c;

        String value = textfield.getValue();

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

}
