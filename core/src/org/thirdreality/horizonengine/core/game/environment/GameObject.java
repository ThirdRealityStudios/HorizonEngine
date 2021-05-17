package org.thirdreality.horizonengine.core.game.environment;

import java.io.Serializable;
import java.util.ArrayList;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import org.thirdreality.horizonengine.Meta;
import org.thirdreality.horizonengine.core.game.management.action.Action;

public abstract class GameObject implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	// The alias is used internally to manage all components with a unique name the programmer can remember better to.
	// If no alias was specifically set, the component will automatically create its own alias.
	private String alias;

	// Used to render this object.
	protected Texture texture;

	// Contains all actions which are triggered when pressing the corresponding key.
	private ArrayList<Action<Input.Keys>> keyActions;

	// Contains all actions which are triggered when pressing the corresponding mouse button.
	private ArrayList<Action<Input.Buttons>> mouseActions;

	public GameObject()
	{
		alias = createFirstAlias();

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

	/*
	public void setPosition(Vector2 position)
	{
		bounds.setPosition(position);
	}

	public Vector2 getPosition()
	{
		return new Vector2(bounds.x, bounds.y);
	}

	 */

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