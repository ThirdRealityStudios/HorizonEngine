package org.thirdreality.evolvinghorizons.guinness.draw;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.thirdreality.evolvinghorizons.guinness.feature.image.ImageToolkit;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;
import org.thirdreality.evolvinghorizons.guinness.gui.font.FontLoader;

public class DrawToolkit
{
	private static FontLoader fontLoader = new FontLoader();

	@Deprecated
	// Displays a letter from the delivered alphabet pattern on the specified
	// graphics object.
	public static void drawChar(char letter, int xPos, int yPos, Font font)
	{
		// This will determine the correct index for the symbol.
		// The index is later used to calculate the correct position in the font image file (PNG).
		int index = fontLoader.getSymbolIndex(letter);

		// This is the size of each symbol in the font image file (PNG),
		// meaning 30px as a "base font size".
		// Scaling it can blur the symbol.
		int dim = 30;

		if(fontLoader.isImplemented(letter) && index > -1)
		{
			// This determines the correct position of the symbol on the x-axis in the font image file (PNG).
			// The font image file contains borders for each symbol (for better distinguishing and readibility for modifications).
			int x = (index - 1) * 30 + index;
			
			// In the font image file (PNG), the symbols always begin from the second pixel on the top.
			// The first pixel is just a kind of border (read description above for 'x').
			int y = 1;

			// Work here on loading sub-images from a texture ! (by using texture packs)

			// Has no function / no real use.
			Pixmap charPixmap = new Pixmap(font.getFontSize(), font.getFontSize(), Pixmap.Format.Alpha);

			// Draws the colorized symbol directly at the given position.
			ImageToolkit.colorize(xPos, yPos, font.getFontSize(), font.getFontSize(), new Texture(charPixmap), font.getFontColor());
		}
		else
		{
			int indexSymbolNotFound = fontLoader.getDigitIndex('0');

			int xSymbolNotFound = (indexSymbolNotFound - 1) * 30 + indexSymbolNotFound, ySymbolNotFound = 1;

			// BufferedImage img = font.getImage().getSubimage(xSymbolNotFound, ySymbolNotFound, dim, dim);

			// Work here on loading sub-images from a texture ! (by using texture packs)

			// Has no function / no real use.
			Pixmap charPixmap = new Pixmap(font.getFontSize(), font.getFontSize(), Pixmap.Format.Alpha);

			// Draws the colorized symbol directly at the given position.
			ImageToolkit.colorize(xPos, yPos, font.getFontSize(), font.getFontSize(), new Texture(charPixmap), font.getFontColor());
		}
	}

	// Displays a whole string (only alphabetic letters) and scales it according to
	// the specified font size.
	public static Dimension drawString(String text, Point pos, Font font)
	{
		char[] converted = text.toCharArray();

		int offset = 0;

		for (char c : converted)
		{
			drawChar(c, pos.x + font.getFontSize() * offset, pos.y, font);

			offset++;
		}

		return new Dimension(font.getFontSize() * text.length(), font.getFontSize());
	}
}