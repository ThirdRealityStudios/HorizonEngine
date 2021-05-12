package org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.tickbox;

import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class GStyle
{
    private Font font;
    private int padding = 10;

    public void setFont(Font font)
    {
        this.font = font;
    }

    public Font getFont()
    {
        return font;
    }

    public int getPadding()
    {
        return padding;
    }

    public void setPadding(int padding)
    {
        this.padding = padding;
    }
}
