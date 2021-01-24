package org.thirdreality.evolvinghorizons.guinness.gui.adapter;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class KeyUtility implements InputProcessor
{
	private int downKey = Input.Keys.UNKNOWN;
	private int upKey = Input.Keys.UNKNOWN;

	private char typedKey = Input.Keys.UNKNOWN;

	private InputProcessor input;

	public KeyUtility(InputProcessor input)
	{
		this.input = input;
	}

	// Checks whether the given key is a control code,
	// e.g. alert or 0 (device should do nothing).
	public boolean isDeviceControlCode(int key)
	{
		return key > 31 && key < 127 || key > 127 && key < 65535;
	}

	public int getKeyDown()
	{
		return downKey;
	}

	public int getKeyUp()
	{
		return upKey;
	}

	public int getKeyTyped()
	{
		return typedKey;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		this.downKey = keycode;

		return input.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode)
	{
		this.upKey = keycode;

		return input.keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character)
	{
		this.typedKey = character;

		return input.keyTyped(character);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return input.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return input.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return input.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return input.mouseMoved(screenX, screenY);
	}

	@Override
	public boolean scrolled(float amountX, float amountY)
	{
		return input.scrolled(amountX, amountY);
	}
}