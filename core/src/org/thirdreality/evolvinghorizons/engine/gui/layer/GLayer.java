package org.thirdreality.evolvinghorizons.engine.gui.layer;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;

// Contains an amount of drawable components.
public class GLayer implements Comparable<GLayer>, Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private GComponent[] components;

	private int priority;

	public GLayer(GComponent[] components, int priority)
	{
		this.components = components;

		this.priority = priority;
	}

	private GComponent[] copy(GComponent[] array)
	{
		GComponent[] components = new GComponent[array.length];

		for(int i = 0; i < components.length; i++)
		{
			components[i] = array[i];
		}

		return components;
	}

	public GComponent[] getComponents()
	{
		return components;
	}

	// Checks whether there is a component in this layer with the same priority.
	private boolean overlaps(GComponent tested)
	{
		for(GComponent current : components)
		{
			Rectangle boundsCurrent = new Rectangle(current.getStyle().getTextureRegion().getRegionX(), current.getStyle().getTextureRegion().getRegionY(), current.getStyle().getTextureRegion().getRegionWidth(), current.getStyle().getTextureRegion().getRegionHeight());
			Rectangle boundsTested = new Rectangle(tested.getStyle().getTextureRegion().getRegionX(), tested.getStyle().getTextureRegion().getRegionY(), tested.getStyle().getTextureRegion().getRegionWidth(), tested.getStyle().getTextureRegion().getRegionHeight());

			if(boundsCurrent.intersects(boundsTested) || boundsCurrent.contains(boundsTested) && current.hashCode() != tested.hashCode())
			{
				return true;
			}
		}

		return false;
	}

	// Is "protected" because you need to make sure no components are at the same position.
	// To use a new CopyOnWriteArrayList of type GComponent, create a new GLayer instead.
	public void setComponents(GComponent[] source)
	{
		for(GComponent comp : source)
		{
			if(overlaps(comp))
			{
				// Return because (at least) two components overlap each other.
				return;
			}
		}

		GComponent[] components = copy(source);

		// Sort the components in the layer by their priority.
		Arrays.sort(components);

		this.components = components;
	}

	public int getPriority()
	{
		return priority;
	}

	public boolean setPriority(int priority)
	{
		if(priority >= 0)
		{
			this.priority = priority;

			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public int compareTo(GLayer layer)
	{
		return layer.getPriority() - this.getPriority();
	}
}
