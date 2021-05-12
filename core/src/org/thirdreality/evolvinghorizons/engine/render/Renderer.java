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
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.tickbox.field.GTickBoxField;
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
            drawImage((GImage) c);
        }
        else if(c instanceof GPolyButton)
        {
            drawPolyButton((GPolyButton) c);
        }
        else if(c instanceof GDescription)
        {
            drawDescription((GDescription) c);
        }
        else if(c instanceof GPath)
        {
            //drawPath((GPath) c);
        }
        else if(c instanceof GTextfield)
        {
            drawTextfield((GTextfield) c);
        }
        else if(c instanceof GCheckbox)
        {
            drawCheckbox((GCheckbox) c);
        }
        else if(c instanceof GTickBoxList)
        {
            drawTickBoxList((GTickBoxList) c);
        }
        else if(c instanceof GRectangle)
        {
            drawRectangle((GRectangle) c);
        }
        else if(c instanceof GButton)
        {
            drawButton((GButton) c);
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

    private static void drawRectangle(GRectangle rectangle)
    {
        Rectangle rect = rectangle.getStyle().getBounds();

        RenderSource.getShapeRenderer(rectangle.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);

        RenderSource.getShapeRenderer(rectangle.isZoomable()).setColor(rectangle.getStyle().getColor());
        RenderSource.getShapeRenderer(rectangle.isZoomable()).rect(rect.x, rect.y, rect.width, rect.height);

        RenderSource.getShapeRenderer(rectangle.isZoomable()).end();
    }

    private static void drawDescription(GDescription description)
    {
        Vector2 position = new Vector2(description.getStyle().getBounds().x, description.getStyle().getBounds().y);

        Font font = description.getStyle().getFont();

        RenderSource.getSpriteBatch(description.isZoomable()).begin();
        font.getBitmapFont().draw(RenderSource.getSpriteBatch(description.isZoomable()), description.getText(), position.x, position.y); //+ (description.getStyle().getBounds().width / 2 - description.getStyle().getGlyphLayout().width / 2), position.y + (description.getStyle().getBounds().height / 2 - description.getStyle().getFont().getBitmapFont().getData().xHeight / 2));
        RenderSource.getSpriteBatch(description.isZoomable()).end();
    }

    private static void drawImage(GImage image)
    {
        // Represents simply the outer bounds of the component.
        Rectangle bounds = image.getStyle().getBounds();

        RenderSource.getSpriteBatch(image.isZoomable()).begin();
        RenderSource.getSpriteBatch(image.isZoomable()).draw(image.getStyle().getTextureRegion(), bounds.x, bounds.y, bounds.width, bounds.height);
        RenderSource.getSpriteBatch(image.isZoomable()).end();
    }

    // Needs to be updated with offset and scale ability from the Viewports settings.
    // Not working currently! Will be replaced soon by another better method which will just draw or fill polygons with multiple overlappings, intersections or joins.
    @Deprecated
    private static void drawPath(GPath path){}

    @Deprecated
    private static void drawCheckbox(GCheckbox checkbox)
    {
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

        RenderSource.getShapeRenderer(checkbox.isZoomable()).setColor(getUpdatedForegroundColor(checkbox));
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

    private static void drawTickBoxList(GTickBoxList tickBoxList)
    {
        drawRectangle(tickBoxList.getBackground());

        for(int i = 0; i < tickBoxList.size(); i++)
        {
            GCheckbox checkbox = tickBoxList.getOption(i).getCheckbox();

            drawCheckbox(checkbox);

            GTickBoxField option = tickBoxList.getOption(i);

            float checkboxSize = option.getCheckbox().getStyle().getBounds().width;
            float tickBoxPadding = tickBoxList.getStyle().getPadding();

            Vector2 textPosition = new Vector2(option.getStyle().getBounds().getX(), option.getStyle().getBounds().getY() + option.getStyle().getBounds().height);
            textPosition.x += checkboxSize + tickBoxPadding;

            drawDescription(tickBoxList.getOption(i).getDescription());

            //RenderSource.getSpriteBatch(tickBoxList.isZoomable()).begin();
            //tickBoxList.getStyle().getFont().getBitmapFont().draw(RenderSource.getSpriteBatch(tickBoxList.isZoomable()), tickBoxList.getOption(i).getDescription(), textPosition.x, textPosition.y);
            //RenderSource.getSpriteBatch(tickBoxList.isZoomable()).end();

            /*
            float borderThicknessPx = tickBoxList.getStyle().getBorderProperties().getBorderThicknessPx();

            // Simply the square size of the image.
            float sizePx = tickBox.width - 2*borderThicknessPx;

            Color hoverColor = tickBoxList.getOption(i).getBackgroundColor();

            if(hoverColor != null)
            {
                RenderSource.getShapeRenderer(tickBoxList.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);
                RenderSource.getShapeRenderer(tickBoxList.isZoomable()).setColor(hoverColor);
                RenderSource.getShapeRenderer(tickBoxList.isZoomable()).rect(tickBox.x + borderThicknessPx, tickBox.y + borderThicknessPx, sizePx, sizePx);
                RenderSource.getShapeRenderer(tickBoxList.isZoomable()).end();
            }

            if(tickBoxList.isSelected(i))
            {
                Texture tickSymbol = tickBoxList.getStyle().getTextureRegion().getTexture();

                RenderSource.getSpriteBatch(tickBoxList.isZoomable()).begin();
                RenderSource.getSpriteBatch(tickBoxList.isZoomable()).draw(tickSymbol, tickBox.x + borderThicknessPx, tickBox.y + borderThicknessPx, sizePx, sizePx);
                RenderSource.getSpriteBatch(tickBoxList.isZoomable()).end();
            }

             */
        }
    }

    @Deprecated
    protected static void drawPolyButton(GPolyButton polyButton)
    {
        PolygonSpriteBatch polygonSpriteBatch = RenderSource.getPolygonSpriteBatch(polyButton.isZoomable());

        polygonSpriteBatch.begin();
        polyButton.getUpdatedPolygonSprite().draw(polygonSpriteBatch);
        polygonSpriteBatch.end();
    }

    private static void drawButton(GButton button)
    {
        String value = button.getTitle();

        Rectangle background = button.getStyle().getBounds();

        RenderSource.getShapeRenderer(button.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);
        RenderSource.getShapeRenderer(button.isZoomable()).setColor(ColorScheme.buttonBg);
        RenderSource.getShapeRenderer(button.isZoomable()).rect(background.x, background.y, background.width, background.height);

        float borderThicknessPx = button.getStyle().getBorderProperties().getBorderThicknessPx();

        float padding = button.getStyle().getPadding();

        Rectangle foreground = new Rectangle(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.height - 2*borderThicknessPx);

        RenderSource.getShapeRenderer(button.isZoomable()).setColor(getUpdatedForegroundColor(button));
        RenderSource.getShapeRenderer(button.isZoomable()).rect(foreground.x, foreground.y, foreground.width, foreground.height );
        RenderSource.getShapeRenderer(button.isZoomable()).end();

        Font font = button.getStyle().getFont();

        RenderSource.getSpriteBatch(button.isZoomable()).begin();
        font.getBitmapFont().draw(RenderSource.getSpriteBatch(button.isZoomable()), value, background.x + borderThicknessPx + padding, background.y + (background.height + button.getGlyphLayout().height) / 2);
        RenderSource.getSpriteBatch(button.isZoomable()).end();
    }

    @Deprecated
    private static void drawTextfield(GTextfield textfield)
    {
        String value = textfield.getValue();

        Rectangle background = textfield.getStyle().getBounds();

        RenderSource.getShapeRenderer(textfield.isZoomable()).begin(ShapeRenderer.ShapeType.Filled);
        RenderSource.getShapeRenderer(textfield.isZoomable()).setColor(ColorScheme.textfieldBg);
        RenderSource.getShapeRenderer(textfield.isZoomable()).rect(background.x, background.y, background.width, background.height);

        float borderThicknessPx = textfield.getStyle().getBorderProperties().getBorderThicknessPx();

        float padding = textfield.getStyle().getPadding();

        Rectangle foreground = new Rectangle(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.height - 2*borderThicknessPx);

        RenderSource.getShapeRenderer(textfield.isZoomable()).setColor(ColorScheme.textfieldFg);
        RenderSource.getShapeRenderer(textfield.isZoomable()).rect(foreground.x, foreground.y, foreground.width, foreground.height);
        RenderSource.getShapeRenderer(textfield.isZoomable()).end();

        Font font = textfield.getStyle().getFont();

        RenderSource.getSpriteBatch(textfield.isZoomable()).begin();
        font.getBitmapFont().draw(RenderSource.getSpriteBatch(textfield.isZoomable()), value, background.x + borderThicknessPx + padding, background.y + (background.height + textfield.getGlyphLayout().height) / 2);
        RenderSource.getSpriteBatch(textfield.isZoomable()).end();
    }

}
