package org.thirdreality.evolvinghorizons.guinness.gui.component.decoration;

import java.awt.Dimension;

import com.badlogic.gdx.graphics.Color;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.feature.shape.ShapeMaker;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;

public class GRectangle extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	public GRectangle(int x, int y, Dimension size, Color color, float opacity)
	{
		super("rectangle", ShapeMaker.createRectangle(x, y, size.width, size.height), null);

		// First, the primary color needs to be set.
		// When applying the opacity below it uses the primary color.
		// That's why you need to set a primary color first because
		// otherwise it would cause a NullPointerException.
		getStyle().setPrimaryColor(color);

		// Applies the given opacity to the primary color.
		getStyle().setAlpha(opacity);
	}
}
