package org.thirdreality.evolvinghorizons.engine.gui.component.selection.checkbox;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.container.style.property.GBorderProperty;

public class GStyle extends org.thirdreality.evolvinghorizons.engine.container.style.GStyle
{
	private TextureRegion textureRegion;
	private GBorderProperty border;
	private Color color;

	public GStyle()
	{
		border = new GBorderProperty();

		// Creating a TextureRegion with no size or coordinate given.
		textureRegion = new TextureRegion(new Texture(new Pixmap(0,0, Pixmap.Format.RGBA8888)));
	}


	public GBorderProperty getBorderProperties()
	{
		return border;
	}

	public void setTexture(Texture t)
	{
		this.textureRegion.setTexture(t);
	}

	public void setBounds(Rectangle bounds)
	{
		textureRegion.setRegion((int) bounds.x, (int) bounds.y, (int) bounds.width, (int) bounds.height);

		// Update 'bounds' so the programmer knows the new ones.
		this.bounds = bounds;
	}

	public TextureRegion getTextureRegion()
	{
		return textureRegion;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
}