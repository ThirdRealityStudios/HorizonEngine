package org.thirdreality.evolvinghorizons.engine.gui.layer;

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
	private boolean isDoublePriority(int priority)
	{
		for(GComponent component : components)
		{
			if(component.getPriority() == priority)
			{
				return true;
			}
		}

		return false;
	}

	// Is "protected" because you need to make sure no components are at the same position.
	// To use a new CopyOnWriteArrayList of type GComponent, create a new GLayer instead.
	public boolean setComponents(GComponent[] source)
	{
		for(GComponent comp : source)
		{
			if(isDoublePriority(comp.getPriority()))
			{
				// Tell the programmer something went wrong, meaning that (at least) two components overlap each other.
				return false;
			}
		}

		GComponent[] components = copy(source);

		// Sort the components in the layer by their priority.
		Arrays.sort(components);

		this.components = components;

		return true;
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
		return this.getPriority() - layer.getPriority();
	}
}
