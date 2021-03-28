package org.thirdreality.evolvinghorizons.engine.gui.environment.strategy;

import com.badlogic.gdx.Gdx;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.GPolyButton;
import org.thirdreality.evolvinghorizons.engine.gui.environment.UIScreen;
import org.thirdreality.evolvinghorizons.engine.gui.environment.strategy.header.Header;
import org.thirdreality.evolvinghorizons.engine.gui.layer.GLayer;
import org.thirdreality.evolvinghorizons.engine.math.Polygon;

import java.util.Arrays;

public abstract class StrategicUIScreen extends UIScreen
{
    private Header defaultHeader;
    private GLayer headerLayer;

    public StrategicUIScreen(Header defaultHeader)
    {
        this.defaultHeader = defaultHeader;

        GComponent[] headerComponents = new GComponent[]{defaultHeader.getBackgroundRectangle()};

        headerLayer = new GLayer(headerComponents, Integer.MAX_VALUE);
    }

    private GLayer[] attach(GLayer[] array, GLayer layer)
    {
        GLayer[] arrayLarger = new GLayer[array.length + 1];

        arrayLarger = Arrays.copyOf(array, array.length + 1);

        arrayLarger[arrayLarger.length - 1] = layer;

        return arrayLarger;
    }

    @Override
    public boolean setLayers(GLayer[] source)
    {
        GLayer[] backup = layers;

        layers = attach(source, headerLayer);

        for(GLayer layer : layers)
        {
            if(isDoublePriority(layer))
            {
                layers = backup;

                // Tell the programmer something went wrong, meaning that (at least) two layers overlap each other.
                return false;
            }
        }

        // Sorts the layers by their priority.
        Arrays.sort(layers);

        for(GLayer layerCurrent : layers)
        {
            for(GComponent component : layerCurrent.getComponents())
            {
                System.out.print(component.getType() + " ");
            }

            System.out.print(" (" + layerCurrent.getPriority() + ")");

            System.out.println();
        }

        return true;
    }
}
