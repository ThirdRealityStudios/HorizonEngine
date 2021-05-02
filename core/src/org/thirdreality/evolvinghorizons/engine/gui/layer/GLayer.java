package org.thirdreality.evolvinghorizons.engine.gui.layer;

import java.io.Serializable;
import java.util.Arrays;

import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.image.GImage;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.path.GPath;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.rectangle.GRectangle;
import org.thirdreality.evolvinghorizons.engine.gui.component.input.textfield.GTextfield;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.checkbox.GCheckbox;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.tickbox.GTickBoxList;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.button.GButton;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.description.GDescription;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.polybutton.GPolyButton;
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

	private Rectangle getBounds(GComponent component)
	{
		if(component instanceof GImage)
		{
			GImage image = (GImage) component;

			return image.getStyle().getBounds();
		}
		else if(component instanceof GPolyButton)
		{
			GPolyButton polyButton = (GPolyButton) component;

			return polyButton.getStyle().getBounds();
		}
		else if(component instanceof GDescription)
		{
			GDescription description = (GDescription) component;

			return description.getStyle().getBounds();
		}
		else if(component instanceof GPath)
		{
			GPath path = (GPath) component;

			return path.getStyle().getBounds();
		}
		else if(component instanceof GTextfield)
		{
			GTextfield textfield = (GTextfield) component;

			return textfield.getStyle().getBounds();
		}
		else if(component instanceof GCheckbox)
		{
			GCheckbox checkbox = (GCheckbox) component;

			return checkbox.getStyle().getBounds();
		}
		else if(component instanceof GTickBoxList)
		{
			GTickBoxList tickBoxList = (GTickBoxList) component;

			return tickBoxList.getStyle().getBounds();
		}
		else if(component instanceof GRectangle)
		{
			GRectangle rectangle = (GRectangle) component;

			return rectangle.getStyle().getBounds();
		}
		else if(component instanceof GButton)
		{
			GButton button = (GButton) component;

			return button.getStyle().getBounds();
		}

		return null;
	}

	// Checks whether there is a component in this layer with the same priority.
	private boolean overlaps(GComponent tested)
	{
		for(GComponent current : components)
		{
			Rectangle boundsCurrent = getBounds(current);
			Rectangle boundsTested = getBounds(tested);

			if(boundsCurrent.overlaps(boundsTested) || boundsCurrent.contains(boundsTested) && current.hashCode() != tested.hashCode())
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
