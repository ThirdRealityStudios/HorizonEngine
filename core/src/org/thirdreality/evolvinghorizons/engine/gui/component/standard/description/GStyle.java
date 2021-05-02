package org.thirdreality.evolvinghorizons.engine.gui.component.standard.description;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GStyle extends org.thirdreality.evolvinghorizons.engine.container.style.GStyle
{
	private Font font;
	private String text;
	private TextureRegion textureRegion;
	private GlyphLayout layout;

	public GStyle(Font font, String text)
	{
		this.text = text;

		// Creating a TextureRegion with no size or coordinate given.
		textureRegion = new TextureRegion(new Texture(new Pixmap(0,0, Pixmap.Format.RGBA8888)));

		setFont(font);

		update();
	}

	// Updates all bounds and grid information for the text.
	private void update()
	{
		layout = new GlyphLayout(font.getBitmapFont(), text);

		bounds = new Rectangle(bounds.x,bounds.y, layout.width, layout.height);

		textureRegion.setRegion((int) bounds.x, (int) bounds.y, (int) bounds.width, (int) bounds.height);
	}

	@Override
	public Rectangle getBounds()
	{
		update();

		return bounds;
	}

	public GlyphLayout getGlyphLayout()
	{
		return layout;
	}

	public void setFont(Font font)
	{
		this.font = font;
	}

	public Font getFont()
	{
		return font;
	}
}