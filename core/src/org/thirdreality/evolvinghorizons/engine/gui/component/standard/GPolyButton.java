package org.thirdreality.evolvinghorizons.engine.gui.component.standard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.math.Polygon;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.optional.GValueManager;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GPolyButton extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private GValueManager valueManager;

	private Polygon polygon;

	// Info: The polygon is closed automatically later..
	public GPolyButton(Polygon polygon, String title, Font font)
	{
		super("polybutton", new Rectangle(polygon.getBoundingRectangle().x, polygon.getBoundingRectangle().y, polygon.getBoundingRectangle().width, polygon.getBoundingRectangle().height));

		getStyle().setColor(Color.RED);

		this.polygon = polygon;

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

				setMaxLength(getValue().length());
			}
		};

		setTitle(title);
	}

	private GValueManager getValueManager()
	{
		return valueManager;
	}

	public Polygon getPolygon()
	{
		return polygon;
	}

	private PolygonRegion processRegion(Polygon polygon)
	{
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.RED);
		pixmap.fill();

		Texture texture = new Texture(pixmap);

		TextureRegion textureRegion = new TextureRegion(texture, 1, 1);

		PolygonRegion polygonRegion = new PolygonRegion(textureRegion, polygon.getTransformedVertices(), polygon.getTriangles());

		return polygonRegion;
	}

	public PolygonSprite getUpdatedPolygonSprite()
	{
		PolygonSprite polySprite = new PolygonSprite(processRegion(polygon));

		return polySprite;
	}

	public String getTitle()
	{
		return getValueManager().getValue();
	}

	public void setTitle(String title)
	{
		getValueManager().setValue(title);
	}
}
