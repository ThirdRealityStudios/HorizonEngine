package org.thirdreality.evolvinghorizons.engine.gui.component.decoration.image;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GStyle extends org.thirdreality.evolvinghorizons.engine.container.style.GStyle
{
	private TextureRegion textureRegion;

	public GStyle()
	{
		// Creating a TextureRegion with no size or coordinate given.
		textureRegion = new TextureRegion(new Texture(new Pixmap(0,0, Pixmap.Format.RGBA8888)));
	}

	public void setTexture(Texture t)
	{
		this.textureRegion.setTexture(t);
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
}