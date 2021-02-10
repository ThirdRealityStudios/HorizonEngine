package org.thirdreality.evolvinghorizons.engine.gui.component.standard;

import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.optional.GValueManager;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GPolyButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private GValueManager valueManager;

	private PolygonSprite polygon;

	// Info: The polygon is closed automatically later..
	public GPolyButton(PolygonSprite polygon, String title, Font font)
	{
		super("polybutton", new Rectangle(polygon.getBoundingRectangle().x, polygon.getBoundingRectangle().y, polygon.getBoundingRectangle().width, polygon.getBoundingRectangle().height));

		this.polygon = polygon;

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

	public PolygonSprite getPolygonSprite()
	{
		return polygon;
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
