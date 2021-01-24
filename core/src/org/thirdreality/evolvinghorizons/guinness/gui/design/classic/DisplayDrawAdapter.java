package org.thirdreality.evolvinghorizons.guinness.gui.design.classic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.thirdreality.evolvinghorizons.guinness.draw.DrawToolkit;
import org.thirdreality.evolvinghorizons.guinness.feature.GIDimension;
import org.thirdreality.evolvinghorizons.guinness.feature.GIPoint;
import org.thirdreality.evolvinghorizons.guinness.feature.shape.ShapeMaker;
import org.thirdreality.evolvinghorizons.guinness.feature.shape.ShapeTransform;
import org.thirdreality.evolvinghorizons.guinness.gui.Viewport;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.decoration.GRectangle;
import org.thirdreality.evolvinghorizons.guinness.gui.component.input.GTextfield;
import org.thirdreality.evolvinghorizons.guinness.gui.component.placeholder.GWindow;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.GCheckbox;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list.GSelectionBox;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list.GSelectionOption;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GButton;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GDescription;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GPolyButton;
import org.thirdreality.evolvinghorizons.guinness.gui.design.Design;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;

public class DisplayDrawAdapter
{
	private Point offset;
	
	private Point origin;

	private float scale;

	private Design design;

	private Pixmap displayDrawContent = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);

	private Texture t = new Texture(displayDrawContent);

	private SpriteBatch batch;

	public DisplayDrawAdapter(Design design)
	{
		this.design = design;

		batch = new SpriteBatch();
	}

	// Every design has its own draw method in order to know how to draw each component.
	// This is a "pre-defined method".
	// Also note! The Viewport given here is only used in order to check things like, whether the context is drawn in a GWindow etc.
	// Simulated viewports are hereby very restricted, especially if it's about the ability of whether a component is movable or not.
	// The draw adapter doesn't care then because this feature is only supported within the Displays Viewport.
	public void drawContext(Viewport target, GComponent c, Point origin, Point offset, float scale)
	{
		this.offset = offset;
		this.origin = origin;
		this.scale = scale;

		batch.begin();

		// For the case there is an image supplied to the GComponent object,
		// it is considered to be rendered.
		// The programmer needs to know how to use the features GComponent delivers and has to ensure
		// a supplied image will not get in conflict with other settings.
		switch(c.getType())
		{
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

			case "image":
			{
				drawImage(c);

				break;
			}

			case "path":
			{
				drawPath(c);

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
				drawWindow(c);
			}

			default:
			{
				
			}
		}

		//batch.draw(t, 0, 0);
		batch.end();
	}

	@Deprecated
	private void drawRectangle(GComponent c)
	{
		// A GRectangle can do more than a usual GComponent.
		// You can define border-radiuses and more.
		if(c.getType().contentEquals("rectangle"))
		{
			GRectangle rect = (GRectangle) c;

			// Polygon rectangle = ShapeMaker.createRectangle(rect.getStyle().getLook().getBounds().getLocation(), rect.getStyle().getLook().getBounds().getSize());
			Polygon rectangle = ShapeMaker.createRectangleFrom(rect.getStyle().getPrimaryLook().getBounds(), rect.getStyle().getBorderProperties());

			displayDrawContent.setColor(rect.getStyle().getPrimaryColor() == null ? Color.BLACK : rect.getStyle().getPrimaryColor());

			// Uses the correct scale depending on whether Viewport scaling is generally wanted by the component.
			float scale = c.getStyle().isScalableForViewport() ? this.scale : 1f;
			
			Point rectLoc = new GIPoint(rectangle.getBounds().getLocation()).add(getOrigin()).add(getOffset(), c.getStyle().isMovableForViewport()).mul(scale).toPoint();

			if(rectangle.getBounds() != null)
			{
				// Replace method!
				//p.fillPolygon(ShapeTransform.movePolygonTo(ShapeTransform.scalePolygon(rectangle, scale), rectLoc));
			}
		}
		// If it's not a GRectangle just draw the shape if there is one. Anyway, you can do less things here..
		else if(c.getStyle().getPrimaryLook() != null)
		{
			Rectangle shape = c.getStyle().getPrimaryLook().getBounds();

			displayDrawContent.setColor(c.getStyle().getPrimaryColor() == null ? Color.BLACK : c.getStyle().getPrimaryColor());
			
			// Uses the correct scale depending on whether Viewport scaling is generally wanted by the component.
			float scale = c.getStyle().isScalableForViewport() ? this.scale : 1f;
			
			Point rectLoc = new GIPoint(shape.getBounds().getLocation()).add(getOrigin()).add(getOffset(), c.getStyle().isMovableForViewport()).mul(scale).toPoint();
			
			if(shape != null)
			{
				displayDrawContent.fillRectangle(rectLoc.x, rectLoc.y, (int) (shape.width * scale), (int) (shape.height * scale));
			}
		}
	}

	@Deprecated
	private void drawDescription(GComponent c)
	{
		GDescription description = (GDescription) c;
		
		// Represents simply the outer bounds of the component.
		Rectangle bounds = description.getStyle().getPrimaryLook().getBounds();
		
		Point descLoc = new GIPoint(bounds.getLocation()).add(getOrigin()).add(getOffset(), description.getStyle().isMovableForViewport()).add(getDesign().getPaddingProperty().getInnerThickness()).add(getDesign().getBorderProperty().getBorderThicknessPx()).mul(getScale(), description.getStyle().isScalableForViewport()).toPoint();
		
		Font original = description.getStyle().getFont();
		Font scaledFont = new Font(original.getName(), original.getFile().getAbsolutePath(), (int) (original.getFontSize() * scale));

		// Work on this! ! !
		// DrawToolkit.drawString(p, description.getTitle(), descLoc, scaledFont);
	}

	@Deprecated
	private void drawImage(GComponent c)
	{
		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getPrimaryLook().getBounds();

		// Uses the correct scale depending on whether Viewport scaling is generally wanted by the component.
		float scale = c.getStyle().isScalableForViewport() ? this.scale : 1f;

		Point imgLoc = new GIPoint(bounds.getLocation()).add(getOrigin()).add(getOffset(), c.getStyle().isMovableForViewport()).mul(scale).toPoint();

		batch.draw(c.getStyle().getImage(), imgLoc.x, imgLoc.y, (int) (bounds.width * scale), (int) (bounds.height * scale));

		// Work on this!
		//p.drawImage(c.getStyle().getImage(), imgLoc.x, imgLoc.y, (int) (bounds.width * scale), (int) (bounds.height * scale), null);
	}
	
	// Needs to be updated with offset and scale ability from the Viewports settings.
	// Not working currently! Will be replaced soon by another better method which will just draw or fill polygons with multiple overlappings, intersections or joins.
	@Deprecated
	private void drawPath(GComponent c)
	{
		// Dead code
		/*
		GPath path = (GPath) c;

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(path.getStyle().getPrimaryColor());

		if(path.isFill())
		{
			g2d.fill(path.getPath());
		}
		else
		{
			g2d.draw(path.getPath());
		}
		*/
	}

	@Deprecated
	private void drawCheckbox(GComponent c)
	{
		GCheckbox checkbox = (GCheckbox) c;

		// Represents simply the outer bounds of the component.
		Rectangle bounds = c.getStyle().getPrimaryLook().getBounds();
		
		// It wouldn't matter if you use 'height' or 'width' because both values are the same.
		Dimension outerSize = new Dimension(bounds.width, bounds.width);
		Dimension innerSize = new Dimension(outerSize.width - getDesign().getPaddingProperty().getInnerThickness(), outerSize.width - getDesign().getPaddingProperty().getInnerThickness());
		
		Point locOuter = new GIPoint(bounds.getLocation()).add(getOrigin()).add(getOffset(), c.getStyle().isMovableForViewport()).toPoint();
		Point locInner = new GIPoint(locOuter).add(getDesign().getBorderProperty().getBorderThicknessPx()).toPoint();
		
		
		
		Point locOuterScaled = new GIPoint(locOuter).mul(getScale(), c.getStyle().isScalableForViewport()).toPoint();
		Point locInnerScaled = new GIPoint(locInner).mul(getScale(), c.getStyle().isScalableForViewport()).toPoint();

		Dimension outerSizeScaled = new GIDimension(outerSize).mul(getScale(), c.getStyle().isScalableForViewport());
		Dimension innerSizeScaled = new GIDimension(innerSize).mul(getScale(), c.getStyle().isScalableForViewport());

		displayDrawContent.setColor(getDesign().getDesignColor().getBorderColor());

		displayDrawContent.fillRectangle(locOuterScaled.x, locOuterScaled.y, outerSizeScaled.width, outerSizeScaled.width);

		displayDrawContent.setColor(Color.WHITE);

		displayDrawContent.fillRectangle(locInnerScaled.x, locInnerScaled.y, innerSizeScaled.width, innerSizeScaled.width);
		
		if(checkbox.isChecked())
		{
			Texture checkSymbol = c.getStyle().getImage();

			// Simply the square size of the image.
			// The image is saved with square dimensions,
			// so it doesn't matter if you take the width or height (see package core.gui.image.icon for "check_sign.png").
			int sizePx = (int) (checkSymbol.getWidth());

			if(c.getStyle().isScalableForViewport())
			{
				sizePx *= getScale();
			}

			Point imgLoc = new GIPoint(locInner).add(getDesign().getBorderProperty().getBorderThicknessPx()).mul(getScale(), c.getStyle().isScalableForViewport()).toPoint();

			// Work on this ! ! !
			// p.drawImage(checkSymbol, imgLoc.x, imgLoc.y, sizePx, sizePx, null);
		}
	}

	@Deprecated
	// Work on this (text displaying)!
	private void drawSelectionBox(GComponent c)
	{		
		GSelectionBox selectionBox = (GSelectionBox) c;

		drawRectangle(selectionBox);

		ArrayList<Polygon[]> shapeTable = selectionBox.getShapeTable();

		// Draws every single option from the GSelectionBox.
		for(int i = 0; i < shapeTable.size(); i++)
		{
			GSelectionOption option = selectionBox.getOptions().get(i);

			Polygon optionShape = new Polygon(shapeTable.get(i)[0].xpoints, shapeTable.get(i)[0].ypoints, shapeTable.get(i)[0].npoints);
			Polygon titleShape = new Polygon(shapeTable.get(i)[2].xpoints, shapeTable.get(i)[2].ypoints, shapeTable.get(i)[2].npoints);

			// Move the options to the Viewport relative position.
			optionShape = ShapeTransform.movePolygonTo(optionShape, new GIPoint(optionShape.getBounds().getLocation()).add(getOrigin()).add(getOffset(), c.getStyle().isMovableForViewport()).toPoint());
			titleShape = ShapeTransform.movePolygonTo(titleShape, new GIPoint(titleShape.getBounds().getLocation()).add(getOrigin()).add(getOffset(), c.getStyle().isMovableForViewport()).toPoint());

			if(c.getStyle().isScalableForViewport())
			{
				optionShape = ShapeTransform.scalePolygon(optionShape, getScale());
				titleShape = ShapeTransform.scalePolygon(titleShape, getScale());
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
			Color optionColor = option.getStyle().getPrimaryColor();

			int titleShapeWidth = titleShape.getBounds().width;
			int titleShapeHeight = titleShape.getBounds().height;
			
			// But if there is no background color, then no background will just be drawn..
			if(optionColor != null)
			{
				displayDrawContent.setColor(optionColor);
				displayDrawContent.fillRectangle(titleShape.getBounds().x, titleShape.getBounds().y, titleShapeWidth, titleShapeHeight);
			}
			
			Font original = c.getStyle().getFont();
			Font scaledFont = new Font(original.getName(), original.getFile().getAbsolutePath(), (int) (original.getFontSize() * scale));

			// Work on this ! ! !
			// DrawToolkit.drawString(g, option.getValue(), titleShape.getBounds().getLocation(), scaledFont);
		}
	}

	@Deprecated
	protected void drawPolyButton(GComponent c)
	{
		GPolyButton polyButton = (GPolyButton) c;
		
		Polygon look = polyButton.getStyle().getPrimaryLook();
		
		// Represents simply the outer bounds of the component.
		Rectangle bounds = look.getBounds();

		displayDrawContent.setColor(polyButton.getStyle().getPrimaryColor());

		Point buttonLoc = new GIPoint(bounds.getLocation()).add(getOrigin()).add(getOffset(), polyButton.getStyle().isMovableForViewport()).toPoint();

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

			Point loc = new GIPoint(centerX, centerY).add(polyButton.getStyle().getTextTransition()).add(getOffset(), polyButton.getStyle().isMovableForViewport()).mul(getScale(), polyButton.getStyle().isScalableForViewport()).toPoint();
			
			Font original = polyButton.getStyle().getFont();
			Font scaledFont = new Font(original.getName(), original.getFile().getAbsolutePath(), (int) (original.getFontSize() * scale));

			// Work on this ! ! !
			//DrawToolkit.drawString(g, polyButton.getTitle(), loc, scaledFont);
		}
		else // If text should be displayed normally (upper-left corner of the component).
		{
			Point loc = new GIPoint(bounds.getLocation()).add(polyButton.getStyle().getTextTransition()).add(getOffset(), polyButton.getStyle().isMovableForViewport()).mul(getScale(), polyButton.getStyle().isScalableForViewport()).toPoint();

			// Work on this ! ! !
			//DrawToolkit.drawString(g, polyButton.getTitle(), loc, polyButton.getStyle().getFont());
		}
	}
	
	protected void drawButton(GComponent component)
	{		
		GButton button = (GButton) component;
		
		Color temp = getDesign().getDesignColor().getBorderColor();

		getDesign().getDesignColor().setBorderColor(button.getStyle().getPrimaryColor().mul(0.8f).mul(0.8f));
		
		String value = button.getTitle();
		
		drawGeneralField(button, value, value.length());
		
		getDesign().getDesignColor().setBorderColor(temp);
	}
	
	protected void drawTextfield(GComponent component)
	{
		GTextfield textfield = (GTextfield) component;
		
		String value = textfield.getInputValue();
		
		drawGeneralField(textfield, value, textfield.getValueManager().getMaxLength());
	}

	// WOrk on this ! ! !
	protected void drawGeneralField(GComponent c, String value, int maxLength)
	{
		Polygon background = c.getStyle().getPrimaryLook();
		
		Point backgroundLoc = new GIPoint(background.getBounds().getLocation()).add(getOrigin()).add(getOffset(), c.getStyle().isMovableForViewport()).toPoint();

		background = ShapeTransform.movePolygonTo(background, backgroundLoc);

		if(c.getStyle().isScalableForViewport())
		{
			background = ShapeTransform.scalePolygon(background, getScale());
		}

		displayDrawContent.setColor(getDesign().getDesignColor().getBorderColor());

		// WOrk on this ! ! !
		//g.fillPolygon(background);



		Dimension frontDimension = new GIDimension(maxLength * c.getStyle().getFont().getFontSize(), c.getStyle().getFont().getFontSize()).add(2*getDesign().getPaddingProperty().getInnerThickness());

		Rectangle frontRectangle = new Rectangle(new GIPoint(backgroundLoc).add(getDesign().getBorderProperty().getBorderThicknessPx()).toPoint(), frontDimension);

		Polygon front = ShapeMaker.createRectangleFrom(frontRectangle, c.getStyle().getBorderProperties());

		front = ShapeTransform.scalePolygon(front, c.getStyle().isScalableForViewport() ? getScale() : 1f);

		displayDrawContent.setColor(c.getStyle().getPrimaryColor());

		// WOrk on this ! ! !
		//g.fillPolygon(front);



		Point text = new GIPoint(backgroundLoc).add(getDesign().getBorderProperty().getBorderThicknessPx()).add(getDesign().getPaddingProperty().getInnerThickness()).mul(getScale(), c.getStyle().isScalableForViewport()).toPoint();

		DrawToolkit.drawString(value, text, c.getStyle().getFont().getScaledFont(c.getStyle().isScalableForViewport() ? getScale() : 1f));
	}

	@Deprecated
	public void drawWindow(GComponent c)
	{
		GWindow window = (GWindow) c;

		/*
		 * The GWindow currently only supports offsets yet delivered by the corresponding Viewport.
		 */
		
		// Draws the outer part of the window, including offset and scale by Viewport of course.
		{
			displayDrawContent.setColor(window.getFrameColor());

			Point windowOuterMoved = new GIPoint(c.getStyle().getPrimaryLook().getBounds().getLocation()).add(getOffset(), window.getStyle().isMovableForViewport()).toPoint();

			Polygon movedByOffset = ShapeTransform.movePolygonTo(c.getStyle().getPrimaryLook(), windowOuterMoved);

			/*
			 * When scaling is wanted again, you can implement it here again with this code snippet..
			 * 
			 * Polygon scaledByViewport = ShapeTransform.scalePolygon(movedByOffset, window.getStyle().isScalableForViewport() ? getScale() : 1f);
			 * 
			 * g.fillPolygon(scaledByViewport);
			 */

			// Work on this!
			//g.fillPolygon(movedByOffset);
		}

		// Draws the inner part of the window, including offset and scale by Viewport of course.
		{
			displayDrawContent.setColor(Color.BLACK);
			
			Point secondaryLookMoved = new GIPoint(c.getStyle().getSecondaryLook().getBounds().getLocation()).add(getOffset(), window.getStyle().isMovableForViewport()).toPoint();
			
			Polygon movedByOffset = ShapeTransform.movePolygonTo(c.getStyle().getSecondaryLook(), secondaryLookMoved);

			// Work on this!
			// g.fillPolygon(movedByOffset);
		}

		// Draws the window title
		{
			int borderTopMargin = window.getStyle().getBorderProperties().getBorderThicknessPx() + window.getTitleAreaHeightPx() / 2 - window.getStyle().getFont().getFontSize() / 2;
			
			int borderLeftMargin = window.getStyle().getBorderProperties().getBorderThicknessPx();
			
			GIPoint titlePosition = new GIPoint(c.getStyle().getPrimaryLook().getBounds().getLocation()).add(getOffset(), window.getStyle().isMovableForViewport());		
			
			titlePosition = titlePosition.addY(borderTopMargin).addX(borderLeftMargin);
			
			// titlePosition.mul(getScale(), window.getStyle().isScalableForViewport());
			
			DrawToolkit.drawString(window.getTitle(), titlePosition.toPoint(), window.getStyle().getFont());// window.getStyle().isScalableForViewport() ? window.getStyle().getFont().getScaledFont(getScale()) : window.getStyle().getFont());
		}

		{
			Point exitButtonMoved_Loc = new GIPoint(window.getExitButton().getStyle().getLocation()).add(getOffset(), window.getStyle().isMovableForViewport()).toPoint();
			
			Polygon exitButtonMoved = ShapeTransform.movePolygonTo(window.getExitButton().getStyle().getPrimaryLook(), exitButtonMoved_Loc);
			
			// Polygon exitButtonScaled = ShapeTransform.scalePolygon(exitButtonMoved, window.getStyle().isScalableForViewport() ? getScale() : 1f);

			displayDrawContent.setColor(window.getExitButton().getStyle().getPrimaryColor());

			// Work on this!
			//g.fillPolygon(exitButtonMoved);
		}
		
		{	
			Point minimizeButtonMoved_Loc = new GIPoint(window.getMinimizeButton().getStyle().getLocation()).add(getOffset(), window.getStyle().isMovableForViewport()).toPoint();
			
			Polygon minimizeButtonMoved = ShapeTransform.movePolygonTo(window.getMinimizeButton().getStyle().getPrimaryLook(), minimizeButtonMoved_Loc);
			
			// Polygon minimizeButtonScaled = ShapeTransform.scalePolygon(minimizeButtonMoved, window.getStyle().isScalableForViewport() ? getScale() : 1f);

			displayDrawContent.setColor(window.getMinimizeButton().getStyle().getPrimaryColor());

			// Work on this!
			//g.fillPolygon(minimizeButtonMoved);
		}
	}

	public Design getDesign()
	{
		return design;
	}
	
	public void setDesign(Design design)
	{
		this.design = design;
	}
	
	public Point getOrigin()
	{
		return origin;
	}

	public Point getOffset()
	{
		return offset;
	}

	public float getScale()
	{
		return scale;
	}
}
