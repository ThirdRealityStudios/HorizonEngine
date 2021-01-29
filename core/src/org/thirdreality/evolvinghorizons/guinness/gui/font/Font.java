package org.thirdreality.evolvinghorizons.guinness.gui.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import org.thirdreality.evolvinghorizons.guinness.feature.Path;

public class Font
{
	private BitmapFont font;

	public Font(FileHandle internal, int fontSize) throws NullPointerException, IllegalArgumentException
	{
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(internal);
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.color = Color.WHITE;
		parameter.magFilter = Texture.TextureFilter.Linear;
		parameter.minFilter = Texture.TextureFilter.Linear;
		parameter.size = fontSize;

		font = generator.generateFont(parameter);

		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		font.setColor(Color.BLACK);

		generator.dispose();
	}

	public BitmapFont getBitmapFont()
	{
		return font;
	}

	public int getFontSize(char symbol)
	{
		return font.getData().getGlyph(symbol).width;
	}
}
