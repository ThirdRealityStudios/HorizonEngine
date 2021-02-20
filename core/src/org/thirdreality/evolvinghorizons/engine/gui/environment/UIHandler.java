package org.thirdreality.evolvinghorizons.engine.gui.environment;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.input.GTextfield;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.GCheckbox;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.GTickBoxList;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.GTickOption;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.GButton;
import org.thirdreality.evolvinghorizons.engine.gui.ColorScheme;
import org.thirdreality.evolvinghorizons.engine.io.MouseUtility;

public class UIHandler implements InputProcessor
{
	private int keyDown, keyUp;
	private char keyTyped;

	private MouseUtility mouseUtility;

	// Below are the components which have been saved temporarily.
	private GTickBoxList selectionBoxFocused;
	private GTickOption lastlyHoveredOption;

	private GTextfield textfieldFocused;

	private GButton buttonFocused;
	private GButton lastlyHoveredButton;

	private GComponent lastlyFocused;

	private UIScreen uiScreen;

	public UIHandler(UIScreen uiScreen)
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

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		GComponent focused = uiScreen.getFocusedComponent();

		if(focused == null)
		{
			return false;
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

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
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
					GTickBoxList listBox = (GTickBoxList) lastlyFocused;

					if(lastlyHoveredOption != null)
					{
						// Simply assume, after releasing the mouse button, the cursor still remain over the tick box.
						lastlyHoveredOption.setBackgroundColor(ColorScheme.selectionBoxHovered);

						if(!listBox.isMultipleChoice())
						{
							for(int i = 0; i < listBox.size(); i++)
							{
								GTickOption option = listBox.getOption(i);

								option.setSelected(false);
							}
						}

						lastlyHoveredOption.setSelected(!lastlyHoveredOption.isSelected());
					}

					break;
				}

				default:
				{

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
		GComponent focused = uiScreen.getFocusedComponent();

		if(focused == null)
		{
			return false;
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

		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY)
	{
		return false;
	}
}