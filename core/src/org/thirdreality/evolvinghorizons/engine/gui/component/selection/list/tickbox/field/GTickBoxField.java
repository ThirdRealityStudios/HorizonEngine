package org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.tickbox.field;

import org.thirdreality.evolvinghorizons.engine.gui.component.selection.checkbox.GCheckbox;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.description.GDescription;

public class GTickBoxField
{
    private GDescription description;
    private GCheckbox checkbox;

    private GStyle style;

    public GTickBoxField(GDescription title, GCheckbox checkbox)
    {
        this.description = title;
        this.checkbox = checkbox;

        style = new GStyle();
    }

    public GDescription getDescription()
    {
        return description;
    }

    public GCheckbox getCheckbox()
    {
        return checkbox;
    }

    public GStyle getStyle()
    {
        return style;
    }
}