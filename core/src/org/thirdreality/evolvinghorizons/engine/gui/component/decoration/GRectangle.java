package org.thirdreality.evolvinghorizons.engine.gui.component.decoration;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;

public class GRectangle extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	public GRectangle(Rectangle rect, int priority, Color color)
	{
		super("rectangle", priority, rect);

		getStyle().setColor(color);
	}
}
