package org.thirdreality.evolvinghorizons.guinness.gui.component;

import java.awt.Point;
import java.awt.Polygon;
import java.io.File;
import java.io.Serializable;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.Settings;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.feature.Path;
import org.thirdreality.evolvinghorizons.guinness.feature.shape.ShapeTransform;
import org.thirdreality.evolvinghorizons.guinness.gui.DisplayContext;
import org.thirdreality.evolvinghorizons.guinness.gui.Viewport;
import org.thirdreality.evolvinghorizons.guinness.gui.component.optional.GActionListener;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.GStyle;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;

public abstract class GComponent implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	// Determines the type of the GComponent, e.g. image, path or default.
	// This will determine the render method later.
	private String type;

	/* Determines whether the component should be enabled or not.
	 * If it's disabled, it is not just invisible but also you cannot interact with it anymore.
	 * If 'null', a value will be automatically assigned later.
	 * Having 'null' in the beginning only helps the program to know
	 * if a value was assigned already.
	 * Having this possibility, it prevents already set values to be overwritten when
	 * adding new components to a layer.
	 * A layer would otherwise just overwrite the already set value with default values.
	 */
	private Boolean enabled = null;

	// Relates to the offset.
	private boolean movable = true;

	private Vector2 origin, offset;

	private GStyle style;
	
	private GLogic logic;
	
	// This contains the onClick() and onHover() methods to be run on this component.
	private GActionListener actions;

	public GComponent(String type)
	{
		style = new GStyle();
		logic = new GLogic();

		setType(type);
	}
	
	public GComponent(String type, Rectangle bounds)
	{
		this(type);

		getStyle().setBounds(bounds);
	}

	public String getType()
	{
		return type;
	}

	private void setType(String type)
	{
		this.type = type;
	}

	public void print()
	{
		System.out.println(this);
	}

	@Override
	public String toString()
	{
		return getClass().hashCode() + " (class: " + this.getClass().getSimpleName() + ", type: \"" + getType()
				+ "\"):\nshape = " + getStyle().getBounds() + "\nlength = "
				+ "\nvalue = \"" + "\nvisible = " + getStyle().isVisible();
	}

	public Boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public GStyle getStyle()
	{
		return style;
	}
	
	public void setStyle(GStyle style)
	{
		this.style = style;
	}

	public GLogic getLogic()
	{
		return logic;
	}

	public void setLogic(GLogic logic)
	{
		this.logic = logic;
	}

	public void setActionListener(GActionListener actions)
	{
		this.actions = actions;
	}
	
	public GActionListener getActionListener()
	{
		return actions;
	}
	
	public boolean hasActionListener()
	{
		return actions != null;
	}

	public Vector2 getOrigin()
	{
		return origin;
	}

	public void setOrigin(Viewport viewport)
	{
		this.origin = viewport.getOrigin();
	}

	public Vector2 getOffset()
	{
		return offset;
	}

	public void setOffset(Viewport viewport)
	{
		this.offset = viewport.getOffset();
	}
}