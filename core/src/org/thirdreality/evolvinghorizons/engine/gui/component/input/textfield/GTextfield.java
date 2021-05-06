package org.thirdreality.evolvinghorizons.engine.gui.component.input.textfield;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.gui.component.ValueManager;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GTextfield extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean active = false;

	private ValueManager valueManager;

	private GlyphLayout layout;

	private float width;

	private GStyle style;

	public GTextfield(Vector2 position, String title, final int maxInputSymbols, Font font)
	{
		super("textfield");

		style = new GStyle();

		getStyle().setFont(font);

		getStyle().setBounds(new Rectangle());

		valueManager = new ValueManager()
		{
			@Override
			public void setValue(String value)
			{
				this.value = value;

				layout = new GlyphLayout(getStyle().getFont().getBitmapFont(), GTextfield.this.getValue());

				updateBoundsAt(new Vector2(getStyle().getBounds().getX(), getStyle().getBounds().getY()));
			}
		};

		if(maxInputSymbols > 0)
		{
			getValueManager().setMaxLength(maxInputSymbols);
		}
		else
		{
			throw new IllegalArgumentException("Maximum length must be 1 or greater!");
		}

		boolean isTextCompleted = title.length() <= getValueManager().getMaxLength();

		if(isTextCompleted)
		{
			getStyle().setColor(Color.WHITE);
			getStyle().setFont(font);

			getStyle().setPadding(4); // Default

			// Create the GlyphLayout so the method createBounds(...) used below can determine the new bounds.
			layout = new GlyphLayout(getStyle().getFont().getBitmapFont(), title);

			updateBoundsAt(position);

			// Will set the title or text which is contained yet.
			// Will also update the "default shape".
			getValueManager().setValue(title);
		}
		else
		{
			throw new IllegalArgumentException("Title length is bigger than the specified maximum length!");
		}
	}

	public GStyle getStyle()
	{
		return style;
	}

	public void updateBoundsAt(Vector2 position)
	{
		getStyle().getBounds().setSize(getTextfieldWidth() + 2*getStyle().getPadding() + 2*getStyle().getBorderProperties().getBorderThicknessPx(), getGlyphLayout().height + 2*getStyle().getPadding() + 2*getStyle().getBorderProperties().getBorderThicknessPx());

		if(position != null)
		{
			getStyle().getBounds().setPosition(position);
		}
	}

	public ValueManager getValueManager()
	{
		return valueManager;
	}

	/*
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
	*/

	private boolean isActive()
	{
		return active;
	}

	public String getValue()
	{
		return getValueManager().getValue();
	}

	public GlyphLayout getGlyphLayout()
	{
		return layout;
	}

	// Will calcuate the maximum textfield width in pixels by pretending the whole field is full of characters.
	public float getTextfieldWidth()
	{
		char[] repeated = new char[getValueManager().getMaxLength()];

		for(int i = 0; i < getValueManager().getMaxLength(); i++)
		{
			repeated[i] = '#';
		}

		GlyphLayout layout = new GlyphLayout(getStyle().getFont().getBitmapFont(), new String(repeated));

		return layout.width;
	}
}