package org.thirdreality.evolvinghorizons.engine.gui.component.input.textfield;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.container.style.property.GBorderProperty;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GStyle extends org.thirdreality.evolvinghorizons.engine.container.style.GStyle
{
	private Font font;
	private TextureRegion textureRegion;
	private Color color;
	private int padding;
	private GBorderProperty border;

	public GStyle()
	{
		border = new GBorderProperty();

		// Creating a TextureRegion with no size or coordinate given.
		textureRegion = new TextureRegion(new Texture(new Pixmap(0,0, Pixmap.Format.RGBA8888)));
	}

	public void setFont(Font font)
	{
		this.font = font;
	}

	public void setBounds(Rectangle bounds)
	{
		textureRegion.setRegion((int) bounds.x, (int) bounds.y, (int) bounds.width, (int) bounds.height);

		// Update 'bounds' so the programmer knows the new ones.
		this.bounds = bounds;
	}

	public Font getFont()
	{
		return font;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public void setPadding(int padding)
	{
		this.padding = padding;
	}

	public Color getColor()
	{
		return color;
	}

	public int getPadding()
	{
		return padding;
	}

	public GBorderProperty getBorderProperties()
	{
		return border;
	}
}