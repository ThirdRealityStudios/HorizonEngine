package org.thirdreality.evolvinghorizons.guinness.gui.component.decoration;

import java.awt.Dimension;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;

public class GRectangle extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	public GRectangle(Rectangle rect, Color color)
	{
		super("rectangle", rect);

		getStyle().setColor(color);
	}
}
