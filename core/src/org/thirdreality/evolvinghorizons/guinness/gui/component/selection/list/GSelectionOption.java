package org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list;

import java.awt.Image;
import java.awt.Point;
import java.io.File;

import org.thirdreality.evolvinghorizons.guinness.feature.Path;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GLogic;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.GStyle;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;

public class GSelectionOption
{
	private GStyle style;
	
	private GLogic logic;
	
	private boolean isChecked = false;
	
	private boolean isDefaultOption = false;
	
	private String value;
	
	private Image[] icon;
	
	public GSelectionOption(String title, boolean isDefaultOption)
	{
		this.isDefaultOption = isDefaultOption;

		setTitle(title);
		
		setStyle(new GStyle()
		{
			@Override
			public void setLocation(Point location)
			{
				// In real it has no effect because there is no use at all..
				// The GSelectionOption class was rather made for logical purposes (marking something just as marked or unmarked).
				this.location = location;
			}
		});
		
		setLogic(new GLogic());
		
		// This line makes sure every GComponent also has a default font, no matter it is used or not or for other cases.
		getStyle().setFont(new Font("default", Path.FONT_FOLDER + File.separator + "StandardFont.png", 18));
	}
	
	public GSelectionOption(GStyle style, GLogic logic, boolean isDefaultOption)
	{
		this("", isDefaultOption);
		
		this.style = style;
		this.logic = logic;
	}
	
	// The option is being updated with new icons from the GSelectionBox or similar instances.
	// This way, abstractness is being kept by handling all scaling and other stuff by outer instances.
	// Doing so, you don't have to take care about applying an icon for each option (including scaling manually).
	public void setIcon(Image[] icons, int width, int height)
	{
		icon[0] = icons[0].getScaledInstance(width, height, Image.SCALE_SMOOTH); // "Unselected" state
		icon[1] = icons[1].getScaledInstance(width, height, Image.SCALE_SMOOTH); // "Selected" state
	}
	
	public Image[] getIcon()
	{
		return icon;
	}

	public GStyle getStyle()
	{
		return style;
	}

	public void setStyle(GStyle style)
	{
		this.style = style;
	}

	public GLogic getLogic()
	{
		return logic;
	}

	public void setLogic(GLogic logic)
	{
		this.logic = logic;
	}

	public boolean isChecked()
	{
		return isChecked;
	}

	public void setChecked(boolean isChecked)
	{
		this.isChecked = isChecked;
	}

	public boolean isDefaultOption()
	{
		return isDefaultOption;
	}

	public void setDefaultOption(boolean isDefaultOption)
	{
		this.isDefaultOption = isDefaultOption;
	}
	
	public void setTitle(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
}
