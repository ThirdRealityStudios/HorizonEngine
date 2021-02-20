package org.thirdreality.evolvinghorizons.engine.io;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.render.screen.UIScreen;

public class MouseUtility
{
	// The variable is used to calculate the mouse speed below.
	private Vector2 initialCursorLocation = null;

	private boolean measureDistanceMoved = true;
	
	// Keeps the current relative location of the Viewport from the cursor.
	// Assumed to be at (0|0) in the beginning but is refreshed afterwards.
	private Vector2 cursorLocation;
	
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
		cursorLocation = new Vector2();
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
			double mouseDistanceMoved = initialCursorLocation.dst(cursorLocation);

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
	public static Vector2 getCurrentCursorLocation()
	{
		return new Vector2(Gdx.input.getX(), -1 * (Gdx.input.getY() - Gdx.graphics.getHeight()));
	}

	public static boolean isClickingLeft()
	{
		return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
	}

	@Deprecated
	// Tests if the user is clicking a component.
	public static boolean isClickingLeft(UIScreen source, GComponent component)
	{
		return source.isFocusing(component) && isClickingLeft();
	}
	

}
