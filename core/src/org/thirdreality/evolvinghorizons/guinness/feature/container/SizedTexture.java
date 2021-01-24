package org.thirdreality.evolvinghorizons.guinness.feature.container;

import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

// This class is a container which helps the renderer to directly read the wanted size of texture.
public class SizedTexture
{
    private Texture containedTexture;

    private Dimension size;

    public SizedTexture(Texture containedTexture, Dimension size)
    {
        this.containedTexture = containedTexture;
        this.size = size;
    }

    public Texture getContainedTexture()
    {
        return containedTexture;
    }

    public void setContainedTexture(Texture containedTexture)
    {
        this.containedTexture = containedTexture;
    }

    public Dimension getSize()
    {
        return size;
    }

    public void setSize(Dimension size)
    {
        this.size = size;
    }
}
