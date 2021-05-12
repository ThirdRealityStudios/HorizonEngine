package org.thirdreality.evolvinghorizons.engine.gui.component.standard.description;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.gui.component.input.textfield.ValueManager;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GDescription extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean interaction = true;
	
	private ValueManager valueManager;

	private GlyphLayout layout;

	private GStyle style;

	public GDescription(String text, Font font)
	{
		style = new GStyle(font, text);

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

	public GStyle getStyle()
	{
		return style;
	}

	public void setStyle(GStyle style)
	{
		this.style = style;
	}
}
