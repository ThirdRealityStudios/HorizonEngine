package org.thirdreality.evolvinghorizons.guinness.gui.component.standard;

import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.optional.GValueManager;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;

public class GDescription extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean interaction = true;
	
	private GValueManager valueManager;

	private GlyphLayout layout;

	public GDescription(Vector2 position, String title, Font font)
	{
		super("description");

		getStyle().setFont(font);
		
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
			}
		};
		
		setTitle(title);
		
		getValueManager().setMaxLength(title.length());
		
		getStyle().setFont(font);

		int borderThicknessPx = 2;

		layout = new GlyphLayout(font.getBitmapFont(), getValueManager().getValue());

		getStyle().setBounds(new Rectangle(position.x, position.y, layout.width + 2*borderThicknessPx, layout.height + 2*borderThicknessPx));

		getStyle().getBounds().setPosition(position);
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

	public boolean isInteractionEnabled()
	{
		return interaction;
	}
}
