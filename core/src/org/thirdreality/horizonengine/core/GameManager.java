package org.thirdreality.horizonengine.core;

import com.badlogic.gdx.Gdx;
import org.thirdreality.horizonengine.core.action.ActionTrigger;

public class GameManager
{
    private ActionTrigger actionTrigger;

    public LODManager lod;

    public GameManager()
    {
        actionTrigger = new ActionTrigger();

        Gdx.input.setInputProcessor(actionTrigger);

        lod = new LODManager();
    }
}
