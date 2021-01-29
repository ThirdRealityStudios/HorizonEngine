package org.thirdreality.evolvinghorizons.guinness.gui.component.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.optional.GValueManager;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;
import org.thirdreality.evolvinghorizons.guinness.render.ColorScheme;

public class GTextfield extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean active = false;
	
	private GValueManager valueManager;

	private GlyphLayout layout;

	public GTextfield(Vector2 position, String title, int maxInput, Font font)
	{
		super("textfield", font);
		
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

		if(maxInput > 0)
		{
			getValueManager().setMaxLength(maxInput);
		}
		else
		{
			throw new IllegalArgumentException("Maximum length must be 1 or greater!");
		}
		
		boolean isValidTextfield = title.length() <= getValueManager().getMaxLength();

		if(isValidTextfield)
		{
			// Will set the title or text which is contained yet.
			// Will also update the "default shape".
			getValueManager().setValue(title);

			getStyle().setColor(Color.WHITE);
			getStyle().setFont(font);

			layout = new GlyphLayout(font.getBitmapFont(), getValueManager().getValue());

			getStyle().setPadding(4);

			getStyle().setBounds(createBoundsAt(position));
		}
		else
		{
			throw new IllegalArgumentException("Title length is bigger than the specified maximum length!");
		}
	}
	
	public GValueManager getValueManager()
	{
		return valueManager;
	}

	protected void setActive()
	{
		if(getStyle().getColor() == null)
		{
			return;
		}

		getStyle().setColor(ColorScheme.textfieldActive);
		
		active = true;
	}
	
	protected void setInactive()
	{
		getStyle().setColor(null);
		
		active = false;
	}

	private boolean isActive()
	{
		return active;
	}

	public String getInputValue()
	{
		return getValueManager().getValue();
	}

	public Rectangle createBoundsAt(Vector2 position)
	{
		layout = new GlyphLayout(getStyle().getFont().getBitmapFont(), getInputValue());

		return new Rectangle(position.x, position.y, getGlyphLayout().width + 2*getStyle().getPadding() + 2*getStyle().getBorderProperties().getBorderThicknessPx(), getGlyphLayout().height + 2*getStyle().getPadding() + 2*getStyle().getBorderProperties().getBorderThicknessPx());
	}

	public GlyphLayout getGlyphLayout()
	{
		return layout;
	}
}