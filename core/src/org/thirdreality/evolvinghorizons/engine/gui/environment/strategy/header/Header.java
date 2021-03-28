package org.thirdreality.evolvinghorizons.engine.gui.environment.strategy.header;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.GImage;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.GRectangle;

public class Header
{
    private int heightPx = 40;

    private String titleCentered;

    private final HeaderValue[] upperLeftHeader;
    private final HeaderValue[] upperRightHeader;

    private final GImage[] lowerHeader;

    private final int height = 50;

    public Header(String titleCentered, HeaderValue[] upperLeftHeader, HeaderValue[] upperRightHeader, GImage[] lowerHeader)
    {
        this.titleCentered = titleCentered;

        this.upperLeftHeader = upperLeftHeader;
        this.upperRightHeader = upperRightHeader;

        this.lowerHeader = lowerHeader;
    }

    public GRectangle getBackgroundRectangle()
    {
        int width = Gdx.graphics.getWidth();
        int height = this.height;

        int x = 0;
        int y = Gdx.graphics.getHeight() - this.height;

        Rectangle shape = new Rectangle(x, y, Gdx.graphics.getWidth(), height);

        GRectangle backgroundRectangle = new GRectangle(shape, Integer.MIN_VALUE, Color.YELLOW);

        return backgroundRectangle;
    }

    public String getTitleCentered()
    {
        return titleCentered;
    }

    public HeaderValue[] getUpperLeftHeader()
    {
        return upperLeftHeader;
    }

    public HeaderValue[] getUpperRightHeader()
    {
        return upperRightHeader;
    }

    public GImage[] getLowerHeader()
    {
        return lowerHeader;
    }
}
