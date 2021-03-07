package org.thirdreality.evolvinghorizons.engine.gui.component.standard;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.optional.GValueManager;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private GValueManager valueManager;

	private GlyphLayout layout;
	
	public GButton(Vector2 position, int priority, String title, Font font)
	{
		super("button", priority);

		valueManager = new GValueManager()
		{
			@Override
			public void setValue(String title)
			{
				this.value = title;
			}
		};

		//setLength(title.length());
		getStyle().setFont(font);
		getStyle().getBorderProperties().setBorderRadiusPx(4);
		getStyle().setPadding(4);

		setTitle(title);

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
