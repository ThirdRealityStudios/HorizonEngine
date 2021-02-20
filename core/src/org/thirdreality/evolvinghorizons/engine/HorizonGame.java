package org.thirdreality.evolvinghorizons.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import org.thirdreality.evolvinghorizons.engine.io.MouseUtility;
import org.thirdreality.evolvinghorizons.engine.render.RenderSource;
import org.thirdreality.evolvinghorizons.engine.settings.Meta;

public abstract class HorizonGame extends Game
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private Screen screen;

	private MouseUtility mouseUtility;

	public HorizonGame()
	{
		mouseUtility = new MouseUtility();
	}

	// Updates important dependencies for the program in order to ensure the availability of the usual functions.
	private void update(float delta)
	{
		RenderSource.update();
		mouseUtility.updateMouseData(delta);
	}

	@Override
	public void render()
	{
		update(Gdx.graphics.getDeltaTime());

		super.render();
	}
}
