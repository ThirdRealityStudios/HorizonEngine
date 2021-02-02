package org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.GStyle;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;

public class GSelectionBox extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private ArrayList<String> options;

	private boolean multipleChoice;

	// For multiple-choice
	private ArrayList<Boolean> selections;

	private ArrayList<Rectangle> tickBoxBounds, textBounds;

	private int selection = 0;

	public GSelectionBox(Vector2 position, boolean multipleChoice, Font font)
	{
		super("selectionbox");

		getStyle().setFont(font);

		getStyle().setBounds(new Rectangle(position.x, position.y, 0,0));

		getStyle().getBorderProperties().setBorderThicknessPx(1);
		getStyle().setPadding(20);

		options = new ArrayList<String>();

		this.multipleChoice = multipleChoice;

		if(multipleChoice)
		{
			selections = new ArrayList<Boolean>();
		}

		tickBoxBounds = new ArrayList<Rectangle>();
		textBounds = new ArrayList<Rectangle>();
	}

	public void select(int option)
	{
		if(multipleChoice)
		{
			selections.set(option, !selections.get(option));
		}
		else
		{
			selections.set(selection, false);
			selections.set(option, true);
		}
	}

	public void addOption(String text)
	{
		options.add(text);

		GlyphLayout firstOptionLayout = new GlyphLayout(getStyle().getFont().getBitmapFont(), text);

		float width = 0;

		float sizeTickBox = firstOptionLayout.height;

		tickBoxBounds.clear();
		textBounds.clear();

		Vector2 origin = getStyle().getPosition();
		Vector2 tickBoxPosition = new Vector2(origin).add(getStyle().getPadding(), 0);

		float padding = getStyle().getPadding();

		// Update
		for(int i = 0; i < options.size(); i++)
		{
			// Update tick box position for the next one.
			tickBoxPosition.y += padding;

			Rectangle tickBoxBounds = new Rectangle(tickBoxPosition.x, tickBoxPosition.y, sizeTickBox, sizeTickBox);
			this.tickBoxBounds.add(tickBoxBounds);

			// Update tick box position for the next one.
			tickBoxPosition.y += tickBoxBounds.height;

			String currentText = getText(i);

			GlyphLayout optionLayout = new GlyphLayout(getStyle().getFont().getBitmapFont(), currentText);

			Rectangle textBounds = new Rectangle(tickBoxBounds.x + sizeTickBox + padding, tickBoxBounds.y, optionLayout.width, optionLayout.height);
			this.textBounds.add(textBounds);

			float inlineWidth = textBounds.x + textBounds.width + padding - origin.x;

			if(inlineWidth >= width)
			{
				width = inlineWidth;
			}
		}

		tickBoxPosition.y += getStyle().getPadding();

		float height = tickBoxPosition.y - origin.y;

		Rectangle gBoxBounds = new Rectangle(origin.x, origin.y, width, height);

		getStyle().setBounds(gBoxBounds);


		if(multipleChoice)
		{
			selections.add(false);
		}
	}

	public void removeOption(String text)
	{
		int index = options.indexOf(text);

		options.remove(index);

		if(multipleChoice)
		{
			selections.remove(index);
		}

		textBounds.remove(index);
	}

	public void removeOption(int option)
	{
		options.remove(option);

		if(multipleChoice)
		{
			selections.remove(option);
		}

		textBounds.remove(option);
	}

	public String getText(int option)
	{
		return options.get(option);
	}

	public boolean isSelected(int option)
	{
		if(multipleChoice)
		{
			return selections.get(option);
		}
		else
		{
			return option == selection;
		}
	}

	public int size()
	{
		return options.size();
	}

	public Rectangle getTickBoxBounds(int option)
	{
		return new Rectangle(tickBoxBounds.get(option));
	}

	public Rectangle getTextBounds(int option)
	{
		return new Rectangle(textBounds.get(option));
	}
}
