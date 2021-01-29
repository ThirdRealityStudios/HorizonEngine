package org.thirdreality.evolvinghorizons.guinness.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.guinness.gui.Viewport;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.input.GTextfield;
import org.thirdreality.evolvinghorizons.guinness.gui.component.placeholder.GWindow;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.GCheckbox;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list.GSelectionBox;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list.GSelectionOption;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GButton;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GDescription;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;

import java.util.ArrayList;

public class Renderer
{
        private Vector2 offset;
        private Vector2 origin;

        private Pixmap displayDrawContent = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);

        private Texture t = new Texture(displayDrawContent);

        private Pixmap buttonBg, buttonFt;

        // Every design has its own draw method in order to know how to draw each component.
        // This is a "pre-defined method".
        // Also note! The Viewport given here is only used in order to check things like, whether the context is drawn in a GWindow etc.
        // Simulated viewports are hereby very restricted, especially if it's about the ability of whether a component is movable or not.
        // The draw adapter doesn't care then because this feature is only supported within the Displays Viewport.
        public static void drawContext(Viewport target, GComponent c)
        {
            // For the case there is an image supplied to the GComponent object,
            // it is considered to be rendered.
            // The programmer needs to know how to use the features GComponent delivers and has to ensure
            // a supplied image will not get in conflict with other settings.
            switch(c.getType())
            {
                case "image":
                {
                    drawImage(target, c);

                    // .end() is both called in drawImage(...)

                    break;
                }

                case "polybutton":
                {
                    //drawPolyButton(target, c);

                    break;
                }

                case "description":
                {
                    //drawDescription(target, c);

                    break;
                }

                case "path":
                {
                    //drawPath(target, c);

                    break;
                }

                case "textfield":
                {
                    drawTextfield(target, c);

                    // .end() is both called in drawTextfield(...)

                    break;
                }

                case "checkbox":
                {
                    drawCheckbox(target, c);

                    // .end() is both called in drawCheckbox(...)

                    break;
                }

                case "selectionbox":
                {
                    //drawSelectionBox(target, c);

                    break;
                }

                case "rectangle":
                {
                    drawRectangle(target, c);

                    // .end() is both called in drawRectangle(...)

                    break;
                }

                case "button":
                {
                    drawButton(target, c);

                    // .end() is both called in drawButton(...)

                    break;
                }

                case "window":
                {
                    //drawWindow(target, c);
                }

                default:
                {

                }
            }
        }

        @Deprecated
        private static void drawRectangle(Viewport viewport, GComponent c)
        {
            Rectangle rectangle = new Rectangle(c.getStyle().getBounds());

            if(rectangle != null)
            {
                // Every rectangle has by default its own color.
                Color fillColor = c.getStyle().getColor();

                Vector2 position = new Vector2(rectangle.x, rectangle.y).add(viewport.getOrigin());

                if(c.getStyle().isMovableForViewport())
                {
                    position.add(viewport.getOffset());
                }

                rectangle.setPosition(position);

                // Render the rectangle
                RenderSource.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                RenderSource.shapeRenderer.setColor(fillColor);
                RenderSource.shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                RenderSource.shapeRenderer.end();
            }
        }

        @Deprecated
        private static void drawDescription(Viewport viewport, GComponent c)
        {
            GDescription description = (GDescription) c;

            // Represents simply the outer bounds of the component.
            Rectangle bounds = description.getStyle().getBounds();

            //Point descLoc = new GIPoint(bounds.getLocation()).add(viewport.getOrigin().add(viewport.getOffset(, description.getStyle().isMovableForViewport()).add(getDesign().getPaddingProperty().getInnerThickness()).add(getDesign().getBorderProperty().getBorderThicknessPx()).mul(getScale(), description.getStyle().isScalableForViewport()).toPoint();

            Font original = description.getStyle().getFont();
            //Font scaledFont = //new org.thirdreality.evolvinghorizons.guinness.gui.font.Font(original.getName(), original.getFile().getAbsolutePath(), original.getFontSize());

            // Work on this! ! !
            // DrawToolkit.drawString(p, description.getTitle(), descLoc, scaledFont);
        }

        private static void drawImage(Viewport viewport, GComponent c)
        {
            // Represents simply the outer bounds of the component.
            Rectangle bounds = c.getStyle().getBounds();

            Vector2 position = new Vector2(bounds.x, bounds.y).add(viewport.getOrigin());

            if(c.getStyle().isMovableForViewport())
            {
                position.add(viewport.getOffset());
            }

            RenderSource.spriteBatch.begin();
            RenderSource.spriteBatch.draw(c.getStyle().getImage(), position.x, position.y, bounds.width, bounds.height);
            RenderSource.spriteBatch.end();
        }

        // Needs to be updated with offset and scale ability from the Viewports settings.
        // Not working currently! Will be replaced soon by another better method which will just draw or fill polygons with multiple overlappings, intersections or joins.
        @Deprecated
        private static void drawPath(Viewport viewport, GComponent c){}

        @Deprecated
        private static void drawCheckbox(Viewport viewport, GComponent c)
        {
            GCheckbox checkbox = (GCheckbox) c;

            // Represents simply the outer bounds of the component.
            Rectangle background = new Rectangle(c.getStyle().getBounds());

            {
                Vector2 bgPosition = new Vector2(background.x, background.y);
                bgPosition.add(viewport.getOrigin());

                if (c.getStyle().isMovableForViewport()) {
                    bgPosition.add(viewport.getOffset());
                }

                background.setPosition(bgPosition);
            }

            int borderThicknessPx = 1;

            Vector2 fgPosition = new Vector2(background.x, background.y).add(borderThicknessPx, borderThicknessPx);

            Rectangle foreground = new Rectangle(fgPosition.x, fgPosition.y, background.width - 2*borderThicknessPx, background.width - 2*borderThicknessPx);

            if(checkbox.isChecked())
            {
                Texture checkSymbol = c.getStyle().getImage();

                // Simply the square size of the image.
                // The image is saved with square dimensions,
                // so it doesn't matter if you take the width or height (see package core.gui.image.icon for "check_sign.png").
                int sizePx = (int) (checkSymbol.getWidth());

                //Point imgLoc = new GIPoint(locInner).add(getDesign().getBorderProperty().getBorderThicknessPx()).mul(getScale(), c.getStyle().isScalableForViewport()).toPoint();

                // Work on this ! ! !
                // p.drawImage(checkSymbol, imgLoc.x, imgLoc.y, sizePx, sizePx, null);
            }

            RenderSource.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            RenderSource.shapeRenderer.rect(background.x, background.y, background.width, background.width);
            RenderSource.shapeRenderer.rect(foreground.x, foreground.y, foreground.width, foreground.width);
            RenderSource.shapeRenderer.end();
        }

        @Deprecated
        // Work on this (text displaying)!
        private static void drawSelectionBox(Viewport viewport, GComponent c)
        {
            GSelectionBox selectionBox = (GSelectionBox) c;

            drawRectangle(viewport, selectionBox);

            ArrayList<Rectangle[]> shapeTable = selectionBox.getShapeTable();

            // Draws every single option from the GSelectionBox.
            for(int i = 0; i < shapeTable.size(); i++)
            {
                GSelectionOption option = selectionBox.getOptions().get(i);

                Rectangle optionShape = new Rectangle(shapeTable.get(i)[0]);
                Rectangle titleShape = new Rectangle(shapeTable.get(i)[2]);

                {
                    Vector2 positionOptionShape = new Vector2(optionShape.x, optionShape.y).add(viewport.getOrigin());

                    if (c.getStyle().isMovableForViewport())
                    {
                        positionOptionShape.add(viewport.getOffset());
                    }

                    // Move the options to the Viewport relative position.
                    optionShape.setPosition(positionOptionShape);
                }

                {
                    Vector2 positionTitleShape = new Vector2(titleShape.x, titleShape.y).add(viewport.getOrigin());

                    if (c.getStyle().isMovableForViewport())
                    {
                        positionTitleShape.add(viewport.getOffset());
                    }

                    // Move the options to the Viewport relative position.
                    titleShape.setPosition(positionTitleShape);
                }

                if(option.isChecked())
                {
                    // Work on this ! ! !
                    //g.drawImage(selectionBox.getIcons()[1], optionShape.getBounds().x, optionShape.getBounds().y, optionShape.getBounds().width, optionShape.getBounds().height, null);
                }
                else
                {
                    // Work on this ! ! !
                    //g.drawImage(selectionBox.getIcons()[0], optionShape.getBounds().x, optionShape.getBounds().y, optionShape.getBounds().width, optionShape.getBounds().height, null);
                }

                // Every option can have a background color..
                Color optionColor = option.getStyle().getColor();

                // But if there is no background color, then no background will just be drawn..
                if(optionColor != null)
                {
                    //displayDrawContent.setColor(optionColor);
                    //displayDrawContent.fillRectangle(titleShape.getBounds().x, titleShape.getBounds().y, titleShape.width, titleShape.height);
                }

                Font original = c.getStyle().getFont();
               // Font scaledFont = new Font(original.getName(), original.getFile().getAbsolutePath(), original.getFontSize());

                // Work on this ! ! !
                // DrawToolkit.drawString(g, option.getValue(), titleShape.getBounds().getLocation(), scaledFont);
            }
        }

        /*
        @Deprecated
        protected static void drawPolyButton(Viewport viewport, GComponent c)
        {
            GPolyButton polyButton = (GPolyButton) c;

            Polygon look = polyButton.getStyle().getPrimaryLook();

            // Represents simply the outer bounds of the component.
            Rectangle bounds = look.getBounds();

            //displayDrawContent.setColor(polyButton.getStyle().getPrimaryColor());

            Point buttonLoc = new GIPoint(bounds.getLocation()).add(viewport.getOrigin().add(viewport.getOffset(, polyButton.getStyle().isMovableForViewport()).toPoint();

            // Here it is only working with a copy in order not to modify the original object (polygon).
            Polygon transformedCopy = ShapeTransform.scalePolygon(ShapeTransform.movePolygonTo(look, buttonLoc), scale);

            // Work on this ! ! !
            //g.fillPolygon(transformedCopy);

            // If text should be displayed in the center of the component.
            if(polyButton.getStyle().getTextAlign() == 1)
            {
                int textLength = polyButton.getStyle().getFont().getFontSize() * polyButton.getTitle().length();

                int centerX = bounds.getLocation().x + bounds.width / 2 - textLength / 2;
                int centerY = bounds.getLocation().y + bounds.height / 2 - polyButton.getStyle().getFont().getFontSize() / 2;

                Point loc = new GIPoint(centerX, centerY).add(polyButton.getStyle().getTextTransition()).add(viewport.getOffset(, polyButton.getStyle().isMovableForViewport()).mul(getScale(), polyButton.getStyle().isScalableForViewport()).toPoint();

                org.thirdreality.evolvinghorizons.guinness.gui.font.Font original = polyButton.getStyle().getFont();
                org.thirdreality.evolvinghorizons.guinness.gui.font.Font scaledFont = new Font(original.getName(), original.getFile().getAbsolutePath(), (int) (original.getFontSize() * scale));

                // Work on this ! ! !
                //DrawToolkit.drawString(g, polyButton.getTitle(), loc, scaledFont);
            }
            else // If text should be displayed normally (upper-left corner of the component).
            {
                Point loc = new GIPoint(bounds.getLocation()).add(polyButton.getStyle().getTextTransition()).add(viewport.getOffset(), polyButton.getStyle().isMovableForViewport()).toPoint();

                // Work on this ! ! !
                //DrawToolkit.drawString(g, polyButton.getTitle(), loc, polyButton.getStyle().getFont());
            }
        }
         */

        private static void drawButton(Viewport viewport, GComponent component)
        {
            GButton button = (GButton) component;

            String value = button.getTitle();

            syncViewportPosition(viewport, component);

            Vector2 position = new Vector2(button.getStyle().getBounds().getX(), button.getStyle().getBounds().getY());

            Rectangle background = button.createBoundsAt(position);

            RenderSource.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            RenderSource.shapeRenderer.setColor(ColorScheme.buttonBg);
            RenderSource.shapeRenderer.rect(background.x, background.y, background.width, background.height);

            float borderThickness = button.getStyle().getBorderProperties().getBorderThicknessPx();

            Rectangle foreground = new Rectangle(background.x + borderThickness, background.y + borderThickness, background.width - 2*borderThickness, background.height - 2*borderThickness);

            RenderSource.shapeRenderer.setColor(ColorScheme.buttonFg);
            RenderSource.shapeRenderer.rect(foreground.x, foreground.y, foreground.width, foreground.height);
            RenderSource.shapeRenderer.end();

            //float fontSize = textfield.getStyle().getFont().getFontSize();

            RenderSource.spriteBatch.begin();
            button.getStyle().getFont().getBitmapFont().draw(RenderSource.spriteBatch, value, foreground.x + button.getStyle().getPadding(), foreground.y + button.getGlyphLayout().height + button.getStyle().getPadding());
            RenderSource.spriteBatch.end();
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

            RenderSource.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            RenderSource.shapeRenderer.setColor(ColorScheme.buttonBg);
            RenderSource.shapeRenderer.rect(background.x, background.y, background.width, background.height);

            // Use the color defined in the scheme when there is no specific color set directly by the button.
            if(component.getStyle().getColor() == null)
            {
                RenderSource.shapeRenderer.setColor(ColorScheme.buttonFg);
            }
            else
            {
                RenderSource.shapeRenderer.setColor(component.getStyle().getColor());
            }

            RenderSource.shapeRenderer.rect(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.height - 2*borderThicknessPx);
            RenderSource.shapeRenderer.end();
             */
        }

        private static void syncViewportPosition(Viewport viewport, GComponent component)
        {
            Vector2 position = new Vector2(component.getStyle().getPosition());

            if(viewport.getOrigin() != component.getOrigin())
            {
                component.setOrigin(viewport);

                position.add(viewport.getOrigin().sub(component.getOrigin()));
            }

            if(component.getStyle().isMovableForViewport())
            {
                if(viewport.getOffset() != component.getOffset())
                {
                    component.setOffset(viewport);

                    position.add(viewport.getOffset().sub(component.getOffset()));
                }
            }

            component.getStyle().getBounds().setPosition(position);
        }

        @Deprecated
        private static void drawTextfield(Viewport viewport, GComponent component)
        {
            GTextfield textfield = (GTextfield) component;

            String value = textfield.getInputValue();

            syncViewportPosition(viewport, component);

            Vector2 position = new Vector2(textfield.getStyle().getBounds().getX(), textfield.getStyle().getBounds().getY());

            Rectangle background = textfield.createBoundsAt(position);

            RenderSource.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            RenderSource.shapeRenderer.setColor(ColorScheme.textfieldBg);
            RenderSource.shapeRenderer.rect(background.x, background.y, background.width, background.height);

            float borderThicknessPx = textfield.getStyle().getBorderProperties().getBorderThicknessPx();

            float padding = textfield.getStyle().getPadding();

            Rectangle foreground = new Rectangle(background.x + borderThicknessPx, background.y + borderThicknessPx, background.width - 2*borderThicknessPx, background.height - 2*borderThicknessPx);

            RenderSource.shapeRenderer.setColor(ColorScheme.textfieldFg);
            RenderSource.shapeRenderer.rect(foreground.x, foreground.y, foreground.width, foreground.height );
            RenderSource.shapeRenderer.end();

            Font font = textfield.getStyle().getFont();

            RenderSource.spriteBatch.begin();
            font.getBitmapFont().draw(RenderSource.spriteBatch, value, background.x + (background.width / 2 - textfield.getGlyphLayout().width / 2), background.y + (background.height / 2 - textfield.getStyle().getFont().getBitmapFont().getData().xHeight / 2));
            RenderSource.spriteBatch.end();
        }

        @Deprecated
        public void drawWindow(Viewport viewport, GComponent c)
        {
            GWindow window = (GWindow) c;
        }

}
