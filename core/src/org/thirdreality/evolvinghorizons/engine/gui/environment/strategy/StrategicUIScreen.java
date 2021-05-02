package org.thirdreality.evolvinghorizons.engine.gui.environment.strategy;

import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.environment.UIScreen;
import org.thirdreality.evolvinghorizons.engine.gui.environment.strategy.header.Header;
import org.thirdreality.evolvinghorizons.engine.gui.layer.GLayer;

import java.util.Arrays;

public abstract class StrategicUIScreen extends UIScreen
{
    private Header defaultHeader;
    private GLayer headerLayer;

    public StrategicUIScreen(Header defaultHeader)
    {
        this.defaultHeader = defaultHeader;

        GComponent[] headerComponents = defaultHeader.genComponents();

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

        return true;
    }
}
