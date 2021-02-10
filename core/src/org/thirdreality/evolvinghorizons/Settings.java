package org.thirdreality.evolvinghorizons;

import java.awt.*;

public interface Settings
{
    // Defines the distance from the window to the edge of the screen.
    public static final int MARGIN = 2*100;

    // Retrieves the systems screen resolution.
    public static final Dimension SYSTEM_SCREEN = Toolkit.getDefaultToolkit().getScreenSize();

    public static Dimension SCREEN_SIZE = new Dimension(SYSTEM_SCREEN.width - MARGIN,SYSTEM_SCREEN.height - MARGIN);
}
