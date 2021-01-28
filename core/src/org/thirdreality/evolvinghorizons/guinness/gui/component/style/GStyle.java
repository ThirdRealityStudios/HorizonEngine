package org.thirdreality.evolvinghorizons.guinness.gui.component.style;

import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import org.thirdreality.evolvinghorizons.guinness.IssueTracker;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.property.GBorderProperty;
import org.thirdreality.evolvinghorizons.guinness.gui.design.Design;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;

public abstract class GStyle implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	// Will tell the render method how to render this component.
	private Design design;

	// Contains the primary look of the component.
	private Polygon primaryLook;

	// Contains the secondary look of the component.
	private Polygon secondaryLook;

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
	
	// Can the component be scaled larger or smaller by the Viewport?
	private boolean isScalable = true;
	
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
	private Point textTransition;
	
	private Font font;
	
	private int paddingTop = 0, paddingBottom = 0;
	
	private float alpha = 1f;

	private Color primaryColor = null, bufferedColor = null;
	
	// If supported by the component, its borders can be modified by the properties stored in GBorder:
	// e.g. the border thickness and border radiuses in pixels.
	private GBorderProperty border;

	protected Point location;

	// Just contains an image in case it is wanted.
	// If you want the GComponent to be rendered as an image,
	// you need to clarify it in the variable "type" above (String value needs to be
	// "image" then).
	private Texture t;

	// The same as above but can be used as a buffer to things in between when needed..
	private Texture tBuffered;

	public GStyle()
	{
		primaryLook = new Polygon();
		secondaryLook = new Polygon();
		
		textTransition = new Point();
		location = new Point();
		border = new GBorderProperty();
	}

	// Creates a GStyle from another GStyle without modifying its values.
	// The new GStyle will be a unique copy.
	// Anyway, you need specify the setLocation(...) again for your purpose..
	public GStyle(GStyle style)
	{
		border = style.getBorderProperties().copy();
		
		setBufferedColor(style.getBufferedColor());
		setBufferedImage(style.getBufferedImage());
		setDesign(style.getDesign());
		setFont(style.getFont());
		setImage(style.getImage());
		setLocation(style.getLocation());
		setAlpha(style.getAlpha());
		setPaddingBottom(style.getPaddingBottom());
		setPaddingTop(style.getPaddingTop());
		setPrimaryColor(style.getPrimaryColor());
		setPrimaryLook(style.getPrimaryLook());
		setSecondaryLook(style.getSecondaryLook());
		setTextAlign(style.getTextAlign());
		setTextTransition(style.getTextTransition());
		setVisible(style.isVisible());
	}

	public Design getDesign()
	{
		return design;
	}

	public void setDesign(Design d)
	{
		this.design = d;
	}

	public Polygon getPrimaryLook()
	{
		return primaryLook;
	}

	public void setPrimaryLook(Polygon primaryLook)
	{
		this.primaryLook = primaryLook;
	}

	public Polygon getSecondaryLook()
	{
		return secondaryLook;
	}

	public void setSecondaryLook(Polygon secondaryLook)
	{
		this.secondaryLook = secondaryLook;
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

	public Color getPrimaryColor()
	{
		return primaryColor;
	}

	public void setPrimaryColor(Color primaryColor)
	{
		this.primaryColor = primaryColor;
	}

	public Color getBufferedColor()
	{
		return bufferedColor;
	}

	public void setBufferedColor(Color bufferedColor)
	{
		this.bufferedColor = bufferedColor;
	}

	public Point getLocation()
	{
		return location;
	}

	// Sets the location and also transforms the corresponding look / polygon to that location.
	public abstract void setLocation(Point location);

	public Texture getImage()
	{
		return t;
	}

	public void setImage(Texture t)
	{
		this.t = t;
	}

	public Texture getBufferedImage()
	{
		return tBuffered;
	}

	public void setBufferedImage(Texture tBuffered)
	{
		this.tBuffered = tBuffered;
	}
	
	public GBorderProperty getBorderProperties()
	{
		return border;
	}
	
	public void setBorderProperties(GBorderProperty borderProperties)
	{
		border = borderProperties;
	}

	public int getPaddingTop()
	{
		return paddingTop;
	}

	public void setPaddingTop(int paddingTop)
	{
		this.paddingTop = paddingTop;
	}

	public int getPaddingBottom()
	{
		return paddingBottom;
	}

	public void setPaddingBottom(int paddingBottom)
	{
		this.paddingBottom = paddingBottom;
	}

	public float getAlpha()
	{
		return alpha;
	}

	// Applies the given alpha value to the primary color.
	public void setAlpha(float alpha)
	{
		float r = getPrimaryColor().r;
		float g = getPrimaryColor().g;
		float b = getPrimaryColor().b;

		getPrimaryColor().set(r, g, b, alpha);
	}

	public int getTextAlign()
	{
		return textAlign;
	}

	public void setTextAlign(int textAlign)
	{
		this.textAlign = textAlign;
	}

	public Point getTextTransition()
	{
		return textTransition;
	}

	public void setTextTransition(Point textAlignTransition)
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

	public boolean isScalableForViewport()
	{
		return isScalable;
	}

	public void setScalableForViewport(boolean isScalable)
	{
		this.isScalable = isScalable;
	}
	
	public GStyle copy()
	{
		return new GStyle(this)
		{
			@Override
			public void setLocation(Point location)
			{
				System.err.println(IssueTracker.COPIED_ABSTRACT_CLASS_WARNING + "\nAffected methods: GStyle.setLocation(Point location)");
			}
		};
	}
}