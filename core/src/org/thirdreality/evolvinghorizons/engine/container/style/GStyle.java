package org.thirdreality.evolvinghorizons.engine.container.style;

import java.io.Serializable;

import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;

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
	private Boolean visible;

	// Can the component be moved somewhere else by the Viewport?
	private boolean isMovable;

	protected Rectangle bounds;

	public GStyle()
	{
		isMovable = true;
		bounds = new Rectangle();
	}

	public Boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public boolean isMovable()
	{
		return isMovable;
	}

	public void setMovable(boolean isMovable)
	{
		this.isMovable = isMovable;
	}

	public Rectangle getBounds()
	{
		return bounds;
	}
}