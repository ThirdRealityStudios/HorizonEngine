package org.thirdreality.evolvinghorizons.guinness.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FontScheme
{
    public static final BitmapFont defaultFont = create();

    private static BitmapFont create()
    {
        BitmapFont font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(1.6f);

        return font;
    }
}
