package org.thirdreality.evolvinghorizons.guinness.gui.component.decoration;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.feature.shape.ShapeMaker;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.w3c.dom.css.Rect;

public class GImage extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	public GImage(Vector2 position, Texture content)
	{
		super("image");

		int width = content.getWidth();
		int height = content.getHeight();

		Rectangle rectangle = new Rectangle(position.x, position.y, width, height);
		getStyle().setBounds(rectangle);

		getStyle().setImage(content);
	}

	public GImage(Vector2 position, float scale, Texture content)
	{
		super("image");

		int scaledWidth = (int) (scale * content.getWidth());
		int scaledHeight = (int) (scale * content.getHeight());
		
		Rectangle rectangle = new Rectangle(position.x, position.y, scaledWidth, scaledHeight);
		getStyle().setBounds(rectangle);
		
		getStyle().setImage(content);
	}

	public GImage(Rectangle img, Texture content)
	{
		super("image");

		getStyle().setBounds(img);

		getStyle().setImage(content);
	}

	public GImage(Vector2 position, int size, boolean useAsWidth, Texture content)
	{
		super("image");

		int scaledWidth = useAsWidth ? size : (int) (((float) size / content.getHeight()) * content.getWidth());
		int scaledHeight = useAsWidth ? (int) (((float) size / content.getWidth()) * content.getHeight()) : size;

		Rectangle rectangle = new Rectangle(position.x, position.y, scaledWidth, scaledHeight);
		getStyle().setBounds(rectangle);

		// Is always executed after having set the primary look because it transforms it directly to the given location.
		getStyle().setBounds(rectangle);

		getStyle().setImage(content);
	}
}
