package org.thirdreality.evolvinghorizons.guinness.render;

import com.badlogic.gdx.graphics.Color;

public class ColorScheme
{
    private static final float BRIGHTER = 1.5625f;
    private static final float DARKER = 0.64f;

    public static final Color defaultBg = new Color(Color.BLACK), defaultFg = new Color(Color.WHITE);
    public static Color buttonBg = Color.DARK_GRAY, buttonFg = Color.LIGHT_GRAY;
    public static Color textfieldBg = defaultBg, textfieldFg = defaultFg;
    public static Color descriptionBg = Color.DARK_GRAY, descriptionFg = defaultFg;
    public static Color selectionBoxBg = defaultBg, selectionBoxFg = defaultFg;
    public static Color checkboxBg = Color.DARK_GRAY, checkboxFg = defaultFg;

    public static Color buttonClicked = new Color(buttonFg).mul(DARKER);
    public static Color buttonHovered = new Color(buttonFg).mul(BRIGHTER);

    public static Color textfieldClicked = new Color(checkboxFg).mul(DARKER);
    public static Color textfieldActive = new Color(textfieldFg).mul(DARKER);
    public static Color textfieldInactive = new Color(textfieldFg);

    public static Color selectionBoxClicked = new Color(selectionBoxFg).mul(DARKER);
    public static Color selectionBoxHovered = new Color(Color.GRAY).mul(BRIGHTER);

    public static Color checkboxPressed = new Color(checkboxFg).mul(DARKER);
}
