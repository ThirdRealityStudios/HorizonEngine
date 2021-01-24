package org.thirdreality.evolvinghorizons.guinness.gui.adapter;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.thirdreality.evolvinghorizons.guinness.feature.GIPoint;
import org.thirdreality.evolvinghorizons.guinness.feature.shape.ShapeTransform;
import org.thirdreality.evolvinghorizons.guinness.gui.Viewport;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;

public class MouseUtility
{
	// The variable is used to calculate the mouse speed below.
	private Point initialCursorLocation = null;

	private boolean measureDistanceMoved = true;
	
	// Keeps the current relative location of the Viewport from the cursor.
	// Assumed to be at (0|0) in the beginning but is refreshed afterwards.
	private Point cursorLocation;
	
	// Self-explaining: the mouse distance moved / delta time (px/ms).
	private volatile double mouseSpeed = 0d;
	
	/* 
	 * The 'action' variable below tells how the user interacts with the components on the Display.
	 * 
	 * Explanation of the states:
	 * 
	 * false = move
	 * true = click
	 * null = no action or reaction from the mouse.
	 */
	private Boolean action = null;

	// The total delta time (see updateMouseVelocity) to measure the mouses velocity.
	float deltaTimer;

	double totalDistance;

	float maximumDeltaTime = 1/1000*20; // 20ms

	public MouseUtility()
	{
		cursorLocation = getCurrentCursorLocation();
	}

	@Deprecated
	// Calculates the mouse-related data with a delay of 10ms to have a difference,
	// e.g. the
	public void updateMouseData(float delta)
	{
		cursorLocation = getCurrentCursorLocation();

		deltaTimer += delta;

		if(measureDistanceMoved)
		{
			initialCursorLocation = cursorLocation;

			measureDistanceMoved = false;
		}
		else if(deltaTimer >= maximumDeltaTime)
		{
			double mouseDistanceMoved = initialCursorLocation.distance(cursorLocation);

			mouseSpeed = mouseDistanceMoved / delta;

			// Reset timer.
			deltaTimer = 0;

			measureDistanceMoved = true;
		}


	}
	
	// Returns the general and current mouse speed on screen.
	// It is related to no component or such.
	public double getMouseSpeed()
	{
		return mouseSpeed;
	}

	// Returns the absolute current cursor location.
	public Point getCurrentCursorLocation()
	{
		return new Point(Gdx.input.getX(), Gdx.input.getY());
	}
	
	// Tests if the cursor is on the position of a component.
	// Meaning: Tests whether the mouse cursor (relative to the Display) is inside the given component.
	// Returns 'false' if target is 'null'.
	public boolean isFocusing(Viewport source, GComponent target)
	{
		// If there is no component given or interaction is forbidden,
		// this method assumes no component was found,
		// pretending the cursor is not over a component.
		if(target == null || source == null || (target != null && !target.getLogic().isInteractionAllowed()) || !source.isContained(target))
		{
			return false;
		}

		/*
		 *  Below there are variables and control statements which calculate the correct position of a component,
		 *  regarding the offset and scale as well as origin location of the given Viewport.
		 *
		 * 	If you wouldn't regard these aspects / values,
		 *  there would be a huge difference between the real components position and what is displayed on screen.
		 */
		
		GIPoint originAppliedLoc = new GIPoint(target.getStyle().getPrimaryLook().getBounds().getLocation()).add(source.getOrigin());

		Polygon originApplied = ShapeTransform.movePolygonTo(target.getStyle().getPrimaryLook(), originAppliedLoc.toPoint());
		
		if(source.isSimulated())
		{
			return originApplied.contains(getCurrentCursorLocation());
		}
		else
		{
			if(target.getStyle().isMovableForViewport())
			{				
				Polygon offsetApplied = ShapeTransform.movePolygonTo(originApplied, originAppliedLoc.add(source.getOffset()).toPoint());
				
				if(target.getStyle().isScalableForViewport())
				{
					Polygon scaleApplied = ShapeTransform.scalePolygon(offsetApplied, source.getScale());
					
					return scaleApplied.contains(getCurrentCursorLocation());
				}
				else
				{
					return offsetApplied.contains(getCurrentCursorLocation());
				}
			}
			else
			{
				if(target.getStyle().isScalableForViewport())
				{
					Polygon scaleApplied = ShapeTransform.scalePolygon(originApplied, source.getScale());
					
					return scaleApplied.contains(getCurrentCursorLocation());
				}
				else
				{
					return originApplied.contains(getCurrentCursorLocation());
				}
			}
		}
	}

	public boolean isClickingLeft()
	{
		return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
	}

	@Deprecated
	// Tests if the user is clicking a component.
	public boolean isClickingLeft(Viewport source, GComponent component)
	{
		return isFocusing(source, component) && isClickingLeft();
	}
	
	// Returns the first component which is focused by the cursor.
	// Makes the UI more efficient by breaking at the first component already.
	// Returns null if there is no such component.
	public GComponent getFocusedComponent(Viewport source)
	{
		GComponent firstMatch = null;
		
		if(source != null)
		{
				for(GComponent selected : source.getComponentOutput())
				{
					boolean insideComponent = isFocusing(source, selected);
					
					// Returns the first component which is focused by the mouse cursor.
					if(insideComponent)
					{
						// Make sure, if the component is ignored / unfocusable it is not recognized by its click or hover behavior.
						if(selected.getLogic().isFocusable())
						{
							firstMatch = selected;
						}
						
						break;
					}
			
			}
		}
		
		
		// Returns the first component which is focused by the mouse cursor.
		return firstMatch;
	}
	
	// Checks whether the cursor is over any GUInness component.
	// Should be avoided if used too often because of performance reasons.
	public boolean isFocusingAny(Viewport source, ArrayList<String> exceptionalTypes)
	{
		GComponent focused = getFocusedComponent(source);
		
		boolean assigned = focused != null;
		
		for(String type : exceptionalTypes)
		{
			if(assigned && (focused.getType().contentEquals(type)))
			{
				return false;
			}
		}
		
		return assigned;
	}
}
