package org.thirdreality.horizonengine.core.action;

import java.util.ArrayList;

// A class which is responsible for all GameObjects to execute specific actions when the given button / key was triggered.
public abstract class Action<DeviceButton>
{
    // The button or key to trigger this action.
    private DeviceButton buttonCriteria;

    // The time to trigger this action, e.g. when the key was held down.
    private ArrayList<EventTime> eventTimes;

    public Action(DeviceButton criteria)
    {
        this.buttonCriteria = criteria;
    }

    public void setExecutionCriteria(DeviceButton criteria)
    {
        this.buttonCriteria = criteria;
    }

    public DeviceButton getExecutionCriteria()
    {
        return buttonCriteria;
    }

    public abstract void execute();
}
