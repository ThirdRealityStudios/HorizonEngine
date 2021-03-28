package org.thirdreality.evolvinghorizons.engine.gui.component.decoration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;

public class GRectangle extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	public GRectangle(Rectangle rect, Color color)
	{
		super("rectangle");

		getStyle().setColor(color);

		Pixmap p = new Pixmap(1,1, Pixmap.Format.RGBA8888);
		p.setColor(color);
		p.fill();
		Texture t = new Texture(p);

		getStyle().getTextureRegion().setTexture(t);
		getStyle().getTextureRegion().setRegion((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height);
	}
}
