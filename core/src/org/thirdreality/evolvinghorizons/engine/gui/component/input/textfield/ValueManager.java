package org.thirdreality.evolvinghorizons.engine.gui.component.input.textfield;

public abstract class ValueManager
{
	private int maxLength = 0;

	protected volatile String value = "";

	private int cursorIndex = 0;

	// Determines where values should be modified at.
	public void setCursor(int cursorIndex)
	{
		this.cursorIndex = cursorIndex;
	}

	// Will write add a new char in the variable 'value' of type String.
	// It will save the value before in the buffer.
	public void write(char key)
	{
		boolean noOverflow = (getValue().length() + 1) <= getMaxLength();

		StringBuilder modifiedValue = new StringBuilder(getValue());

		if(noOverflow)
		{
			boolean clickedLastCharAtTheEnd = cursorIndex == getValue().length();

			if(clickedLastCharAtTheEnd)
			{
				modifiedValue.append(key);
			}
			else
			{
				modifiedValue.insert(cursorIndex, key);
			}

			setValue(new String(modifiedValue));

			cursorIndex++;
		}
	}

	// Will do the exact opposite of the write(char key) function.
	// It will delete the last char in the variable 'value' of type String.
	// It will save the value before in the buffer.
	public void eraseChar()
	{
		// Checking whether deleting one more char is still possible due to the length
		// of 'value'.
		if(getValue().length() > 0)
		{
			StringBuilder modifiedValue = new StringBuilder(getValue());

			if(cursorIndex > 0)
			{
				cursorIndex--;
			}
			else
			{
				return;
			}

			modifiedValue.deleteCharAt(cursorIndex);

			setValue(new String(modifiedValue));
		}
	}

	public synchronized String getValue()
	{
		return value;
	}

	// Is used to set the input value of the text-field using this class.
	public abstract void setValue(String val);

	// Returns the limit of input values per character.
	public int getMaxLength()
	{
		return maxLength;
	}

	public void setMaxLength(int length)
	{
		this.maxLength = length;
	}
}
