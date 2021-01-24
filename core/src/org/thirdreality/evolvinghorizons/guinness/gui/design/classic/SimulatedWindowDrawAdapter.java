package org.thirdreality.evolvinghorizons.guinness.gui.design.classic;

import java.awt.Graphics;
import java.awt.Point;

import com.badlogic.gdx.graphics.Pixmap;
import org.thirdreality.evolvinghorizons.guinness.feature.GIPoint;
import org.thirdreality.evolvinghorizons.guinness.gui.Viewport;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.placeholder.GWindow;

public class SimulatedWindowDrawAdapter
{
	// This saves the Displays Viewport and uses its offset for the re-calculation of the origin.
	private Viewport displayViewport;
	
	public SimulatedWindowDrawAdapter(Viewport displayViewport)
	{
		this.displayViewport = displayViewport;
	}
	
	// Draws the content for windows (for type GWindow).
	// This is a safe method, meaning it checks the components type for a GWindow.
	public void drawSimulatedContext(GComponent c)
	{
		if(c.getType().contentEquals("window"))
		{
			drawSimulatedViewport((GWindow) c);
		}
	}
	
	// Updates the origin of the simulated Viewport.
	// The re-calculated origin is then used to render the Viewport and to recognize the interaction (via ComponentHandler) with all components correctly.
	private void updateOriginOfSimulatedViewport(Viewport displayViewport, GWindow target)
	{
		// Tells the renderer (Viewport) afterwards to render its components at the given location (origin).
		// In this case, it is the upper-left corner of the inner frame of the window.
		Point originRecalculated = new GIPoint(target.getStyle().getSecondaryLook().getBounds().getLocation()).add(displayViewport.getOffset()).toPoint();
		
		target.getViewport().setOrigin(originRecalculated);
	}

	private void drawSimulatedViewport(GWindow target)
	{
		if(target.hasViewport())
		{
			// Update the origin of the simulated Viewport to render all components correctly.
			updateOriginOfSimulatedViewport(displayViewport, target);

			// Now render here all components of the Viewport at the given origin (location),
			renderEachComponent(target.getViewport());
		}
	}

	private void renderEachComponent(Viewport source)
	{
		source.render(source.getComponentOutput());
	}
}
