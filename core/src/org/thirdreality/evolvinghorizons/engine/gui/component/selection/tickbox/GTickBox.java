package org.thirdreality.evolvinghorizons.engine.gui.component.selection.tickbox;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.container.style.GStyle;

public class GTickBox
{
    private String text;
    private boolean selected;
    private Rectangle tickBox, textBox;
    private Color backgroundColor;

    private GStyle style;

    public GTickBox(String text)
    {
        this.text = text;

        tickBox = new Rectangle();
        textBox = new Rectangle();
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

    protected void setTickBox(Rectangle tickBox)
    {
        this.tickBox = tickBox;
    }

    public Rectangle getTextBox()
    {
        return textBox;
    }

    protected void setTextBox(Rectangle textBox)
    {
        this.textBox = textBox;
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
