package org.thirdreality.evolvinghorizons.guinness.gui.component.standard;

import java.awt.Point;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.optional.GValueManager;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;
import org.thirdreality.evolvinghorizons.guinness.render.FontScheme;

public class GButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private GValueManager valueManager;

	private GlyphLayout layout;
	
	public GButton(Vector2 position, String title, BitmapFont font)
	{
		super("button");
		
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

				setMaxLength(value.length());
			}
		};

		setTitle(title);
		//setLength(title.length());
		getStyle().setFont(font);
		getStyle().getBorderProperties().setBorderRadiusPx(4);

		int borderThicknessPx = 2;

		layout = new GlyphLayout(font, getTitle());

		getStyle().setBounds(new Rectangle(position.x, position.y, layout.width + 2*borderThicknessPx, layout.height + 2*borderThicknessPx));
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
