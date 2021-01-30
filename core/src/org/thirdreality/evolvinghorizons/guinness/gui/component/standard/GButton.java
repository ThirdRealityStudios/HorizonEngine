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

public class GButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private GValueManager valueManager;

	private GlyphLayout layout;
	
	public GButton(Vector2 position, String title, Font font)
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
		getStyle().setPadding(4);

		layout = new GlyphLayout(font.getBitmapFont(), getTitle());

		getStyle().setBounds(createBoundsAt(position));
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

	public Rectangle createBoundsAt(Vector2 position)
	{
		layout = new GlyphLayout(getStyle().getFont().getBitmapFont(), getTitle());

		return new Rectangle(position.x, position.y, getGlyphLayout().width + 2*getStyle().getPadding() + 2*getStyle().getBorderProperties().getBorderThicknessPx(), getGlyphLayout().height + 2*getStyle().getPadding() + 2*getStyle().getBorderProperties().getBorderThicknessPx());
	}

	public GlyphLayout getGlyphLayout()
	{
		return layout;
	}
}
