package org.thirdreality.evolvinghorizons.guinness.gui.component.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
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

	private float width;

	public GTextfield(Vector2 position, String title, final int maxInputSymbols, Font font)
	{
		super("textfield");

		getStyle().setFont(font);

		getStyle().setBounds(new Rectangle());

		valueManager = new GValueManager()
		{
			@Override
			public void setValue(String value)
			{
				this.value = value;

				layout = new GlyphLayout(getStyle().getFont().getBitmapFont(), getInputValue());

				updateBoundsAt(null);
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

	public GlyphLayout getGlyphLayout()
	{
		return layout;
	}

	public void updateBoundsAt(Vector2 position)
	{
		getStyle().getBounds().setSize(getTextfieldWidth() + 2*getStyle().getPadding() + 2*getStyle().getBorderProperties().getBorderThicknessPx(), getGlyphLayout().height + 2*getStyle().getPadding() + 2*getStyle().getBorderProperties().getBorderThicknessPx());

		if(position != null)
		{
			getStyle().getBounds().setPosition(position);
		}
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