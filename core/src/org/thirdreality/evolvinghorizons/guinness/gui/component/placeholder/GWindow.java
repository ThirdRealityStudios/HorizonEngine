package org.thirdreality.evolvinghorizons.guinness.gui.component.placeholder;

import java.io.File;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.guinness.Meta;
import org.thirdreality.evolvinghorizons.guinness.gui.Viewport;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.guinness.gui.component.GLogic;
import org.thirdreality.evolvinghorizons.guinness.gui.component.placeholder.window.GWindowButton;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.GStyle;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.property.GBorderProperty;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;

public class GWindow extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	private String title = "Default window";

	private ArrayList<GComponent> components;

	private GWindowButton minimizeButton, exitButton;

	// The height which is preserved for the area of the title.
	private int titleAreaHeightPx;
	
	private Color frameColor;
	
	// This specifies the origin location when the window was moved.
	// Imagine, you need to know where window is currently at when you drag at the frame.
	// In order to make it work, you would need to calculate the offset for the new location of the window.
	// To calculate it, you first you have remember the point when the window started to be re-moved,
	// and this is where you go here... :
	private Vector2 movementOrigin = null;

	// This is the simulated viewport which you can apply per method setViewport(...).
	// The Viewport is 100% compatible to the Viewport which is also applied to a Display (JFrame).
	private Viewport viewport;

	// GWindows are prioritized, meaning the priority decides whether a window is on top of another (priority must greater) and the same principle goes for the other way around.
	// Also, this variable is protected because it should only be used by the GWindowManager.
	// The GWindowManager takes care of assigning a correct priority which also does not exist twice.
	// The value -1 just means it wasn't assigned yet during runtime (for better error tracking and status checking if necessary).
	protected int priority = -1;

	public GWindow(String title, Font titleFont, Rectangle window, GBorderProperty borderProperties, ArrayList<GComponent> components)
	{
		super("window");

		getStyle().setFont(titleFont);
		
		this.components = components;
		
		setLogic(new GLogic());

		initFrameStyle(title, window, borderProperties);
	}

	private void initFrameStyle(String title, Rectangle window, GBorderProperty borderProperties)
	{
		// Here, the buttons are created, e.g. exit and minimize buttons for the window.
		//createWindowButtons(window, borderProperties);

		initStyle(window, borderProperties);

		movementOrigin = new Vector2(getStyle().getBounds().x, getStyle().getBounds().y);

		//setTitle(title);
	}

	private void initStyle(Rectangle window, final GBorderProperty borderProperties)
	{
		/*
		setStyle(new GStyle()
		{
			private static final long serialVersionUID = Meta.serialVersionUID;

			@Override
			public void setLocation(Point location)
			{
				setPrimaryLook(ShapeTransform.movePolygonTo(getPrimaryLook(), location));

				Point movedInnerArea = new Point(location.x + borderProperties.getBorderThicknessPx(), location.y + borderProperties.getBorderThicknessPx() + titleAreaHeightPx);
				setSecondaryLook(ShapeTransform.movePolygonTo(getSecondaryLook(), movedInnerArea));

				updateWindowButtons(getStyle().getPrimaryLook().getBounds());

				this.location = location;
			}
		});
		 */

		frameColor = Color.valueOf("#656bff");
		frameColor = new Color(frameColor.r, frameColor.g, frameColor.b, 160f/255f);

		getStyle().setBorderProperties(borderProperties);

		/*
		getStyle().setBounds(new Rectangle(window, getStyle().getBorderProperties()));

		// The font which is used for the window title.
		getStyle().setFont(new Font("GWindow.title", Path.FONT_FOLDER + File.separator + "StandardFont.png", 17));

		titleAreaHeightPx = (int) (getStyle().getFont().getFontSize() * 1.5);

		Point movedInnerArea = new Point(window.x + borderProperties.getBorderThicknessPx(), window.y + borderProperties.getBorderThicknessPx() + titleAreaHeightPx);
		Dimension sizeInnerArea = new Dimension(window.width - 2 * borderProperties.getBorderThicknessPx(), window.height - 2 * borderProperties.getBorderThicknessPx() - titleAreaHeightPx);

		Rectangle innerArea = new Rectangle(movedInnerArea, sizeInnerArea);

		GBorderProperty innerAreaBorders = new GBorderProperty(0);

		getStyle().setSecondaryLook(ShapeMaker.createRectangleFrom(innerArea, innerAreaBorders));

		getStyle().setLocation(window.getLocation());
		 */
	}

	/*
	// Updates the window buttons when something has changed, e.g. the location of the GWindow or its dimensions (re-sized or scaled).
	private void updateWindowButtons(Rectangle window)
	{
		Point exitLocation = new Point(window.x + window.width - exitButton.getStyle().getPrimaryLook().getBounds().width, window.y);
		
		exitButton.getStyle().setPrimaryLook(exitButton.getStyle().getPrimaryLook());
		exitButton.getStyle().setLocation(exitLocation);
		
		int marginRight = 1;
		
		Point minimizeLocation = new Point(window.x + window.width - 2 * minimizeButton.getStyle().getPrimaryLook().getBounds().width - marginRight, window.y);
		
		minimizeButton.getStyle().setPrimaryLook(minimizeButton.getStyle().getPrimaryLook());
		minimizeButton.getStyle().setLocation(minimizeLocation);
	}
	
	// This is the part where the window gets initialized with its button areas, such as exit buttons etc.
	private void createWindowButtons(Rectangle window, GBorderProperty borderProperties)
	{
		Dimension buttonSize = new Dimension(44, 20);

		Point exitLocation = new Point(window.x + window.width - buttonSize.width, window.y);

		Rectangle exitButtonRect = new Rectangle(exitLocation, buttonSize);

		GBorderProperty exitBorderStyle = new GBorderProperty(borderProperties.getBorderRadiusPx());

		exitBorderStyle.setUpperLeftBorderRadiusPx(0);
		exitBorderStyle.setLowerLeftBorderRadiusPx(0);
		exitBorderStyle.setLowerRightBorderRadiusPx(0);

		exitButton = new GWindowButton(exitButtonRect, Color.RED, 0.9f, exitBorderStyle, null);

		int marginRight = 1;

		Point minimizeLocation = new Point(window.x + window.width - 2 * buttonSize.width - marginRight, window.y);

		Rectangle minimizeButtonRect = new Rectangle(minimizeLocation, buttonSize);

		GBorderProperty minimizeBorderStyle = new GBorderProperty(borderProperties.getBorderRadiusPx());

		minimizeBorderStyle.setUpperLeftBorderRadiusPx(0);
		minimizeBorderStyle.setLowerRightBorderRadiusPx(0);
		minimizeBorderStyle.setUpperRightBorderRadiusPx(0);

		minimizeButton = new GWindowButton(minimizeButtonRect, Color.valueOf("#606060"), 0.9f, minimizeBorderStyle, null);
	}
	
	public Color getFrameColor()
	{
		return frameColor;
	}

	public void setComponents(ArrayList<GComponent> components)
	{
		this.components = components;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public GWindowButton getExitButton()
	{
		return exitButton;
	}
	
	public GWindowButton getMinimizeButton()
	{
		return minimizeButton;
	}
	
	public int getTitleAreaHeightPx()
	{
		return titleAreaHeightPx;
	}

	public Point getMovementOrigin()
	{
		return movementOrigin;
	}

	public void setMovementOrigin(Point movementOrigin)
	{
		this.movementOrigin = movementOrigin;
	}
	
	public Viewport getViewport()
	{
		return viewport;
	}
	
	public void setViewport(Viewport viewport)
	{
		this.viewport = viewport;

		viewport.setOrigin(getStyle().getSecondaryLook().getBounds().getLocation());

		// This will tell the window not to display / render components which are beyond the given measurements (a.k.a clipping area).
		viewport.setClippingArea(new Dimension(getStyle().getSecondaryLook().getBounds().getSize()));
	}
	
	public boolean hasViewport()
	{
		return viewport != null;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj.hashCode() == this.hashCode();
	}
	 */
}
