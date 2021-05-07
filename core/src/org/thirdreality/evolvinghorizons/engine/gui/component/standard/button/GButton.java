package org.thirdreality.evolvinghorizons.engine.gui.component.standard.button;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.gui.component.input.textfield.ValueManager;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private GStyle style;
	
	private ValueManager valueManager;

	private GlyphLayout layout;

	public GButton(Vector2 position, int priority, String title, Font font)
	{
		super("button");

		valueManager = new ValueManager()
		{
			@Override
			public void setValue(String title)
			{
				this.value = title;
			}
		};

		style = new GStyle();

		getStyle().setFont(font);
		getStyle().getBorderProperties().setBorderRadiusPx(4);
		getStyle().setPadding(4);

		setTitle(title);

		layout = new GlyphLayout(font.getBitmapFont(), getTitle());

		getStyle().fillBounds(Color.BLACK);
		getStyle().setBounds(createBoundsAt(position));
	}

	public GStyle getStyle()
	{
		return style;
	}

	private ValueManager getValueManager()
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

		layout = new GlyphLayout(getStyle().getFont().getBitmapFont(), getTitle());
	}

	public Rectangle createBoundsAt(Vector2 position)
	{
		return new Rectangle(position.x, position.y, getGlyphLayout().width + 2*getStyle().getPadding() + 2*getStyle().getBorderProperties().getBorderThicknessPx(), getGlyphLayout().height + 2*getStyle().getPadding() + 2*getStyle().getBorderProperties().getBorderThicknessPx());
	}

	public GlyphLayout getGlyphLayout()
	{
		return layout;
	}
}
