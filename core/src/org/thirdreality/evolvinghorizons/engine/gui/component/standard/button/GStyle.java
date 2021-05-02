package org.thirdreality.evolvinghorizons.engine.gui.component.standard.button;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.container.style.property.GBorderProperty;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;

import java.io.Serializable;

public class GStyle extends org.thirdreality.evolvinghorizons.engine.container.style.GStyle
{
	private Font font;
	private int padding;
	private GBorderProperty border;
	private TextureRegion textureRegion;
	private Color color;

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

	public GBorderProperty getBorderProperties()
	{
		return border;
	}

	public void setPadding(int padding)
	{
		this.padding = padding;
	}

	public void fillBounds(Color color)
	{
		Pixmap p = new Pixmap(1,1, Pixmap.Format.RGBA8888);
		p.setColor(color);
		p.fill();
		Texture t = new Texture(p);
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

	public int getPadding()
	{
		return padding;
	}

	public Color getColor()
	{
		return color;
	}

	// The applied when clicked.
	public void setColor(Color color)
	{
		this.color = color;
	}

	/*
	public Rectangle getBounds()
	{
		return new Rectangle(position.x, position.y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
	}
	 */
}