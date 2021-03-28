package org.thirdreality.evolvinghorizons.engine.settings;

import java.awt.*;

public interface Settings
{
    // Defines the distance from the window to the edge of the screen.
    public static final int MARGIN = 2*100;

    // Retrieves the systems screen resolution.
    public static final Dimension SYSTEM_SCREEN = new Dimension(1280, 720);//Toolkit.getDefaultToolkit().getScreenSize();

    public static Dimension SCREEN_SIZE = new Dimension(SYSTEM_SCREEN.width - MARGIN,SYSTEM_SCREEN.height - MARGIN);

    public static int MAX_RENDERED_COMPONENTS = 128;
}
