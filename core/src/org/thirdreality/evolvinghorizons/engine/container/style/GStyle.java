package org.thirdreality.evolvinghorizons.engine.container.style;

import java.io.Serializable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.container.style.property.GBorderProperty;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GStyle implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	// Tells whether the context or component is visible or not.
	// If 'null', a value will be automatically assigned later.
	// Having 'null' in the beginning only helps the program to know
	// if a value was assigned already.
	// Having this possibility, it prevents already set values to be overwritten when
	// adding new components to a layer.
	// A layer would otherwise just overwrite the already set value with default values.
	private Boolean visible = null;
	
	// Can the component be moved somewhere else by the Viewport?
	private boolean isMovable = true;

	/*
	 * WARNING! This property might not be supported for all components.
	 * If not supported, it is anyway mostly the case that the upper-left corner is taken by default..
	 * 
	 * 0 = normal (text or title will  appear at the upper-left corner of the component).
	 * 1 = center (appears in the center of the component.
	 */
	private int textAlign = 0;
	
	// If you want the actual text to appear somewhere else than in the center or upper-left corner,
	// you can tell the program to translate or reposition the text by any x and y value.
	// The translated text is then not recognized later as a component itself.
	// Translating your text is because of that just for pure decoration purposes.
	private Vector2 textTransition;

	private Vector2 vect;
	
	private Font font;
	
	private int padding = 4;
	
	private float alpha = 1f;

	private Color color;
	
	// If supported by the component, its borders can be modified by the properties stored in GBorder:
	// e.g. the border thickness and border radiuses in pixels.
	private GBorderProperty border;

	private TextureRegion textureRegion;

	public GStyle()
	{
		textTransition = new Vector2();
		border = new GBorderProperty();

		// Creating a TextureRegion with no size or coordinate given.
		textureRegion = new TextureRegion(new Texture(new Pixmap(0,0, Pixmap.Format.RGBA8888)));
	}

	public Boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public Font getFont()
	{
		return font;
	}

	public void setFont(Font font)
	{
		this.font = font;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public Vector2 getPosition()
	{
		return new Vector2(textureRegion.getRegionX(), textureRegion.getRegionY());
	}

	public TextureRegion getTextureRegion()
	{
		return textureRegion;
	}

	public void setTexture(Texture t)
	{
		this.textureRegion.setTexture(t);
	}

	public GBorderProperty getBorderProperties()
	{
		return border;
	}
	
	public void setBorderProperties(GBorderProperty borderProperties)
	{
		border = borderProperties;
	}

	public int getPadding()
	{
		return padding;
	}

	public void setPadding(int padding)
	{
		this.padding = padding;
	}

	public float getAlpha()
	{
		return alpha;
	}

	public int getTextAlign()
	{
		return textAlign;
	}

	public void setTextAlign(int textAlign)
	{
		this.textAlign = textAlign;
	}

	public Vector2 getTextTransition()
	{
		return textTransition;
	}

	public void setTextTransition(Vector2 textAlignTransition)
	{
		this.textTransition = textAlignTransition;
	}

	public boolean isMovableForViewport()
	{
		return isMovable;
	}

	public void setMovableForViewport(boolean isMovable)
	{
		this.isMovable = isMovable;
	}

	public void setBounds(Rectangle bounds)
	{
		textureRegion.setRegion((int) bounds.x, (int) bounds.y, (int) bounds.width, (int) bounds.height);
	}

	public Rectangle getBounds()
	{
		return new Rectangle(textureRegion.getRegionX(), textureRegion.getRegionY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
	}
}