package org.thirdreality.evolvinghorizons.guinness.gui.design;

import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;

import com.badlogic.gdx.graphics.Pixmap;
import org.thirdreality.evolvinghorizons.guinness.gui.Viewport;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.property.GBorderProperty;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.property.GPaddingProperty;
import org.thirdreality.evolvinghorizons.guinness.gui.font.FontLoader;

public abstract class Design implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private DesignColor designColor;
	
	private GBorderProperty borderProperty;
	
	private GPaddingProperty paddingProperty;
	
	private FontLoader fL = new FontLoader();
	
	public Design(DesignColor designColor, GBorderProperty borderProperties, GPaddingProperty paddingProperty)
	{
		this.designColor = designColor;
		this.borderProperty = borderProperties;
		this.paddingProperty = paddingProperty;
	}

	public DesignColor getDesignColor()
	{
		return designColor;
	}

	public void setDesignColor(DesignColor designColor)
	{
		this.designColor = designColor;
	}

	public GBorderProperty getBorderProperty()
	{
		return borderProperty;
	}

	public void setBorderProperty(GBorderProperty borderProperty)
	{
		this.borderProperty = borderProperty;
	}

	public GPaddingProperty getPaddingProperty()
	{
		return paddingProperty;
	}

	public void setPaddingProperty(GPaddingProperty paddingProperty)
	{
		this.paddingProperty = paddingProperty;
	}

	public FontLoader getFontLoader()
	{
		return fL;
	}
	
	public abstract void drawContext(Viewport displayViewport, GComponent c, Point origin, Point offset, float scale);
	
	public abstract Polygon generateDefaultShape(GComponent c);
	
	public abstract void updateDefaultShape(GComponent c);
}