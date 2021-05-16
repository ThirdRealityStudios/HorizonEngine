package org.thirdreality.horizonengine.gui.font;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Font
{
	private BitmapFont font;

	public Font(FileHandle internal, int fontSize) throws NullPointerException, IllegalArgumentException
	{
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(internal);
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.padBottom = 0;
		parameter.padTop = 0;
		parameter.padLeft = 0;
		parameter.padRight = 0;
		parameter.color = Color.BLACK;
		parameter.magFilter = Texture.TextureFilter.Linear;
		parameter.minFilter = Texture.TextureFilter.Linear;
		parameter.size = fontSize;

		font = generator.generateFont(parameter);
		
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

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
