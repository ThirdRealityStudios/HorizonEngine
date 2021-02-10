package org.thirdreality.evolvinghorizons.engine.gui.component.placeholder.window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.GImage;
import org.thirdreality.evolvinghorizons.engine.container.style.property.GBorderProperty;

public class GWindowButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private GImage icon;
	
	private Color defaultColor, clickColor, hoverColor;

	public GWindowButton(Rectangle rect, Color background, GBorderProperty borderProperties, GImage icon)
	{
		super("window_button");

		getStyle().setBorderProperties(borderProperties);

		getStyle().setBounds(new Rectangle(rect));//, getStyle().getBorderProperties()));
		//getStyle().setLocation(new GIPoint(rect.getLocation()).toPoint());

		getStyle().setColor(background);

		clickColor = getStyle().getColor().mul(0.8f).mul(0.8f);
		defaultColor = getStyle().getColor().mul(0.8f);
		hoverColor = getStyle().getColor();

		getStyle().setColor(defaultColor);

		this.icon = icon;
	}
	
	public GImage getIcon()
	{
		return icon;
	}
	
	public Color getDefaultColor()
	{
		return defaultColor;
	}
	
	public Color getClickColor()
	{
		return clickColor;
	}

	public Color getHoverColor()
	{
		return hoverColor;
	}
}
