package org.thirdreality.horizonengine.core.game.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.thirdreality.horizonengine.HorizonEngine;
import org.thirdreality.horizonengine.settings.DataSize;
import org.thirdreality.horizonengine.Meta;
import org.thirdreality.horizonengine.core.console.trouble.Error;
import org.thirdreality.horizonengine.core.console.trouble.Troubleshooting;

// Contains an amount of drawable components.
public class GameLayer extends GameObject implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	// Contains all GameObjects, described as members.
	private ArrayList<GameObject> members;

	// Describes where the GameLayer will appear at, meaning before, behind or on the same index (not overlapping).
	private int zIndex;

	public GameLayer(int zIndex)
	{
		this.members = new ArrayList<GameObject>(DataSize.INIT_SIZE_GAME_LAYER);

		this.zIndex = zIndex;
	}

	@Override
	protected String createFirstAlias()
	{
		return getClass().getSimpleName() + "_" + hashCode();
	}

	public void add(GameObject object)
	{
		if(object instanceof GameLayer)
		{
			GameLayer gameLayer = (GameLayer) object;

			if(gameLayer.getZIndex() <= getZIndex())
			{
				HorizonEngine.errorExit(Error.INVALID_GAME_LAYER_ZINDEX, Troubleshooting.INVALID_GAME_LAYER_ZINDEX);
			}
		}

		members.add(object);
	}

	public void remove(int memberIndex)
	{
		members.remove(memberIndex);
	}

	public GameObject getMember(int memberIndex)
	{
		return members.get(memberIndex);
	}

	public Iterator<GameObject> getMembersIterated()
	{
		return members.iterator();
	}

	public void deleteMembers()
	{
		members.clear();
	}

	public int size()
	{
		return members.size();
	}

	public int getZIndex()
	{
		return zIndex;
	}

	public boolean setZIndex(int zIndex)
	{
		if(zIndex >= 0)
		{
			this.zIndex = zIndex;

			return true;
		}

		return false;
	}

	@Override
	public void render(Batch batch)
	{
		for(GameObject obj : members)
		{
			obj.render(batch);
		}
	}
}
