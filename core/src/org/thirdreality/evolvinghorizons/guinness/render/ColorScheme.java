package org.thirdreality.evolvinghorizons.guinness.render;

import com.badlogic.gdx.graphics.Color;

public class ColorScheme
{
    private static final float BRIGHTER = 1.5625f;
    private static final float DARKER = 0.64f;

    public static final Color defaultBg = new Color(Color.BLACK), defaultFg = new Color(Color.WHITE);
    public static Color buttonBg = defaultBg, buttonFg = defaultFg;
    public static Color textfieldBg = defaultBg, textfieldFg = defaultFg;
    public static Color selectionBoxBg = defaultBg, selectionBoxFg = defaultFg;
    public static Color checkboxBg = defaultBg, checkboxFg = defaultFg;

    public static Color buttonClicked = new Color(buttonFg).mul(DARKER);
    public static Color buttonHovered = new Color(buttonFg).mul(BRIGHTER);

    public static Color textfieldActive = new Color(textfieldFg).mul(DARKER);
    public static Color textfieldInactive = new Color(textfieldFg);
}
