package org.thirdreality.evolvinghorizons.engine.gui.component.standard;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.optional.GValueManager;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GDescription extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean interaction = true;
	
	private GValueManager valueManager;

	private GlyphLayout layout;

	public GDescription(Vector2 position, String text, Font font)
	{
		super("description");

		getStyle().setFont(font);
		
		valueManager = new GValueManager()
		{
			@Override
			public void setValue(String value)
			{
				this.value = value;

				layout = new GlyphLayout(getStyle().getFont().getBitmapFont(), getText());
			}
		};
		
		setText(text);

		getStyle().setFont(font);

		layout = new GlyphLayout(font.getBitmapFont(), getText());

		// Only saves the position in the bounds.
		getStyle().setBounds(createBoundsAt(position));

		getStyle().getBounds().setPosition(position);
	}
	
	private GValueManager getValueManager()
	{
		return valueManager;
	}

	public String getText()
	{
		return getValueManager().getValue();
	}

	public void setText(String text)
	{
		getValueManager().setValue(text);
	}

	public boolean isInteractionEnabled()
	{
		return interaction;
	}

	public GlyphLayout getGlyphLayout()
	{
		return layout;
	}

	public Rectangle createBoundsAt(Vector2 position)
	{
		return new Rectangle(position.x, position.y, getGlyphLayout().width, getGlyphLayout().height);
	}
}
