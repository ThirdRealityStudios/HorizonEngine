package org.thirdreality.evolvinghorizons.engine.gui;

import java.awt.Dimension;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import org.thirdreality.evolvinghorizons.engine.io.MouseUtility;
import org.thirdreality.evolvinghorizons.engine.render.RenderSource;

public class DisplayContext
{
	private static final long serialVersionUID = 1L;

	private Viewport viewport;

	private MouseUtility mouseUtility;

	public DisplayContext()
	{
		mouseUtility = new MouseUtility();
	}

	// In the beginning this will just draw a background color which will erase the content of the last render cycle.
	private void drawBackground()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public void updateMouseUtility(float delta)
	{
		mouseUtility.updateMouseData(delta);
	}

	// Updates important dependencies for the program in order to ensure the availability of the usual functions.
	private void updatePresets(float delta)
	{
		RenderSource.updateSources();
		updateMouseUtility(delta);
	}

	public void render(float delta)
	{
		updatePresets(delta);

		drawBackground();

		getViewport().render(getViewport().getComponentOutput());
	}

	/*
	public void center()
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		int xScreenMiddle = (screen.width / 2), yScreenMiddle = (screen.height / 2);
		int xWindowMiddle = (Gdx.graphics.getWidth() / 2), yWindowMiddle = (Gdx.graphics.getHeight() / 2);
		
		int xMiddle = xScreenMiddle - xWindowMiddle, yMiddle = yScreenMiddle - yWindowMiddle;

		setLocation(xMiddle, yMiddle);
	}
	*/
	
	public Viewport getViewport()
	{
		return viewport;
	}

	public void setViewport(Viewport viewport)
	{
		this.viewport = viewport;

		int size = viewport.sizeOfComponentBuffer();

		// Make sure the first component (assumed another JPanel by default, but NOT a viewport) is not removed from the Display (JFrame).
		if (size > 1)
		{
			this.viewport = viewport;
		}

		viewport.setClippingArea(new Dimension(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	}
	
	public boolean hasViewport()
	{
		return viewport != null;
	}
}
