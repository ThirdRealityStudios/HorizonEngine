package org.thirdreality.evolvinghorizons.engine.gui.component.standard.description;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.container.style.GStyle;
import org.thirdreality.evolvinghorizons.engine.gui.component.ValueManager;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GDescription extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean interaction = true;
	
	private ValueManager valueManager;

	private GlyphLayout layout;

	public GDescription(Vector2 position, String text, Font font)
	{
		super("description");

		setStyle(new GStyle()
		{
			// Will ignore the given dimensions as they are calculated automatically.
			@Override
			public void setBounds(Rectangle bounds)
			{
				super.setBounds(updateBoundsAt(new Vector2(bounds.x, bounds.y)));
			}
		});

		getStyle().setFont(font);
		
		valueManager = new ValueManager()
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
		getStyle().setBounds(updateBoundsAt(position));
	}
	
	private ValueManager getValueManager()
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

	public Rectangle updateBoundsAt(Vector2 position)
	{
		return new Rectangle(position.x, position.y, getGlyphLayout().width, getGlyphLayout().height);
	}
}
