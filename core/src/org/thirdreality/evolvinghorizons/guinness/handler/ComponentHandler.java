package org.thirdreality.evolvinghorizons.guinness.handler;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Cursor;
import org.thirdreality.evolvinghorizons.guinness.exec.ThreadManager;
import org.thirdreality.evolvinghorizons.guinness.feature.GIPoint;
import org.thirdreality.evolvinghorizons.guinness.feature.Timer;
import org.thirdreality.evolvinghorizons.guinness.feature.shape.ShapeTransform;
import org.thirdreality.evolvinghorizons.guinness.gui.Viewport;
import org.thirdreality.evolvinghorizons.guinness.gui.adapter.KeyUtility;
import org.thirdreality.evolvinghorizons.guinness.gui.adapter.MouseUtility;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.input.GTextfield;
import org.thirdreality.evolvinghorizons.guinness.gui.component.placeholder.GWindow;
import org.thirdreality.evolvinghorizons.guinness.gui.component.placeholder.window.GWindowButton;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.GCheckbox;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list.GSelectionBox;
import org.thirdreality.evolvinghorizons.guinness.handler.componenthandler.ComponentSession;

public class ComponentHandler
{
	private ThreadManager hoverTManager, clickTManager;

	private KeyUtility keyUtility;
	private MouseUtility mouseUtility;
	
	// This stores all sessions of necessary components which have their own component handling but want it being managed through one "main thread", meaning this class.
	// Using sessions can save here the use of too many threads because one thread takes over multiple component sessions.
	// Notice: The "main session" is always accessible through the index 0 (zero).
	// 		   It is responsible for treating all general components of the type GComponent within the main Viewport.
	private ArrayList<ComponentSession> sessions;

	public ComponentHandler(InputProcessor input)
	{
		this.keyUtility = new KeyUtility(input);

		// Assigns the modified input processor to the global LibGDX context.
		Gdx.input.setInputProcessor(input);

		this.mouseUtility = new MouseUtility();

		final int maximumThreads = 2;

		this.hoverTManager = new ThreadManager(maximumThreads);
		this.clickTManager = new ThreadManager(maximumThreads);
		
		sessions = new ArrayList<ComponentSession>();
		
		// Creates the first component session at index 0 (zero).
		// It will be responsible for treating all general components of the type "GComponent".
		// All following sessions after this will be responsible for other purposes, e.g. for treating components within a GWindow in a different Viewport.
		sessions.add(new ComponentSession());
	}

	// Updates selected / marked changes if there are any.
	// This ensures, all added layers are also displayed later.
	// This is in the end a safe method, meaning it checks whether 'target' is 'null'.
	public void updateChangedLayers(Viewport target)
	{
		if(target != null)
		{
			if(target.getLayerModifications() > 0)
			{
				target.updateComponentBuffer();
				target.outputComponentBuffer();
			}
		}
	}

	private void executeClick(final GComponent execute)
	{
		// Make sure, actions are only triggered when they are defined (listener is != null).
		if(execute.hasActionListener())
		{
			if(execute.getLogic().isMultithreadingOn())
			{
				Thread t = new Thread() // Run this task parallel so execution doesn't interfere other components or
				{						// interactions with the UI.
					
					@Override
					public void run()
					{
						execute.getActionListener().onClick();
					}
				};

				clickTManager.fire(t);
			}
			else
			{
				execute.getActionListener().onClick();
			}

			Timer.pauseMillisecond(execute.getLogic().getDelayMs());
		}
	}

	private void executeHover(final GComponent execute)
	{
		// Make sure, actions are only triggered when they are defined (listener is != null).
		if(execute.hasActionListener())
		{
			if(execute.getLogic().isMultithreadingOn())
			{
				Thread t = new Thread() // Run this task parallel so execution doesn't interfere other components or
										// interactions with the UI.
				{
					@Override
					public void run()
					{
						execute.getActionListener().onHover();
					}
				};

				hoverTManager.fire(t);
			}
			else
			{
				execute.getActionListener().onHover();
			}

			Timer.pauseMillisecond(execute.getLogic().getDelayMs());
		}
	}

	private Point initialLoc = null;

	// Is responsible for firing the implemented functions by the component.
	private void triggerGeneralLogic(ComponentSession session, Viewport source, GComponent focused, boolean clicking, Point mouseLocation, int keyStroke)
	{
		if(clicking)
		{
			// relates to text-fields only.
			{
				boolean canTextfieldBeFocussed = focused != null && focused.getType().contentEquals("textfield") && focused.getLogic().isInteractionAllowed() && focused.getLogic().isActingOnClick();

				if(canTextfieldBeFocussed)
				{
					session.setFocusedTextfield((GTextfield) focused);
				}

				boolean shouldDefocusIt = focused != session.getFocusedTextfield();

				if(shouldDefocusIt)
				{
					session.setFocusedTextfield(null);
				}
			}
		}

		// This is the actual part where text-fields are modified, meaning the value or
		// text it contains gets changed.
		// If there is no key delivered (KeyEvent.VK_UNDEFING), this part is ignored
		// for faster execution.
		if(session.getFocusedTextfield() != null && !(keyStroke == KeyEvent.VK_UNDEFINED) && focused != null && focused.getLogic().isInteractionAllowed() && focused.getLogic().isActingOnClick())
		{
			boolean isDeviceControlCode = keyUtility.isDeviceControlCode(keyStroke);

			if(isDeviceControlCode && !session.getFocusedTextfield().getValueManager().isCursorAtEnd())
			{
				session.getFocusedTextfield().getValueManager().write((char) keyStroke);
			}
			else
			{
				switch(keyStroke)
				{
					case KeyEvent.VK_BACK_SPACE:
					{
						if(!session.getFocusedTextfield().getValueManager().isCursorAtBeginning())
						{
							session.getFocusedTextfield().getValueManager().eraseLastChar();
						}

						break;
					}
				}
			}
		}

		if(focused != null)
		{
			if(focused.getLogic().isInteractionAllowed() && focused.getLogic().isActingOnHover()) // ask whether it should run the onHover() method if wished by the components configuration.
			{
				// This will decide internally whether the component is being executed by threads or in sequence order.
				executeHover(focused);
			}

			boolean isClickingAllowed = clicking && focused.getLogic().isInteractionAllowed() && focused.getLogic().isActingOnClick();

			if(isClickingAllowed) // ask whether it should run the onClick() method depending on whether it is wanted to interact or click on it.
			{
				// Interactions which do not regard whether it is double clicked
				switch(focused.getType())
				{
					case "window":
					{
						GWindow window = (GWindow) focused;

						/*
						 * The GWindow currently only supports offsets yet delivered by the corresponding Viewport.
						 */

						GIPoint offset = window.getStyle().isMovableForViewport() ? new GIPoint(source.getOffset()) : new GIPoint();

						Polygon outerArea = window.getStyle().getPrimaryLook();

						outerArea = ShapeTransform.movePolygonTo(outerArea, offset.copy().add(outerArea.getBounds().getLocation()).toPoint());

						Polygon innerArea = window.getStyle().getSecondaryLook();

						innerArea = ShapeTransform.movePolygonTo(innerArea, offset.copy().add(innerArea.getBounds().getLocation()).toPoint());

						Polygon exitButtonArea = window.getExitButton().getStyle().getPrimaryLook();

						exitButtonArea = ShapeTransform.movePolygonTo(exitButtonArea, offset.copy().add(exitButtonArea.getBounds().getLocation()).toPoint());

						Polygon minimizeButtonArea = window.getMinimizeButton().getStyle().getPrimaryLook();

						minimizeButtonArea = ShapeTransform.movePolygonTo(minimizeButtonArea, offset.copy().add(minimizeButtonArea.getBounds().getLocation()).toPoint());

						boolean focusedWindowBorderFirstTime = this.initialLoc == null && clicking && !innerArea.contains(mouseLocation) && !exitButtonArea.contains(mouseLocation) && !minimizeButtonArea.contains(mouseLocation);					

						if(focusedWindowBorderFirstTime)
						{
							this.initialLoc = mouseLocation;
						}

						boolean aboutToMoveWindowYet = initialLoc != null;

						if(aboutToMoveWindowYet)
						{
							GIPoint cursorDiff = new GIPoint(mouseLocation).sub(initialLoc);

							GIPoint moved = new GIPoint(window.getStyle().getLocation()).add(cursorDiff);//.div(target.getScale(), window.getStyle().isScalableForViewport());

							window.getStyle().setLocation(moved.toPoint());

							this.initialLoc = mouseLocation;
						}

						break;
					}
				}

				boolean isDoubleClickingWanted = !session.isFocusedComponentDoubleClicked() || focused.getLogic().isDoubleClickingAllowed();

				// Make sure the user cannot double click the same component multiple times if it is unwanted.
				if(isDoubleClickingWanted)
				{
					switch(focused.getType())
					{
						// Additionally check-boxes are treated here.
						// This will simply enable or disable the check-box this is about..
						case "checkbox":
						{
							GCheckbox checkbox = (GCheckbox) focused;

							// Just invert the current setting.
							checkbox.setChecked(!checkbox.isChecked());

							break;
						}

						case "selectionbox":
						{
							GSelectionBox selectionbox = (GSelectionBox) focused;

							ArrayList<Polygon[]> shapeTable = selectionbox.getShapeTable();

							for(int i = 0; i < shapeTable.size(); i++)
							{
								Point offset = source != null ? source.getOffset() : new Point();
								
								Point viewportRelative = new GIPoint(offset).add(source.getOrigin()).toPoint();
								
								Polygon rect0 = shapeTable.get(i)[0];
								Polygon rect2 = shapeTable.get(i)[2];
								
								Point pos0 = new GIPoint(rect0.getBounds().getLocation()).add(viewportRelative).toPoint();

								Point pos2 = new GIPoint(rect2.getBounds().getLocation()).add(viewportRelative).toPoint();
								
								boolean isViewportAvailable = source != null;
								
								float scale = isViewportAvailable && focused.getStyle().isScalableForViewport() ? source.getScale() : 1f;

								// Creates two moved and scaled copies (by the global offset and scale factor).
								Polygon transformed0 = ShapeTransform.scalePolygon(ShapeTransform.movePolygonTo(rect0, pos0), scale);
								Polygon transformed2 = ShapeTransform.scalePolygon(ShapeTransform.movePolygonTo(rect2, pos2), scale);
								
								if(transformed0.contains(mouseLocation) || transformed2.contains(mouseLocation))
								{
									selectionbox.selectOptionAt(i);
								}
							}
							
							break;
						}
					}

					// This will decide internally whether the component is being executed by threads or in sequence order.
					// It actually just runs the defined click action by the user.
					// It is executed here at the end to make sure changes by interacting with the components are recognized by the user defined click action.
					executeClick(focused);
				}
			}
			
			if(!clicking)
			{
				this.initialLoc = null;
			}
		}
	}

	@Deprecated
	private void resetLastFocus(ComponentSession session)
	{
		GComponent lastlyFocused = session.getLastlyFocusedComponent();

		// WORK ON THIS !
		// When hovering over something else the cursor is set to default.
		Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Crosshair);

		switch(lastlyFocused.getType())
		{		
			case "button":
			{
				lastlyFocused.getStyle().setPrimaryColor(lastlyFocused.getStyle().getDesign().getDesignColor().getBackgroundColor());

				break;
			}

			case "window":
			{
				GWindow window = (GWindow) lastlyFocused;

				window.getExitButton().getStyle().setPrimaryColor(window.getExitButton().getDefaultColor());
				window.getMinimizeButton().getStyle().setPrimaryColor(window.getMinimizeButton().getDefaultColor());

				break;
			}

			default:
			{
				
			}
		}
	}

	private void triggerWindowButtonColor(Viewport source, GWindowButton windowButton, Point mouseLocation, boolean clicking)
	{
		boolean activeColorIsSame = windowButton.getStyle().getPrimaryColor().equals(windowButton.getClickColor());
		boolean hoverColorIsSame = windowButton.getStyle().getPrimaryColor().equals(windowButton.getHoverColor());

		Point windowButtonLocation = new GIPoint(windowButton.getStyle().getLocation()).add(source.getOrigin()).add(source.getOffset()).toPoint();
		
		// Asking for
		if(ShapeTransform.movePolygonTo(windowButton.getStyle().getPrimaryLook(), windowButtonLocation).contains(mouseLocation))
		{
			if(clicking)
			{
				if(!activeColorIsSame)
				{
					windowButton.getStyle().setPrimaryColor(windowButton.getClickColor());
				}
			}
			else if(!hoverColorIsSame)
			{
				windowButton.getStyle().setPrimaryColor(windowButton.getHoverColor());
			}
		}
		else
		{
			windowButton.getStyle().setPrimaryColor(windowButton.getDefaultColor());
		}
	}

	private void triggerAnimation(ComponentSession session, Viewport source, GComponent focused, boolean clicking, Point mouseLocation)
	{
		GComponent lastlyFocused = session.getLastlyFocusedComponent();
		
		boolean sameComponentFocused = lastlyFocused != focused && lastlyFocused != null;
		
		if(focused != null)
		{
			switch(focused.getType())
			{
				case "button":
				{
					// The next two booleans prevent the redraw algorithm to run again if there was
					// no change in color..
					boolean activeColorIsSame = focused.getStyle().getPrimaryColor().equals(focused.getStyle().getDesign().getDesignColor().getActiveColor());
					boolean hoverColorIsSame = focused.getStyle().getPrimaryColor().equals(focused.getStyle().getDesign().getDesignColor().getHoverColor());
					
					if(clicking)
					{
						if(!activeColorIsSame)
						{
							focused.getStyle().setPrimaryColor(focused.getStyle().getDesign().getDesignColor().getActiveColor());
						}
					}
					else if(!hoverColorIsSame)
					{
						focused.getStyle().setPrimaryColor(focused.getStyle().getDesign().getDesignColor().getHoverColor());

						// When hovering (once!) over a button the cursor is changed.
						Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
					}

					break;
				}
				case "textfield":
				{
					if(!session.isFocusedComponentDoubleHovered())
					{
						// When hovering over a text-field the cursor is changed.
						Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Ibeam);
					}

					break;
				}
				
				case "window":
				{
					GWindow window = (GWindow) focused;
					
					// Will update the window content every time the user focuses the window.
					// This will also reduce the CPU usage because it is event driven then.
					// Anyway, this might be changed in future so the window content is always changed.
					if(window.hasViewport())
					{
						updateChangedLayers(window.getViewport());
					}
					
					triggerWindowButtonColor(source, window.getExitButton(), mouseLocation, clicking);
					triggerWindowButtonColor(source, window.getMinimizeButton(), mouseLocation, clicking);

					break;
				}

				default:
				{
					// Make sure the 'default' branch is executed only once.
					if(sameComponentFocused)
					{
						resetLastFocus(session);
					}
				}
			}
		}
		else
		{
			if(lastlyFocused != null)
			{
				resetLastFocus(session);
			}
		}
	}

	private void preEvaluateEvents(ComponentSession session, GComponent focused)
	{
		if(session.getYetHoveredComponent() == focused)
		{
			session.setFocusedComponentDoubleHovered(true);
		}
		else
		{
			session.setFocusedComponentDoubleHovered(false);
		}
		
		if(session.getYetClickedComponent() == focused)
		{
			session.setFocusedComponentDoubleClicked(true);
		}
	}

	private void postEvaluateEvents(ComponentSession session, boolean clicking, GComponent focused)
	{
		if(clicking)
		{
			session.setYetClickedComponent(focused);
		}
		else
		{
			session.setYetClickedComponent(null);
			session.setFocusedComponentDoubleClicked(false);
		}

		session.setYetHoveredComponent(focused);
	}

	// Looks up the session which matches the corresponding Viewport.
	private ComponentSession loadSession(Viewport context)
	{		
		// Looking here for an existing component session.
		for(ComponentSession session : sessions)
		{
			if(session.getTrackedViewport() == context)
			{
				return session;
			}
		}
		
		// If execution goes until here, no corresponding session was found above.
		
		ComponentSession newlyCreatedSession = null;
		
		// Now it will look whether the Viewport is simulated.
		// In this case, the Viewport receives its own session, as multiple GWindows should have their own component handling independent of the Displays Viewport.
		if(context.isSimulated())
		{
			// Creates a new ComponentSession which will keep track of the components of the target Viewport.
			newlyCreatedSession = new ComponentSession(context);

			// Adds the newly created session to the list. In the next cycle it will automatically be detected.
			sessions.add(newlyCreatedSession);
		}
		else
		{
			// Uses the default session which belongs to the Displays Viewport.
			// It will also be used in the next cycle.
			newlyCreatedSession = sessions.get(0);
		}
		
		return newlyCreatedSession;
	}

	@Deprecated
	// This will trigger the component where the user has performed an action at.
	// Anyway, keep in mind that a component can only be triggered if it is also enabled (see 'isEnabled()' at GComponent).
	public void triggerComponent(Viewport target)
	{
		ComponentSession session = loadSession(target);

		// WORK ON BOTH BELOW !

		GComponent focused = mouseUtility.getFocusedComponent(target);

		Point mouseLocation = new Point(Gdx.input.getX(), Gdx.input.getY());

		boolean windowWasMoved = initialLoc != null;

		// Make sure if one window was focused before, the focus does NOT get lost.
		// Otherwise, if you move a window the focus could get lost.
		// This happens usually when the mouse cursor goes beyond the window borders.
		// In this case, the focus would get lost if you wouldn't consider this event here.
		if(windowWasMoved)
		{
			focused = session.getLastlyFocusedComponent();
		}

		/*
		 *  WARNING! The codtriggerComponente below must be executed only under certain circumstances ! ! !
		 *  
		 *  Make sure the UI is only treated / handled when the component is also enabled if you create your own component type (modification).
		 */
		if(focused != null && !focused.isEnabled())
		{
			preEvaluateEvents(session, focused);

			// Pretend there was no component detected.
			postEvaluateEvents(session, false, null);

			// The remaining part of the code is not executed.
			return;
		}

		// If the execution goes until here, it will be triggered in the next steps.

		preEvaluateEvents(session, focused);

		boolean clicking = Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT);

		// This line means if the KeyAdapter is active, then only read the currently
		// pressed key from it.
		// This is because the KeyAdapter is only available (!= null) when it is
		// necessary to save resources on the CPU.
		// Anyway, in Gaming Mode (see definition of it in LayeredDisplay.java for
		// reference) the KeyAdapter is always initialized and available.
		int keyStroke = keyUtility.getKeyTyped();

		triggerGeneralLogic(session, target, focused, clicking, mouseLocation, keyStroke);
		triggerAnimation(session, target, focused, clicking, mouseLocation);

		postEvaluateEvents(session, clicking, focused);

		session.setLastlyFocusedComponent(focused);

		// Evaluates a possible GWindow.
		evaluateWindowComponents(focused);
	}
	
	// Evaluates all components in a GWindow if the given component is one.
	private void evaluateWindowComponents(GComponent possibleWindow)
	{
		// The session is actually closed from this point. No further changes are applied anymore..
		// From this point it will only check whether there are other components (subroutines) which have to be run,
		// e.g. handling the Viewport of a GWindow (simulated Viewport / component environment).

		boolean windowFocused = possibleWindow != null && possibleWindow.getType().contentEquals("window");

		if(windowFocused)
		{
			GWindow window = (GWindow) possibleWindow;

			if(window.hasViewport())
			{
				// Evaluate all components within the GWindow..

				Viewport windowViewport = window.getViewport();

				for(GComponent component : windowViewport.getComponentOutput())
				{
					if(windowViewport.isContained(component))
					{
						triggerComponent(windowViewport);
					}
				}
			}
		}
	}
}