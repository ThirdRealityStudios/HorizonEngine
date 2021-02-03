package org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list;

import java.io.File;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.feature.Path;
import org.thirdreality.evolvinghorizons.guinness.gui.adapter.MouseUtility;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.GStyle;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;
import org.thirdreality.evolvinghorizons.guinness.render.ColorScheme;

public class GSelectionListBox extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private boolean multipleChoice;

	private ArrayList<GSelectionOption> options;

	private int lastSelection = 0;

	private int selection = 0;

	public GSelectionListBox(Vector2 position, boolean multipleChoice, Font font)
	{
		super("selectionbox");

		getStyle().setFont(font);
		getStyle().setImage(new Texture(Gdx.files.internal(Path.ICON_FOLDER + File.separator + "check_sign.png")));

		getStyle().setBounds(new Rectangle(position.x, position.y, 0,0));

		getStyle().getBorderProperties().setBorderThicknessPx(1);
		getStyle().setPadding(8);

		options = new ArrayList<GSelectionOption>();

		this.multipleChoice = multipleChoice;
	}

	public void select(int option)
	{
		if(multipleChoice)
		{
			options.get(option).setSelected(!options.get(option).isSelected());
		}
		else
		{
			options.get(lastSelection).setSelected(false);
			options.get(option).setSelected(true);
		}
	}

	public void addOption(String text)
	{
		GSelectionOption option = new GSelectionOption(text);

		GlyphLayout firstOptionLayout = new GlyphLayout(getStyle().getFont().getBitmapFont(), text);

		float width = 0;

		float sizeTickBox = firstOptionLayout.height;

		Vector2 origin = getStyle().getPosition();
		Vector2 tickBoxPosition = new Vector2(origin).add(getStyle().getPadding(), 0);

		float padding = getStyle().getPadding();

		// Update
		for(int i = 0; i < options.size(); i++)
		{
			// Update tick box position for the next one.
			tickBoxPosition.y += padding;

			Rectangle tickBoxBounds = new Rectangle(tickBoxPosition.x, tickBoxPosition.y, sizeTickBox, sizeTickBox);
			option.setTickBox(tickBoxBounds);

			// Update tick box position for the next one.
			tickBoxPosition.y += tickBoxBounds.height;

			String currentText = getText(i);

			GlyphLayout optionLayout = new GlyphLayout(getStyle().getFont().getBitmapFont(), currentText);

			Rectangle textBounds = new Rectangle(tickBoxBounds.x + sizeTickBox + padding, tickBoxBounds.y, optionLayout.width, optionLayout.height);
			option.setTextBox(textBounds);

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

		if(!multipleChoice && options.size() > 0 && options.get(lastSelection) != null && options.get(lastSelection).isSelected() && option.isSelected())
		{
			options.get(lastSelection).setSelected(false);
		}

		options.add(option);
	}

	/*
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
	 */

	public String getText(int option)
	{
		return options.get(option).getText();
	}

	public boolean isSelected(int option)
	{
		return options.get(option).isSelected();
	}

	public boolean isJustHovered(int option)
	{
		return options.get(option).getTickBox().contains(MouseUtility.getCurrentCursorLocation());
	}

	public int size()
	{
		return options.size();
	}

	public GSelectionOption getOption(int option)
	{
		return options.get(option);
	}
}