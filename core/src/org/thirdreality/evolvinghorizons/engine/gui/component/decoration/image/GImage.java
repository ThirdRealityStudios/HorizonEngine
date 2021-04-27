package org.thirdreality.evolvinghorizons.engine.gui.component.decoration.image;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;

public class GImage extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	public GImage(Vector2 position, Texture content, boolean repeat)
	{
		super("image");

		getStyle().setTexture(content);
	}

	public GImage(Vector2 position, float scale, Texture content)
	{
		super("image");

		int scaledWidth = (int) (scale * content.getWidth());
		int scaledHeight = (int) (scale * content.getHeight());

		getStyle().setTexture(content);

		getStyle().getTextureRegion().setRegionX((int) position.x);
		getStyle().getTextureRegion().setRegionY((int) position.y);

		getStyle().getTextureRegion().setRegionWidth(scaledWidth / content.getWidth());
		getStyle().getTextureRegion().setRegionWidth(scaledHeight / content.getHeight());
	}

	public GImage(Rectangle size, Texture content)
	{
		super("image");

		getStyle().setTexture(content);
		getStyle().setBounds(size);
	}

	public GImage(Vector2 position, int size, boolean useAsWidth, Texture content)
	{
		super("image");

		int scaledWidth = useAsWidth ? size : (int) (((float) size / content.getHeight()) * content.getWidth());
		int scaledHeight = useAsWidth ? (int) (((float) size / content.getWidth()) * content.getHeight()) : size;

		getStyle().setTexture(content);
		getStyle().getTextureRegion().setRegion((int) position.x, (int) position.y, scaledWidth, scaledHeight);
	}
}
