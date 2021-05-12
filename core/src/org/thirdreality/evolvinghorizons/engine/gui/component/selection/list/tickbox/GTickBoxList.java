package org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.tickbox;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.gui.ColorScheme;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.rectangle.GRectangle;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.checkbox.GCheckbox;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.tickbox.field.GTickBoxField;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.description.GDescription;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GTickBoxList extends GComponent
{
	private GRectangle background;
	private ArrayList<GTickBoxField> options;

	private int height;

	private GStyle style;

	public GTickBoxList(Vector2 position, boolean multipleChoice, Font font)
	{
		background = new GRectangle(new Rectangle(position.x, position.y, 0, 0), ColorScheme.selectionBoxFg);
		background.getStyle().getBorderProperties().setBorderColor(ColorScheme.selectionBoxBg);

		options = new ArrayList<GTickBoxField>();

		style = new GStyle();
		getStyle().setFont(font);
	}

	private Vector2 sizeOf(String title)
	{
		GlyphLayout layout = new GlyphLayout(getStyle().getFont().getBitmapFont(), title);

		Vector2 size = new Vector2(layout.width, layout.height);

		return size;
	}

	public void addOption(String title)
	{
		Vector2 size = sizeOf(title);

		Rectangle bounds = getBackground().getStyle().getBounds();
		int borderThickness = getBackground().getStyle().getBorderProperties().getBorderThicknessPx();

		height += getStyle().getPadding();

		Vector2 positionOption = new Vector2(bounds.x + borderThickness + getStyle().getPadding(), bounds.y + height);

		GCheckbox checkbox = new GCheckbox(false);
		checkbox.getStyle().setBounds(new Rectangle(positionOption.x, positionOption.y, size.y, size.y));

		GDescription description = new GDescription(title, getStyle().getFont());
		description.getStyle().getBounds().setPosition(positionOption);
		description.getStyle().getBounds().x += 2*getStyle().getPadding();
		description.getStyle().getBounds().y += size.y;

		GTickBoxField option = new GTickBoxField(description, checkbox);

		// Creates the bounds of the option, meaning it includes the checkbox and the title next to it.
		option.getStyle().setBounds(new Rectangle(positionOption.x, positionOption.y, size.y + getStyle().getPadding() + size.x, size.y));

		options.add(option);

		height += size.y;

		getBackground().getStyle().getBounds().height = height + getStyle().getPadding();

		float optionWidth = option.getStyle().getBounds().width + 2*getStyle().getPadding();
		float listWidth = getBackground().getStyle().getBounds().width;

		if(optionWidth > listWidth)
		{
			getBackground().getStyle().getBounds().width = optionWidth;
		}
	}

	public GTickBoxField getOption(int index)
	{
		return options.get(index);
	}

	public GRectangle getBackground()
	{
		return background;
	}

	public int size()
	{
		return options.size();
	}

	public GStyle getStyle()
	{
		return style;
	}

	public GComponent[] getItems()
	{
		GComponent[] items = new GComponent[options.size() * 2];

		for(int i = 0; i < options.size(); i++)
		{
			items[i*2] = options.get(i).getCheckbox();
			items[i*2 + 1] = options.get(i).getDescription();
		}

		return items;
	}
}
