package org.thirdreality.evolvinghorizons.engine.gui.component.input.textfield;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

			// Create the GlyphLayout so the method updateBoundsAt(...) used below can determine the new bounds.
			layout = new GlyphLayout(getStyle().getFont().getBitmapFont(), title);

			// Initialize the bounds with the position given and calculate them after that.
			updateBoundsAt(position);

			// Will set the title or text which is contained at the beginning.
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

	// Calculates all glyphs and returns them.
	// All glyphs are returned with their location and measurements (mono-space, meaning width and height do not change here!).
	public Rectangle[] getGlyphs()
	{
		Rectangle[] glyphs = new Rectangle[getValue().length()];

		float glyphWidth = getTextfieldWidth() / getValueManager().getMaxLength();

		Rectangle bounds = getStyle().getBounds();

		float borderThicknessPx = getStyle().getBorderProperties().getBorderThicknessPx();
		float padding = getStyle().getPadding();

		Vector2 textPosition = new Vector2(bounds.x + borderThicknessPx + padding, bounds.y + (getGlyphLayout().height) / 2);

		for(int i = 0; i < glyphs.length; i++)
		{
			glyphs[i] = new Rectangle(textPosition.x + i * glyphWidth, getStyle().getBounds().y, glyphWidth, getStyle().getBounds().height);
		}

		return glyphs;
	}

	// Returns the index of the char which was selected by mouse.
	// Returns n+1 if the user wants to write / delete at the end of the text-field (at the very right),
	// whereas n = length of the input value of the text-field. Make sure to prevent corresponding OutOfBounds exceptions if you use this method!
	public int getSelectedCharByIndex()
	{
			Rectangle[] glyphs = getGlyphs();

			Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

			for(int i = 0; i < glyphs.length; i++)
			{
				Rectangle glyph = glyphs[i];

				boolean containsMousePosition = glyph.contains(mousePosition);

				if(containsMousePosition)
				{
					boolean leftContainsMousePosition = new Rectangle(glyph.x, glyph.y, glyph.width / 2, glyph.height).contains(mousePosition);

					if(leftContainsMousePosition)
					{
						return i;
					}

					// Return i+1 when the next char should be selected (cursor is roughly pointing on the char to the right).
					return i+1;
				}
			}

			// Represents the x location of the first glyph (symbol) of the text-field.
			float glyphX = getStyle().getBounds().x + getStyle().getBorderProperties().getBorderThicknessPx() + getStyle().getPadding();

			// This rectangle represents the "gap area" or left edge of the text-field.
			Rectangle leftmostPartTextfield = new Rectangle(getStyle().getBounds().x, getStyle().getBounds().y, glyphX - getStyle().getBounds().x, getStyle().getBounds().height);

			boolean clickedTextfieldBounds = leftmostPartTextfield.contains(mousePosition);

			// If the user clicks on the left edge it will return the cursor index, beginning at the left of the first char.
			if(clickedTextfieldBounds)
			{
				return 0;
			}
			else
			{
				return getValue().length();
			}
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