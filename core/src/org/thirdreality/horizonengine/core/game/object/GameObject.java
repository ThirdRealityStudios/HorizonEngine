package org.thirdreality.horizonengine.core.game.object;

import java.io.Serializable;
import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.horizonengine.Meta;
import org.thirdreality.horizonengine.core.game.object.management.LODGroup;
import org.thirdreality.horizonengine.core.game.object.action.Action;

public abstract class GameObject implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	// The alias is used internally to manage all components with a unique name the programmer can remember better to.
	// If no alias was specifically set, the component will automatically create its own alias.
	private String alias;

	// Contains all meshes (shapes) and textures per level-of-detail (LOD).
	private LODGroup lodGroup;

	// Saves the global (!) position of this object on the map.
	private Vector2 position;

	// Contains all actions which are triggered when pressing the corresponding key.
	private ArrayList<Action<Input.Keys>> keyActions;

	// Contains all actions which are triggered when pressing the corresponding mouse button.
	private ArrayList<Action<Input.Buttons>> mouseActions;

	public GameObject()
	{
		alias = createFirstAlias();

		lodGroup = new LODGroup();

		position = new Vector2();

		keyActions = new ArrayList<Action<Input.Keys>>();
		mouseActions = new ArrayList<Action<Input.Buttons>>();
	}

	protected String createFirstAlias()
	{
		return getClass().getSuperclass().getSimpleName() + "_" + hashCode();
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getAlias()
	{
		return alias;
	}

	public void setPosition(Vector2 position)
	{
		this.position = position;
	}

	public Vector2 getPosition()
	{
		return new Vector2(position.x, position.y);
	}

	public LODGroup getLODGroup()
	{
		return lodGroup;
	}

	public ArrayList<Action<Input.Keys>> getKeyActions()
	{
		return keyActions;
	}

	public ArrayList<Action<Input.Buttons>> getMouseActions()
	{
		return mouseActions;
	}

	public abstract void render(Batch batch);
}