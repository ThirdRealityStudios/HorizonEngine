package org.thirdreality.evolvinghorizons.engine.gui.component.decoration.rectangle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.container.style.property.GBorderProperty;

public class GStyle extends org.thirdreality.evolvinghorizons.engine.container.style.GStyle
{
	private Color color;
	private TextureRegion textureRegion;
	private int padding;

	// Borders can be modified by several parameters,
	// such as the border thickness or border radius in pixels.
	private GBorderProperty border;

	public GStyle()
	{
		border = new GBorderProperty();

		// Creating a TextureRegion with no size or coordinate given.
		textureRegion = new TextureRegion(new Texture(new Pixmap(0,0, Pixmap.Format.RGBA8888)));
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public TextureRegion getTextureRegion()
	{
		return textureRegion;
	}

	public void setBounds(Rectangle bounds)
	{
		textureRegion.setRegion((int) bounds.x, (int) bounds.y, (int) bounds.width, (int) bounds.height);

		// Update 'bounds' so the programmer knows the new ones.
		this.bounds = bounds;
	}

	public void setPadding(int padding)
	{
		this.padding = padding;
	}

	public GBorderProperty getBorderProperties()
	{
		return border;
	}
}