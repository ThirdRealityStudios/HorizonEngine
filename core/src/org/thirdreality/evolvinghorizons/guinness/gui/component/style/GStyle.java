package org.thirdreality.evolvinghorizons.guinness.gui.component.style;

import java.io.Serializable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.property.GBorderProperty;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;

public class GStyle implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	// Contains the bounds of the component.
	private Rectangle bounds;

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
	
	private BitmapFont font;
	
	private int padding = 0;
	
	private float alpha = 1f;

	private Color color = Color.BLACK;
	
	// If supported by the component, its borders can be modified by the properties stored in GBorder:
	// e.g. the border thickness and border radiuses in pixels.
	private GBorderProperty border;

	private Vector2 location;

	private Texture img;

	public GStyle()
	{
		textTransition = new Vector2();
		location = new Vector2();
		border = new GBorderProperty();
	}

	// Creates a GStyle from another GStyle without modifying its values.
	// The new GStyle will be a unique copy.
	// Anyway, you need specify the setLocation(...) again for your purpose..
	public GStyle(GStyle style)
	{
		border = style.getBorderProperties().copy();

		setFont(style.getFont());
		setImage(style.getImage());
		setAlpha(style.getAlpha());
		setPadding(style.getPadding());
		setColor(style.getColor());
		setTextAlign(style.getTextAlign());
		setTextTransition(style.getTextTransition());
		setVisible(style.isVisible());
		setBounds(style.getBounds());
	}

	public Boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public BitmapFont getFont()
	{
		return font;
	}

	public void setFont(BitmapFont font)
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
		return new Vector2(bounds.x, bounds.y);
	}

	public Texture getImage()
	{
		return img;
	}

	public void setImage(Texture img)
	{
		this.img = img;
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

	// Applies the given alpha value to the primary color.
	public void setAlpha(float alpha)
	{
		float r = getColor().r;
		float g = getColor().g;
		float b = getColor().b;

		getColor().set(r, g, b, alpha);
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

	public Rectangle getBounds()
	{
		return bounds;
	}

	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
	}
}