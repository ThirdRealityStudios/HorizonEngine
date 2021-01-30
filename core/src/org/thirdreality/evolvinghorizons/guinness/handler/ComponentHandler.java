package org.thirdreality.evolvinghorizons.guinness.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import org.thirdreality.evolvinghorizons.guinness.gui.Viewport;
import org.thirdreality.evolvinghorizons.guinness.gui.adapter.MouseUtility;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.input.GTextfield;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.GCheckbox;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GButton;
import org.thirdreality.evolvinghorizons.guinness.render.ColorScheme;

public class ComponentHandler implements InputProcessor
{
	private MouseUtility mouseUtility;

	private int keyDown, keyUp;
	private char keyTyped;

	private InputProcessor customInput;

	private Viewport viewport;

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

	private GTextfield textfieldFocused;

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

	private GButton buttonFocused;

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

		return flag;
	}

	private GButton lastlyHoveredButton;
	private GComponent lastlyFocused;

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