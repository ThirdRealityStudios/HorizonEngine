package org.thirdreality.evolvinghorizons.guinness.gui.component.decoration;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

import com.badlogic.gdx.graphics.Texture;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.feature.shape.ShapeMaker;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;

public class GImage extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	public GImage(Point location, Texture content)
	{
		super("image");

		getStyle().setLocation(location);

		int width = content.getWidth();
		int height = content.getHeight();

		Polygon rectangle = ShapeMaker.createRectangle(location.x, location.y, width, height);
		getStyle().setPrimaryLook(rectangle);

		getStyle().setImage(content);
	}

	public GImage(Point location, float scale, Texture content)
	{
		super("image");
		
		getStyle().setLocation(location);
		
		int scaledWidth = (int) (scale * content.getWidth());
		int scaledHeight = (int) (scale * content.getHeight());
		
		Polygon rectangle = ShapeMaker.createRectangle(location.x, location.y, scaledWidth, scaledHeight);
		getStyle().setPrimaryLook(rectangle);
		
		getStyle().setImage(content);
	}

	public GImage(Point location, Dimension size, Texture content)
	{
		super("image");
		
		getStyle().setLocation(location);
		
		getStyle().setPrimaryLook(ShapeMaker.createRectangle(location.x, location.y, size.width, size.height));
		
		getStyle().setImage(content);
	}

	public GImage(Point location, int size, boolean useAsWidth, Texture content)
	{
		super("image");

		int scaledWidth = useAsWidth ? size : (int) (((float) size / content.getHeight()) * content.getWidth());
		int scaledHeight = useAsWidth ? (int) (((float) size / content.getWidth()) * content.getHeight()) : size;
		
		Polygon rectangle = ShapeMaker.createRectangle(location.x, location.y, scaledWidth, scaledHeight);
		getStyle().setPrimaryLook(rectangle);
		
		// Is always executed after having set the primary look because it transforms it directly to the given location.
		getStyle().setLocation(location);
		
		getStyle().setImage(content);
	}
}
