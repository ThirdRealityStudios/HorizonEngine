package org.thirdreality.evolvinghorizons.engine.gui.environment.strategy.header;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.image.GImage;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.rectangle.GRectangle;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.description.GDescription;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;

public class Header
{
    private int heightPx = 40;

    private String title;

    private final HeaderValue[] upperLeftHeader;
    private final HeaderValue[] upperRightHeader;

    private final GImage[] lowerHeader;

    private final int height = 30;

    private final Font font;

    public Header(String headerTitle, Font font, HeaderValue[] upperLeftHeader, HeaderValue[] upperRightHeader, GImage[] lowerHeader)
    {
        this.title = headerTitle;
        this.font = font;

        this.upperLeftHeader = upperLeftHeader;
        this.upperRightHeader = upperRightHeader;

        this.lowerHeader = lowerHeader;
    }

    // Will create a title which is centered at the given bounds.
    private GDescription genTitle(String title, Rectangle bounds)
    {
        GDescription headerTitle = new GDescription(new Vector2(), title, font);

        float textWidth = headerTitle.getStyle().getBounds().width;
        float textHeight = headerTitle.getStyle().getBounds().height;

        float x = bounds.x + bounds.width / 2 - textWidth / 2;
        float y = bounds.y + bounds.height / 2 + textHeight / 2;

        headerTitle.getStyle().getBounds().x = x;
        headerTitle.getStyle().getBounds().y = y;

        //float x = Gdx.graphics.getWidth() / 2 - headerTitle.getStyle().getBounds().width / 2;
        //float y = Gdx.graphics.getHeight() - headerTitle.getStyle().getBounds().height;

        //Vector2 position = new Vector2(x, y);

        // Should be unnecessary as it is always re-calculated when accessing its bounds.
        //headerTitle.getStyle().setBounds(headerTitle.updateBoundsAt(position));

        //headerTitle = new GDescription(position, title, font);

        return headerTitle;
    }

    private GRectangle genBackgroundRectangle()
    {
        int width = Gdx.graphics.getWidth();
        int height = this.height;

        int x = 0;
        int y = Gdx.graphics.getHeight() - this.height;

        Rectangle shape = new Rectangle(x, y, Gdx.graphics.getWidth(), height);

        GRectangle backgroundRectangle = new GRectangle(shape, Color.RED);
        backgroundRectangle.getStyle().setPadding(1);
        backgroundRectangle.getStyle().setColor(Color.BLUE);

        return backgroundRectangle;
    }

    public GComponent[] genComponents()
    {
        GRectangle headerRectangle = genBackgroundRectangle();
        GDescription headerTitle = genTitle(title, headerRectangle.getStyle().getBounds());

        GComponent[] components = new GComponent[]{headerRectangle, headerTitle};

        return components;
    }

    public String getTitle()
    {
        return title;
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
