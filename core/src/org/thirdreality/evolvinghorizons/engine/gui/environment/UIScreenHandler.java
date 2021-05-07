package org.thirdreality.evolvinghorizons.engine.gui.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.input.textfield.GTextfield;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.checkbox.GCheckbox;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.tickbox.GTickBoxList;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.tickbox.field.GTickBoxField;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.button.GButton;
import org.thirdreality.evolvinghorizons.engine.gui.ColorScheme;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.polybutton.GPolyButton;
import org.thirdreality.evolvinghorizons.engine.io.MouseUtility;
import org.thirdreality.evolvinghorizons.engine.render.RenderSource;

public class UIScreenHandler implements InputProcessor
{
	private int keyDown, keyUp;
	private char keyTyped;

	private MouseUtility mouseUtility;

	// Below are the components which have been saved temporarily.
	private GTickBoxList selectionBoxFocused;
	private GTickBoxField lastlyHoveredOption;

	private GTextfield textfieldFocused;

	private GButton buttonSaved;

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
		// By checking for processed control keys in the program this prevents falsified input values.
		// The reason is, the char values retrieved from keyTyped(...) are different from the ones you would might want to compare with Input.Keys.*
		// As a consequence, the char value of backspace would be 8 instead of the integer value 67.
		// Hence, all control keys are checked separately with the method Gdx.input.isKeyPressed(...)
		if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE))
		{
			this.keyTyped = Input.Keys.UNKNOWN;
		}
		else
		{
			this.keyTyped = character;
		}

		return false;
	}

	// Updates all components. Needs to be called frequently by the UIScreen object.
	// Takes care especially of the input values retrieved from the keyboard which have an effect on the components.
	public void update()
	{
		if(textfieldFocused != null)
		{
			boolean backspace = Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE);

			// Makes sure, the text-field cursor is only being updated when the user performs a click.
			if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
			{
				textfieldFocused.getValueManager().setCursor(textfieldFocused.getSelectedCharByIndex());
			}

			if(backspace)
			{
				textfieldFocused.getValueManager().eraseChar();
			}
			else if(keyTyped != Input.Keys.UNKNOWN)
			{
				textfieldFocused.getValueManager().write(keyTyped);
			}
		}

		// Reset the key
		keyTyped = Input.Keys.UNKNOWN;
	}

	private Vector2 getProjectedCursor()
	{
		Vector3 projected = RenderSource.orthographicCamera.project(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

		return new Vector2(projected.x, projected.y);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		GComponent focused = uiScreen.getFocusedComponent(screenX, screenY);

		Vector2 projectedCursor = getProjectedCursor();

		screenX = (int) projectedCursor.x;
		screenY = (int) projectedCursor.y;

		if(focused == null)
		{
			textfieldFocused = null;
			buttonSaved = null;

			return false;
		}

		if(focused instanceof GButton)
		{
			textfieldFocused = null;

			buttonSaved = (GButton) focused;
			buttonSaved.getStyle().setColor(ColorScheme.buttonClicked);
		}
		else if(focused instanceof GTextfield)
		{
			textfieldFocused = (GTextfield) focused;

			buttonSaved = null;
		}
		else if(focused instanceof GCheckbox)
		{
			textfieldFocused = null;
			buttonSaved = null;

			GCheckbox checkboxFocused = (GCheckbox) focused;

			checkboxFocused.getStyle().setColor(ColorScheme.checkboxPressed);
		}
		else if(focused instanceof GTickBoxList)
		{
			textfieldFocused = null;
			buttonSaved = null;

			if (lastlyHoveredOption != null)
			{
				lastlyHoveredOption.setBackgroundColor(ColorScheme.selectionBoxClicked);
			}
		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int mouseButton)
	{
		Vector2 projectedCursor = getProjectedCursor();

		screenX = (int) projectedCursor.x;
		screenY = (int) projectedCursor.y;

		if(buttonSaved != null)
		{
			textfieldFocused = null;

			buttonSaved.getActionListener().onClick();

			// Reset the appearance of the button after it was unfocused.
			buttonSaved.getStyle().resetAppearance();
		}

		if(lastlyFocused != null)
		{
			if(lastlyFocused instanceof GCheckbox)
			{
				textfieldFocused = null;
				buttonSaved = null;

				GCheckbox checkbox = (GCheckbox) lastlyFocused;

				checkbox.getStyle().setColor(ColorScheme.checkboxFg);

				checkbox.setChecked(!checkbox.isChecked());
			}
			else if(lastlyFocused instanceof GTickBoxList)
			{
				textfieldFocused = null;
				buttonSaved = null;

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

	private void resetSavedButton()
	{
		if(buttonSaved != null)
		{
			// Resets the appearance of the button (when not hovering over it anymore).
			buttonSaved.getStyle().resetAppearance();

			buttonSaved = null;
		}
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		GComponent currentComponent = uiScreen.getFocusedComponent(screenX, screenY);

		if(currentComponent == null)
		{
			resetSavedButton();
		}

		if(currentComponent instanceof GButton)
		{
			buttonSaved = (GButton) currentComponent;

			buttonSaved.getStyle().setColor(ColorScheme.buttonHovered);
		}
		else if(currentComponent instanceof GTickBoxList)
		{
			selectionBoxFocused = (GTickBoxList) currentComponent;

			if(lastlyHoveredOption != null)
			{
				lastlyHoveredOption.setBackgroundColor(null);
				lastlyHoveredOption = null;
			}

			for(int i = 0; i < selectionBoxFocused.size(); i++)
			{
				// See if the user hovered the current selection option.
				if(selectionBoxFocused.isJustHovered(i) && selectionBoxFocused.getOption(i).getBackgroundColor() == null)
				{
					selectionBoxFocused.getOption(i).setBackgroundColor(ColorScheme.selectionBoxHovered);

					lastlyHoveredOption = selectionBoxFocused.getOption(i);
				}
			}
		}
		else if(currentComponent instanceof GPolyButton)
		{
			resetSavedButton();
		}

		lastlyFocused = currentComponent;

		return false;
	}

	private void navigateToCursor()
	{
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