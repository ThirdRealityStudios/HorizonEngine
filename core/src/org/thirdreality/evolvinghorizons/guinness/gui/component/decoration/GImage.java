package org.thirdreality.evolvinghorizons.guinness.gui.component.decoration;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.feature.shape.ShapeMaker;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;

public class GImage extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	public GImage(Point location, Image content)
	{
		super("image");

		getStyle().setLocation(location);

		int width = content.getWidth(null);
		int height = content.getHeight(null);

		Polygon rectangle = ShapeMaker.createRectangle(location.x, location.y, width, height);
		getStyle().setPrimaryLook(rectangle);

		getStyle().setImage(content);
	}

	public GImage(Point location, float scale, Image content)
	{
		super("image");
		
		getStyle().setLocation(location);
		
		int scaledWidth = (int) (scale * content.getWidth(null));
		int scaledHeight = (int) (scale * content.getHeight(null));
		
		Polygon rectangle = ShapeMaker.createRectangle(location.x, location.y, scaledWidth, scaledHeight);
		getStyle().setPrimaryLook(rectangle);
		
		getStyle().setImage(content);
	}

	public GImage(Point location, Dimension size, Image content)
	{
		super("image");
		
		getStyle().setLocation(location);
		
		getStyle().setPrimaryLook(ShapeMaker.createRectangle(location.x, location.y, size.width, size.height));
		
		getStyle().setImage(content);
	}

	public GImage(Point location, int size, boolean useAsWidth, Image content)
	{
		super("image");

		int scaledWidth = useAsWidth ? size : (int) (((float) size / content.getHeight(null)) * content.getWidth(null));
		int scaledHeight = useAsWidth ? (int) (((float) size / content.getWidth(null)) * content.getHeight(null)) : size;
		
		Polygon rectangle = ShapeMaker.createRectangle(location.x, location.y, scaledWidth, scaledHeight);
		getStyle().setPrimaryLook(rectangle);
		
		// Is always executed after having set the primary look because it transforms it directly to the given location.
		getStyle().setLocation(location);
		
		getStyle().setImage(content);
	}
}
