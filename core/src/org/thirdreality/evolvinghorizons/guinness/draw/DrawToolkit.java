package org.thirdreality.evolvinghorizons.guinness.draw;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import org.thirdreality.evolvinghorizons.guinness.feature.image.ImageToolkit;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;
import org.thirdreality.evolvinghorizons.guinness.gui.font.FontLoader;

public class DrawToolkit
{
	private static FontLoader fontLoader = new FontLoader();
	
	// Displays a letter from the delivered alphabet pattern on the specified
	// graphics object.
	public static void drawChar(Graphics g, char letter, int xPos, int yPos, Font font)
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

			BufferedImage img = font.getImage().getSubimage(x, y, dim, dim);

			Image colorized = ImageToolkit.colorize(img, font.getFontColor()).getScaledInstance(font.getFontSize(), font.getFontSize(), Image.SCALE_SMOOTH);

			g.drawImage(colorized, xPos, yPos, null);
		}
		else
		{
			int indexSymbolNotFound = fontLoader.getDigitIndex('0');

			int xSymbolNotFound = (indexSymbolNotFound - 1) * 30 + indexSymbolNotFound, ySymbolNotFound = 1;

			BufferedImage img = font.getImage().getSubimage(xSymbolNotFound, ySymbolNotFound, dim, dim);

			Image colorized = ImageToolkit.colorize(img, font.getFontColor()).getScaledInstance(font.getFontSize(), font.getFontSize(), Image.SCALE_SMOOTH);

			g.drawImage(colorized, xPos, yPos, null);
		}
	}

	// Displays a whole string (only alphabetic letters) and scales it according to
	// the specified font size.
	public static Dimension drawString(Graphics g, String text, Point pos, Font font)
	{
		char[] converted = text.toCharArray();

		int offset = 0;

		for (char c : converted)
		{
			drawChar(g, c, pos.x + font.getFontSize() * offset, pos.y, font);

			offset++;
		}

		return new Dimension(font.getFontSize() * text.length(), font.getFontSize());
	}
}