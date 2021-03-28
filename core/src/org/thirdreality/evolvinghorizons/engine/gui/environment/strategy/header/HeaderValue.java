package org.thirdreality.evolvinghorizons.engine.gui.environment.strategy.header;

import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.GImage;

public class HeaderValue
{
    private GImage icon;
    private String value;

    public HeaderValue(GImage icon, String value) throws NullPointerException
    {
        if(icon == null)
        {
            throw new NullPointerException("Missing value for 'icon' as null is not accepted!");
        }

        this.icon = icon;

        this.value = value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
