package org.thirdreality.evolvinghorizons.guinness.gui;

import java.awt.Dimension;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import org.thirdreality.evolvinghorizons.guinness.gui.adapter.MouseUtility;

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
		Gdx.gl.glClearColor(0f, 1f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public void updateMouseUtility(float delta)
	{
		mouseUtility.updateMouseData(delta);
	}

	public void render(float delta)
	{
		updateMouseUtility(delta);

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

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;

		int size = viewport.sizeOfComponentBuffer();

		// Make sure the first component (assumed another JPanel by default, but NOT a viewport) is not removed from the Display (JFrame).
		if (size > 1) {
			this.viewport = viewport;
		}

		viewport.setClippingArea(new Dimension(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	}
	
	public boolean hasViewport()
	{
		return viewport != null;
	}
}
