package org.thirdreality.evolvinghorizons.guinness.gui.component;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.guinness.gui.Viewport;
import org.thirdreality.evolvinghorizons.guinness.gui.adapter.MouseUtility;
import org.thirdreality.evolvinghorizons.guinness.gui.component.input.GTextfield;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.GCheckbox;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list.GSelectionListBox;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list.GSelectionOption;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GButton;
import org.thirdreality.evolvinghorizons.guinness.render.ColorScheme;

public class ComponentHandler implements InputProcessor
{
	private int keyDown, keyUp;
	private char keyTyped;

	private InputProcessor customInput;
	private MouseUtility mouseUtility;
	private Viewport viewport;

	// Below are the components which have been saved temporarily.
	private GSelectionListBox selectionBoxFocused;
	private GSelectionOption lastlyHoveredOption;

	private GTextfield textfieldFocused;

	private GButton buttonFocused;
	private GButton lastlyHoveredButton;

	private GComponent lastlyFocused;

	public ComponentHandler(Viewport viewport, InputProcessor input)
	{
		customInput = input;

		this.viewport = viewport;

		this.mouseUtility = new MouseUtility();
	}

	public void setCurrentViewport(Viewport viewport)
	{
		this.viewport = viewport;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		boolean flag = customInput.keyDown(keycode);

		this.keyDown = keycode;

		return flag;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		boolean flag = customInput.keyUp(keycode);

		this.keyUp = keycode;

		return flag;
	}

	@Override
	public boolean keyTyped(char character)
	{
		boolean flag = customInput.keyTyped(character);

		this.keyTyped = character;

		if(textfieldFocused != null)
		{
			if(character != Input.Keys.UNKNOWN)
			{
				textfieldFocused.getValueManager().write(character);
			}
		}

		return flag;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		boolean flag = customInput.touchDown(screenX, screenY, pointer, button);

		GComponent focused = mouseUtility.getFocusedComponent(viewport);

		if(focused == null)
		{
			return flag;
		}

		switch(focused.getType())
		{
			case "button":
			{
				textfieldFocused = null;

				buttonFocused = (GButton) focused;

				buttonFocused.getStyle().setColor(ColorScheme.buttonClicked);

				break;
			}

			case "textfield":
			{
				textfieldFocused = (GTextfield) focused;

				break;
			}

			case "checkbox":
			{
				textfieldFocused = null;

				focused.getStyle().setColor(ColorScheme.checkboxPressed);

				break;
			}

			case "selectionbox":
			{
				Rectangle tickBoxBounds;

				if(lastlyHoveredOption != null)
				{
					lastlyHoveredOption.setBackgroundColor(ColorScheme.selectionBoxClicked);
				}

				break;
			}

			default:
			{

			}
		}

		return flag;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		boolean flag = customInput.touchUp(screenX, screenY, pointer, button);

		if(lastlyFocused != null)
		{
			switch(lastlyFocused.getType())
			{
				case "button":
				{
					lastlyFocused.getActionListener().onClick();

					lastlyFocused.getStyle().setColor(ColorScheme.buttonFg);

					break;
				}

				case "checkbox":
				{
					textfieldFocused = null;

					GCheckbox checkbox = (GCheckbox) lastlyFocused;

					checkbox.getStyle().setColor(ColorScheme.checkboxFg);

					checkbox.setChecked(!checkbox.isChecked());

					break;
				}

				case "selectionbox":
				{
					Rectangle tickBoxBounds;

					if(lastlyHoveredOption != null)
					{
						lastlyHoveredOption.setSelected(!lastlyHoveredOption.isSelected());

						// Simply assume, after releasing the mouse button, the cursor still remain over the tick box.
						lastlyHoveredOption.setBackgroundColor(ColorScheme.selectionBoxHovered);
					}

					break;
				}

				default:
				{

				}
			}
		}

		return flag;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		boolean flag = customInput.touchDragged(screenX, screenY, pointer);

		if(lastlyHoveredOption != null)
		{
			lastlyHoveredOption.setBackgroundColor(null);
			lastlyHoveredOption = null;
		}

		return flag;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		boolean flag = customInput.mouseMoved(screenX, screenY);

		GComponent focused = mouseUtility.getFocusedComponent(viewport);

		if(focused == null)
		{
			return flag;
		}

		switch(focused.getType())
		{
			case "button":
			{
				buttonFocused = (GButton) focused;

				buttonFocused.getStyle().setColor(ColorScheme.buttonHovered);

				lastlyHoveredButton = buttonFocused;

				break;
			}

			case "selectionbox":
			{
				selectionBoxFocused = (GSelectionListBox) focused;

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

				break;
			}

			default:
			{

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

		return flag;
	}

	@Override
	public boolean scrolled(float amountX, float amountY)
	{
		boolean flag = customInput.scrolled(amountX, amountY);

		return flag;
	}
}