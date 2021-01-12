package org.thirdreality.evolvinghorizons.guinness.gui.design;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.feature.shape.ShapeMaker;
import org.thirdreality.evolvinghorizons.guinness.gui.Viewport;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.input.GTextfield;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GButton;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GDescription;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GPolyButton;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.property.GBorderProperty;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.property.GPaddingProperty;
import org.thirdreality.evolvinghorizons.guinness.gui.design.classic.DisplayDrawAdapter;
import org.thirdreality.evolvinghorizons.guinness.gui.design.classic.SimulatedWindowDrawAdapter;

// The classic design without which looks very retro-stylish or ugly.
public class Classic extends Design
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private Point offset;

	private float scale;
	
	private DisplayDrawAdapter displayDrawAdapter;
	
	private SimulatedWindowDrawAdapter windowDrawAdapter;

	public Classic(DesignColor designColor, GBorderProperty borderProperty, GPaddingProperty paddingProperty)
	{
		super(designColor, borderProperty, paddingProperty);
		
		// This should be definitively the last constructor call as there are properties which have to be initialized first.
		displayDrawAdapter = new DisplayDrawAdapter(this);
	}
	
	public void drawContext(Graphics g, Viewport displayViewport, GComponent c, Point origin, Point offset, float scale)
	{
		if(windowDrawAdapter == null && displayViewport != null)
		{
			windowDrawAdapter = new SimulatedWindowDrawAdapter(displayViewport);
		}
		
		displayDrawAdapter.drawContext(g, displayViewport, c, origin, offset, scale);
		
		// Draws the content for windows (GWindows)
		windowDrawAdapter.drawSimulatedContext(g, c);
	}

	// Returns a determined shape which uses the design defined in this class.
	public Polygon generateDefaultShape(GComponent c)
	{
		int length = 0;
		
		switch(c.getType())
		{
			case "button":
			{
				length = ((GButton) c).getTitle().length();
				
				break;
			}
			
			case "description":
			{
				length = ((GDescription) c).getTitle().length();
				
				break;
			}
			
			case "textfield":
			{
				length = ((GTextfield) c).getValueManager().getMaxLength();
				
				break;
			}
			
			case "polybutton":
			{
				length = ((GPolyButton) c).getTitle().length();
				
				break;
			}
		}
		
		// Calculates the correct size of the rectangle for the default button component.
		Dimension backgroundSize = new Dimension(length * c.getStyle().getFont().getFontSize() + 2 * getPaddingProperty().getInnerThickness() + 2 * getBorderProperty().getBorderThicknessPx(), c.getStyle().getFont().getFontSize() + 2 * getPaddingProperty().getInnerThickness() + 2 * getBorderProperty().getBorderThicknessPx());

		Rectangle rectangle = new Rectangle(c.getStyle().getLocation(), backgroundSize);

		// Creates a rectangle actually.
		Polygon polygon = ShapeMaker.createRectangleFrom(rectangle, c.getStyle().getBorderProperties());
		
		return polygon;
	}

	// Updates the context component with its corresponding values.
	// This is a post-method.
	public void updateDefaultShape(GComponent c)
	{
		Polygon recalculated = generateDefaultShape(c);
		
		c.getStyle().setPrimaryLook(recalculated);
	}

	// Will return the offset of the Viewport.
	// If no offset was defined or assigned, it will return an empty point (0|0).
	public Point getOffset()
	{
		return offset != null ? offset : new Point();
	}

	public float getScale()
	{
		return scale;
	}
}