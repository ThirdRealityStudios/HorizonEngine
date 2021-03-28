package org.thirdreality.evolvinghorizons.engine.gui.component.decoration;

import java.awt.Point;
import java.awt.Polygon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;

public class GPath extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private Polygon path;
	private boolean fill;

	public GPath(Polygon path, int priority, Color drawColor, boolean fill, Point location)
	{
		super("path");

		setPath(path);
		getStyle().setColor(drawColor); // The "primary color" of GComponent is used as the "draw color".
		setFill(fill);
	}

	public Polygon getPath()
	{
		return path;
	}

	public void setPath(Polygon path)
	{
		this.path = path;
	}

	public boolean isFill()
	{
		return fill;
	}

	public void setFill(boolean fill)
	{
		this.fill = fill;
	}
}
