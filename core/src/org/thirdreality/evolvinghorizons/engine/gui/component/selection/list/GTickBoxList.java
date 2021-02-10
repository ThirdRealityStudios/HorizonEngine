package org.thirdreality.evolvinghorizons.engine.gui.component.selection.list;

import java.io.File;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.Meta;
import org.thirdreality.evolvinghorizons.engine.io.Path;
import org.thirdreality.evolvinghorizons.engine.io.MouseUtility;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GTickBoxList extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	// As it says, you can determine in the beginning whether this list should accept multiple selections or only one at once.
	private final boolean multipleChoice;

	private ArrayList<GTickOption> options;

	// Remembers the last selected option (index for 'options' right above).
	// Is only used for the method addOption(...) .
	int lastSelection = 0;

	public GTickBoxList(Vector2 position, boolean multipleChoice, Font font)
	{
		super("selectionbox");

		getStyle().setFont(font);
		getStyle().setImage(new Texture(Gdx.files.internal(Path.ICON_FOLDER + File.separator + "check_sign.png")));

		getStyle().setBounds(new Rectangle(position.x, position.y, 0,0));

		getStyle().getBorderProperties().setBorderThicknessPx(1);
		getStyle().setPadding(8);

		options = new ArrayList<GTickOption>();

		this.multipleChoice = multipleChoice;
	}

	public void addOption(String text)
	{
		GTickOption option = new GTickOption(text);

		GlyphLayout firstOptionLayout = new GlyphLayout(getStyle().getFont().getBitmapFont(), text);

		// Will be used down in the for-loop to determine the possible largest width for this GTickBoxList.
		// The value will further depend on the font size, text length, tick box size and border-related settings,
		// such as the padding.
		// The border-thickness will not have an effect here as it is not used and meant to have such an effect on the bounds of something.
		float width = 0;

		float sizeTickBox = firstOptionLayout.height;

		Vector2 origin = getStyle().getPosition();
		Vector2 tickBoxPosition = new Vector2(origin).add(getStyle().getPadding(), 0);

		float padding = getStyle().getPadding();

		// Updates all corresponding bounds for the tick boxes and their related texts.
		// Also updates the outer bounds (background size and position) of this GTickBoxList.
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

	public String getText(int option)
	{
		return options.get(option).getText();
	}

	public boolean isMultipleChoice()
	{
		return multipleChoice;
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

	public GTickOption getOption(int option)
	{
		return options.get(option);
	}
}
