package org.thirdreality.evolvinghorizons.engine.gui.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.input.textfield.GTextfield;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.checkbox.GCheckbox;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.tickbox.GTickBoxList;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.tickbox.field.GTickBoxField;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.button.GButton;
import org.thirdreality.evolvinghorizons.engine.gui.ColorScheme;
import org.thirdreality.evolvinghorizons.engine.io.MouseUtility;
import org.thirdreality.evolvinghorizons.engine.render.RenderSource;

import java.util.ArrayList;

public class UIScreenHandler implements InputProcessor
{
	private int keyDown, keyUp;
	private char keyTyped;

	private MouseUtility mouseUtility;

	// Below are the components which have been saved temporarily.
	private GTickBoxList selectionBoxFocused;
	private GTickBoxField lastlyHoveredOption;

	private GTextfield textfieldFocused;

	private GButton buttonFocused;
	private GButton lastlyHoveredButton;

	private GComponent lastlyFocused;

	private UIScreen uiScreen;
	protected float zoomAcceleration = 0;
	protected boolean allowFocusOnZoom = false;
	protected float delta = 0;

	public UIScreenHandler(UIScreen uiScreen)
	{
		this.uiScreen = uiScreen;
		this.mouseUtility = new MouseUtility();
	}

	@Override
	public boolean keyDown(int keycode)
	{
		this.keyDown = keycode;

		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		this.keyUp = keycode;

		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		this.keyTyped = character;

		if(textfieldFocused != null)
		{
			if(character != Input.Keys.UNKNOWN)
			{
				textfieldFocused.getValueManager().write(character);
			}
		}

		return false;
	}

	private Vector2 getProjectedCursor()
	{
		Vector3 projected = RenderSource.orthographicCamera.project(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

		return new Vector2(projected.x, projected.y);
	}

	int i = 0;

	ArrayList<Vector2> points = new ArrayList<Vector2>();

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		GComponent focused = uiScreen.getFocusedComponent(screenX, screenY);

		Vector2 projectedCursor = getProjectedCursor();

		screenX = (int) projectedCursor.x;
		screenY = (int) projectedCursor.y;

		if(focused == null)
		{
			return false;
		}

		if(focused instanceof GButton)
		{
			textfieldFocused = null;

			buttonFocused = (GButton) focused;

			buttonFocused.getStyle().setColor(ColorScheme.buttonClicked);
		}
		else if(focused instanceof GTextfield)
		{
			textfieldFocused = (GTextfield) focused;
		}
		else if(focused instanceof GCheckbox)
		{
			textfieldFocused = null;

			GCheckbox checkboxFocused = (GCheckbox) focused;

			checkboxFocused.getStyle().setColor(ColorScheme.checkboxPressed);
		}
		else if(focused instanceof GTickBoxList)
		{
			if (lastlyHoveredOption != null)
			{
				lastlyHoveredOption.setBackgroundColor(ColorScheme.selectionBoxClicked);
			}
		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		Vector2 projectedCursor = getProjectedCursor();

		screenX = (int) projectedCursor.x;
		screenY = (int) projectedCursor.y;

		if(lastlyFocused != null)
		{
			if(lastlyFocused instanceof GButton)
			{
				lastlyFocused.getActionListener().onClick();

				GButton lastlyFocusedButton = (GButton) lastlyFocused;

				// Reset the appearance of the button after it was unfocused.
				lastlyFocusedButton.getStyle().resetAppearance();
			}
			if(lastlyFocused instanceof GCheckbox)
			{
				textfieldFocused = null;

				GCheckbox checkbox = (GCheckbox) lastlyFocused;

				checkbox.getStyle().setColor(ColorScheme.checkboxFg);

				checkbox.setChecked(!checkbox.isChecked());
			}
			if(lastlyFocused instanceof GTickBoxList)
			{
				GTickBoxList listBox = (GTickBoxList) lastlyFocused;

				if(lastlyHoveredOption != null)
				{
					// Simply assume, after releasing the mouse button, the cursor still remain over the tick box.
					lastlyHoveredOption.setBackgroundColor(ColorScheme.selectionBoxHovered);

					if(!listBox.isMultipleChoice())
					{
						for(int i = 0; i < listBox.size(); i++)
						{
							GTickBoxField option = listBox.getOption(i);

							option.setSelected(false);
						}
					}

					lastlyHoveredOption.setSelected(!lastlyHoveredOption.isSelected());
				}
			}
		}

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		if(lastlyHoveredOption != null)
		{
			lastlyHoveredOption.setBackgroundColor(null);
			lastlyHoveredOption = null;
		}

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		GComponent focused = uiScreen.getFocusedComponent(screenX, screenY);

		Vector2 projectedCursor = getProjectedCursor();

		screenX = (int) projectedCursor.x;
		screenY = (int) projectedCursor.y;

		if(focused == null)
		{
			if(lastlyHoveredButton != null)
			{
				// Resets the appearance of the button after it was unfocused ('focused == null').
				lastlyHoveredButton.getStyle().resetAppearance();
			}

			return false;
		}

		if(focused instanceof GButton)
		{
			buttonFocused = (GButton) focused;

			buttonFocused.getStyle().setColor(ColorScheme.buttonHovered);

			lastlyHoveredButton = buttonFocused;
		}
		else if(focused instanceof GTickBoxList)
		{
			selectionBoxFocused = (GTickBoxList) focused;

			Rectangle tickBoxBounds;

			if(lastlyHoveredOption != null)
			{
				lastlyHoveredOption.setBackgroundColor(null);
				lastlyHoveredOption = null;
			}

			for(int i = 0; i < selectionBoxFocused.size(); i++)
			{
				tickBoxBounds = selectionBoxFocused.getOption(i).getTickBox();

				// See if the user hovered the current selection option.
				if(selectionBoxFocused.isJustHovered(i) && selectionBoxFocused.getOption(i).getBackgroundColor() == null)
				{
					selectionBoxFocused.getOption(i).setBackgroundColor(ColorScheme.selectionBoxHovered);

					lastlyHoveredOption = selectionBoxFocused.getOption(i);
				}
			}
		}

		if(focused != lastlyFocused)
		{
			if(buttonFocused != null)
			{
				buttonFocused.getStyle().setColor(ColorScheme.buttonFg);
			}
		}

		lastlyFocused = focused;

		return false;
	}

	private void navigateToCursor()
	{
		Vector3 camPosition = RenderSource.orthographicCamera.position;

		int zoomLevel = (int) RenderSource.orthographicCamera.zoom;

		Vector2 cursorDirection = uiScreen.getCursorDirection().scl(zoomAcceleration * zoomLevel).scl(delta);

		// This will adjust the zoom speed to the real distance travelled per time.
		// The factor "zoomSpeed" makes the zoom-in work properly in relation to the distance travelled.
		RenderSource.orthographicCamera.position.x -= cursorDirection.x;
		RenderSource.orthographicCamera.position.y += cursorDirection.y;
	}

	@Override
	public boolean scrolled(float amountX, float amountY)
	{
		float zoom = RenderSource.orthographicCamera.zoom;

		int zoomLevel = (int) zoom;

		float speed = zoomAcceleration * amountY * delta;

		zoom += speed * zoomLevel;

		// Zoom level may not be below 100% as this causes render bugs.
		if(zoom >= 1)
		{
			RenderSource.orthographicCamera.zoom = zoom;

			// Make sure it will only focus on the cursor when the zoom level is beyond 100% and the user zooms in.
			// This will make the zoom feature to zoom in to the point you want to go to with your mouse.
			// The zoom-in focus feature will only if enabled (see method allowFocusOnZoom(...)).
			if(amountY < 0 && allowFocusOnZoom)
			{
				navigateToCursor();
			}
		}

		return false;
	}
}