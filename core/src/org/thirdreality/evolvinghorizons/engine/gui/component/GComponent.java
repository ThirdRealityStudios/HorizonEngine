package org.thirdreality.evolvinghorizons.engine.gui.component;

import java.io.Serializable;

import org.thirdreality.evolvinghorizons.engine.container.GLogic;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;

public abstract class GComponent implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;

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

	private GLogic logic;
	
	// This contains the onClick() and onHover() methods to be run on this component.
	private ActionListener actions;

	private boolean isZoomable = false;

	public GComponent(String type)
	{
		logic = new GLogic();
	}

	public void print()
	{
		System.out.println(this);
	}

	@Override
	public String toString()
	{
		return getClass().hashCode() + " (class: " + this.getClass().getSimpleName() + ", type: \"" + getClass()
				+ "\"):\nlength = "
				+ "\nvalue = \"";
	}

	public Boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public GLogic getLogic()
	{
		return logic;
	}

	public void setLogic(GLogic logic)
	{
		this.logic = logic;
	}

	public void setActionListener(ActionListener actions)
	{
		this.actions = actions;
	}
	
	public ActionListener getActionListener()
	{
		return actions;
	}
	
	public boolean hasActionListener()
	{
		return actions != null;
	}

	public boolean isZoomable()
	{
		return isZoomable;
	}

	public void setZoomable(boolean zoomable)
	{
		isZoomable = zoomable;
	}
}