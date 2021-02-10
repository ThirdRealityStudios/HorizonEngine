package org.thirdreality.evolvinghorizons.engine;

import java.awt.*;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.placeholder.GWindowManager;
import org.thirdreality.evolvinghorizons.engine.gui.layer.GLayer;
import org.thirdreality.evolvinghorizons.engine.render.Renderer;

public class Viewport
{
	private static final long serialVersionUID = 1L;

	// This contains all (prioritized) layers added to this Viewport.
	// The same priority can only exist once in a Viewport!
	private CopyOnWriteArrayList<GLayer> layers;

	// This list contains all GComponents which are added by new layers but not yet recognized by the system.
	private CopyOnWriteArrayList<GComponent> compBuffer;

	// After all components have been added all components are added from 'compBuffer' above.
	// This ensures that no errors can appear while adding new layers and reduces "performance waste".
	private GComponent[] compOutput;

	private int layerModifications = 0;

	private Vector2 offset = new Vector2();

	public int key = 0;
	
	// This will tell you whether this Viewport is used in a GWindow context (simulated) or in a Display context (real).
	// The Viewport will know it is simulated by just passing 'null' to the constructor when creating it.
	// In this case, the event handling is fully taken over by the "real Viewport" which makes use of its EventHandler yet.
	// That means on the other hand, an EventHandler can only be used with Displays, not with GWindows !
	private final boolean isSimulated;
	
	/* The origin can be set in order to apply an additional offset to the Viewport.
	 * The origin is a special implementation in order to enable component content in GWindows.
	 * Imagine, you have a GWindow but how should the main renderer know that there is an offset for a Viewport?
	 * Normally, there are offsets only for components directly added to a Display (JFrame).
	 * But in this case, you need to know further details to correctly position this Viewport in a GWindow.
	 * 
	 * By default, the origin is at (0|0) relative to the upper-left Display corner (also at (0|0) by nature).
	 * Anyway, the origin is at the position of the GWindow content frame when it is used for "simulated" GUI environments..
	 */
	private Vector2 origin = new Vector2();
	
	// You can define with this variable a so called "clipping area".
	// This will prevent components beyond these borders from being rendered and recognized.
	// It might help you to save performance but on the other hand it also serves a GWindow to avoid overlapping by the just simulated components.
	// The clipping area must be regarded absolute, meaning it really clips off components no matter what scale or offset they have in the end.
	// Also the clipping area always begins at (0|0) at the upper-left corner of the Display or GWindow.
	private Dimension clippingArea;

	// This is used for pure evaluation whether a component is still inside the clipping area or not.
	private Rectangle clippingRectangle;
	
	// Saves the highest priority of all layers recognized in this Viewport.
	private int priorityHighest;
	
	private GWindowManager windowManager;

	public Viewport(boolean isSimulated)
	{
		this.isSimulated = isSimulated;

		compBuffer = new CopyOnWriteArrayList<GComponent>();
		compOutput = new GComponent[0];

		layers = new CopyOnWriteArrayList<GLayer>();

		updateClippingRectangle(new Dimension());
		
		// Makes sure, it is being checked whether a window manager can be used at all.
		// A window manager cannot be used for example, when you use a Viewport for a GWindow (because it is simulated then).
		// More precisely, simulated Viewports do not support multiple GWindows within each other which is why there is such a restriction.
		if(!isSimulated())
		{
			windowManager = new GWindowManager(this);
		}
	}

	// Draws all components from the output list 'compOutput'.
	// When adding new layers, they are not yet added to the output directly.
	// First, it is being waited until all (new and old) components have been read (again (for old components yet stored)).
	// Only then the components are directly outputed by just changing the reference.
	public void render(GComponent[] components)
	{
		// Render all GUInness components.
		for(int i = components.length - 1; i >= 0; i--)
		{
			GComponent component = components[i];

			if(isContained(component) && component.getStyle().isVisible())
			{
				Renderer.drawContext(this, component);
			}
		}
	}

	// Erases the internal buffer.
	public synchronized void erase()
	{
		compBuffer.clear();
	}

	// Outputs all components of the buffer immediately to the output, 
	// so all changes will be visible then first.
	public void outputComponentBuffer()
	{
		compOutput = compBuffer.toArray(compOutput);
	}

	// Adds all components of a layer to the internal component buffer (which is used for drawing only).
	private void addLayerToComponentBuffer(GLayer target)
	{
		// Add every component of the current layer.
		for(GComponent comp : target.getComponentBuffer())
		{
			compBuffer.add(comp);
		}
	}

	// If a layer was changed, you can call this method to apply all changes.
	// Is very inefficient if it's called frequently.
	public synchronized void updateComponentBuffer()
	{
		erase(); // If buggy, re-instantiate the list.

		Collections.sort(layers);

		if(layers != null && layers.size() > 0)
		{
			if(layers.size() > 1)
			{
				for(GLayer layer : layers)
				{
					addLayerToComponentBuffer(layer);
				}
			}
			else
			{
				addLayerToComponentBuffer(layers.get(0));
			}
		}

		layerModifications = 0;
	}
	
	// This will check whether a given layer has the same priority as a layer which is added yet to the list.
	private boolean isDoublePriority(GLayer layer)
	{
		for(GLayer current : layers)
		{
			if(current.getPriority() == layer.getPriority())
			{
				return true;
			}
		}
		
		return false;
	}

	// The priority of a layer has to be at least zero or greater and mustn't not appear more than twice in the same Viewport.
	private boolean isValidPriority(GLayer layer)
	{
		return layer.getPriority() >= 0 && !isDoublePriority(layer);
	}

	public void addLayer(GLayer layer) throws IllegalArgumentException
	{
		if(isValidPriority(layer))
		{
			// This line is responsible for keeping the highest priority of all registered GLayers up-to-date.
			// Do not replace this line with the method "updateHighestLayerPriority()" as it occupies a lot more CPU usage.
			priorityHighest = Math.max(priorityHighest, layer.getPriority());

			layers.add(layer);

			layerModifications++;

			updateComponentBuffer();
			outputComponentBuffer();
		}
		else
		{
			throw new IllegalArgumentException("The given layers priority is invalid (< 0 or reason is \"double priority\")");
		}
	}

	public GLayer removeLayer(GLayer toRemove)
	{
		int index = 0;

		for(GLayer current : layers)
		{
			if(current.hashCode() == toRemove.hashCode())
			{
				break;
			}

			index++;
		}

		GLayer removed = layers.remove(index);
		
		updateHighestLayerPriority();

		layerModifications++;
		
		updateComponentBuffer();
		outputComponentBuffer();
		
		return removed;
	}
	
	private void updateHighestLayerPriority()
	{
		int priorityMax = 0;
		
		for(GLayer layer : layers)
		{
			priorityMax = Math.max(priorityMax, layer.getPriority());
		}
		
		this.priorityHighest = priorityMax;
	}

	public CopyOnWriteArrayList<GLayer> getLayers()
	{
		return layers;
	}

	public GLayer getLayer(int index)
	{
		return layers.get(index);
	}

	public int getLayerModifications()
	{
		return layerModifications;
	}

	// Returns the current offset, regardless of any Viewport scaling (!).
	// If you want to consider the Viewport scaling in your application and more precision,
	// use the getRelativeOffset()-method below please.
	public Vector2 getOffset()
	{
		return offset;
	}

	// Returns the exact offset relative to the current Viewport scale.
	// This method is definitely recommended when you need high precision,
	// though its frequent execution could cause a worse impact on the performance (remind floating Vector2 calculations and object creation).
	public Vector2 getRelativeOffset()
	{
		return new Vector2(getOffset().x, getOffset().y);
	}

	public void setOffset(Vector2 offset)
	{
		this.offset = offset;
	}

	// Use this method to retrieve a location which considers the scale and offset given by this Viewport.
	public Vector2 getLocationRelativeToViewport(Vector2 location)
	{
		return new Vector2(location.x + offset.x, location.y + offset.y);
	}

	public boolean isSimulated()
	{
		return isSimulated;
	}

	public Vector2 getOrigin()
	{
		return origin;
	}

	public void setOrigin(Vector2 origin)
	{
		this.origin = origin;
	}

	public int sizeOfComponentBuffer()
	{
		return compBuffer.size();
	}

	public int sizeOfComponentOutput()
	{
		return compOutput.length;
	}

	public GComponent[] getComponentOutput()
	{
		return compOutput;
	}

	public Dimension getClippingArea()
	{
		return clippingArea;
	}

	public void setClippingArea(Dimension clippingArea)
	{
		this.clippingArea = clippingArea;

		createClippingRectangle();
	}

	private void createClippingRectangle()
	{
		clippingRectangle = new Rectangle(getOrigin().x, getOrigin().y, getClippingArea().width, getClippingArea().height);
	}

	private void updateClippingRectangle(Dimension clippingArea)
	{
		setClippingArea(clippingArea);

		clippingRectangle.setSize(clippingArea.width, clippingArea.height);
		clippingRectangle.setPosition(getOrigin());
	}

	// Tells you whether a component can be rendered or recognized by IO, depending on an area which defines this (see 'clippingArea' and 'clippingRectangle' on top).
	// Also depends on whether the component is enabled.
	public boolean isContained(GComponent component)
	{
		boolean isContained = component.isEnabled();
		
		if(isSimulated())
		{
			clippingRectangle.setPosition(getOrigin());

			Rectangle componentBounds = component.getStyle().getBounds();

			Vector2 location = new Vector2(getOrigin()).add(getOffset()).add(componentBounds.x, componentBounds.y);

			Rectangle componentBoundsRelative = new Rectangle(location.x, location.y, componentBounds.width, componentBounds.height);

			isContained &= clippingRectangle.contains(componentBoundsRelative);
			
			return isContained;
		}

		return isContained;
	}

	// Returns the highest recognized priority of a layer in this Viewport.
	public int getLayerHighestPriority()
	{
		return priorityHighest;
	}

	// Returns the window manager responsible for this Viewport.
	// There is no window manager returned when this Viewport is simulated (see initialization in constructor).
	// This should ensure that you do not even try to work with GWindows within GWindows because this feature is not supported currently.
	public GWindowManager getWindowManager()
	{
		return windowManager;
	}
}