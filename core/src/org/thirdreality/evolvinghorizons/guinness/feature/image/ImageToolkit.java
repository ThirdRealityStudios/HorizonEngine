package org.thirdreality.evolvinghorizons.guinness.feature.image;

import com.badlogic.gdx.assets.loaders.PixmapLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.xml.soap.Text;

public class ImageToolkit
{
	public static Texture loadImage(String path)
	{
		return new Texture(path);
	}

	@Deprecated
	public static void colorize(float x, float y, int width, int height, Texture texture, Color color)
    {
        // Work on this ! ! !

        Pixmap filterPreset = new Pixmap(width, height, Pixmap.Format.Alpha);
        filterPreset.setColor(color);
        filterPreset.fillRectangle(0, 0, width, height);

        Texture filterEffect = new Texture(filterPreset);

        SpriteBatch drawContext = new SpriteBatch(2);

        drawContext.begin();
        drawContext.draw(texture, x, y, width, height);
        drawContext.draw(filterEffect, x, y);
        drawContext.end();
    }
}
