package org.thirdreality.evolvinghorizons.guinness.feature.container;

import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

// This class is a container which helps the renderer to directly read the wanted size of texture.
public class SizedTexture
{
    private Texture containedTexture;

    private float width, height;

    public SizedTexture(Texture containedTexture, float width, float height)
    {
        this.containedTexture = containedTexture;
        this.width = width;
        this.height = height;
    }

    public Texture getContainedTexture()
    {
        return containedTexture;
    }

    public void setContainedTexture(Texture containedTexture)
    {
        this.containedTexture = containedTexture;
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }

    public void setWidth(float width)
    {
        this.width = width;
    }

    public void setHeight(float height)
    {
        this.height = height;
    }
}
