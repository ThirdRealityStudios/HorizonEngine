package org.thirdreality.evolvinghorizons.engine.gui.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public abstract class ValueManager
{
	private int maxLength = 0;

	protected volatile String value = "";

	// Will write add a new char in the variable 'value' of type String.
	// It will save the value before in the buffer.
	public void write(char key)
	{
		boolean noOverflow = (getValue().length() + 1) <= getMaxLength();

		if(noOverflow)
		{
			for(char c : value.toCharArray())
			{
				System.out.print(c + ",");
			}

			System.out.println();

			setValue(getValue() + key);

			System.out.println("new length " + getValue().length());
		}
	}

	// Will do the exact opposite of the write(char key) function.
	// It will delete the last char in the variable 'value' of type String.
	// It will save the value before in the buffer.
	public void eraseLastChar()
	{
		// Checking whether deleting one more char is still possible due to the length
		// of 'value'.
		if(getValue().length() > 0)
		{
			value = value.substring(0, value.length()-1);

			System.out.println("new length " + getValue().length() + " [del]");

			/*
			setBufferedValue(getBufferedValue());

			char[] charValues = getValue().toCharArray();

			setValue(String.valueOf(charValues, 0, charValues.length - 1));
			*/
		}
	}

	public synchronized String getValue()
	{
		return value;
	}

	// The implementation depends on the type,
	// e.g. a text-field is treated differently than an image.
	public abstract void setValue(String val);

	public int getMaxLength()
	{
		return maxLength;
	}

	public void setMaxLength(int length)
	{
		this.maxLength = length;
	}
}
