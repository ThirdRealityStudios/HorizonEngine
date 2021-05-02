package org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.tickbox.field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.container.style.GStyle;

public class GTickBoxField
{
    private String text;
    private boolean selected;
    private Rectangle tickBox, bounds;
    private Color backgroundColor;

    private GStyle style;

    public GTickBoxField(String text)
    {
        this.text = text;

        tickBox = new Rectangle();
        bounds = new Rectangle();
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public Rectangle getTickBox()
    {
        return tickBox;
    }

    public void setTickBox(Rectangle tickBox)
    {
        this.tickBox = tickBox;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void setBounds(Rectangle bounds)
    {
        this.bounds = bounds;
    }

    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }
}