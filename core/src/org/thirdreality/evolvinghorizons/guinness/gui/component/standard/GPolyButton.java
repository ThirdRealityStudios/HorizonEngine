package org.thirdreality.evolvinghorizons.guinness.gui.component.standard;

import java.awt.Point;
import java.awt.Polygon;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.optional.GValueManager;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;

public class GPolyButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private GValueManager valueManager;
	
	// Info: The polygon is closed automatically later..
	public GPolyButton(Vector2 position, String title, Font font, Polygon polygon)
	{
		super("polybutton", new Rectangle(position.x, position.y, polygon.getBounds().width, polygon.getBounds().height));
		
		// Make sure, the polygon is at the correct position.
		// This way, you can also independently create polygons regardless of the Viewports measurements.
		// Anyway, scaling polygons and other transformations are up to the user still..
		polygon.translate((int) position.x, (int) position.y);
		
		valueManager = new GValueManager()
		{
			@Override
			public void setValue(String value)
			{
				if(value == null)
				{
					return;
				}

				this.value = value;
				
				setMaxLength(getValue().length());
			}
		};
		
		setTitle(title);
	}
	
	private GValueManager getValueManager()
	{
		return valueManager;
	}

	public String getTitle()
	{
		return getValueManager().getValue();
	}

	public void setTitle(String title)
	{
		getValueManager().setValue(title);
	}
}
