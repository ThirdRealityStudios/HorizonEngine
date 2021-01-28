package org.thirdreality.evolvinghorizons.guinness.sample;

import java.awt.Dimension;
import java.awt.Polygon;
import java.io.File;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.guinness.feature.Path;
import org.thirdreality.evolvinghorizons.guinness.feature.image.ImageToolkit;
import org.thirdreality.evolvinghorizons.guinness.gui.DisplayContext;
import org.thirdreality.evolvinghorizons.guinness.gui.Viewport;
import org.thirdreality.evolvinghorizons.guinness.gui.component.decoration.GImage;
import org.thirdreality.evolvinghorizons.guinness.gui.component.decoration.GRectangle;
import org.thirdreality.evolvinghorizons.guinness.gui.component.input.GTextfield;
import org.thirdreality.evolvinghorizons.guinness.gui.component.optional.GActionListener;
import org.thirdreality.evolvinghorizons.guinness.gui.component.placeholder.GWindow;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.GCheckbox;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list.GSelectionBox;
import org.thirdreality.evolvinghorizons.guinness.gui.component.selection.list.GSelectionOption;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GButton;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GDescription;
import org.thirdreality.evolvinghorizons.guinness.gui.component.standard.GPolyButton;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.property.GBorderProperty;
import org.thirdreality.evolvinghorizons.guinness.gui.component.style.property.GPaddingProperty;
import org.thirdreality.evolvinghorizons.guinness.gui.font.Font;
import org.thirdreality.evolvinghorizons.guinness.gui.layer.GLayer;
import org.thirdreality.evolvinghorizons.guinness.render.FontScheme;

public class Main
{
	public DisplayContext displayContext;
	
	private GRectangle rect;

	private GButton moveButton, increaseScale, exit;

	private GTextfield input1, input2, input3;
	
	private GImage img0;
	
	private GDescription description;
	
	private GCheckbox checkbox1;
	
	// Some sample options for the selection box below.
	private ArrayList<GSelectionOption> options;
	
	private GSelectionBox gSB;
	
	private GWindow window0, window1;

	private GLayer layer0, layer1, layer2_shared, layer3, layer4;

	private Font biggerFont = new Font("biggerFont", Font.getDefaultFilepath(), 25), smallerFont = new Font("smallerFont", Font.getDefaultFilepath());

	private Viewport viewport;
	
	// For simulating on a GWindow.
	private Viewport viewportGWindow0;

	public static void main(String[] args)
	{
		Main m = new Main();

		m.init();
		m.run();
	}

	private GPolyButton getPolyButton0()
	{
		Polygon poly = new Polygon();

		poly.addPoint(0, 0);
		poly.addPoint(100, 0);
		poly.addPoint(150, 50);
		poly.addPoint(150, 100);
		poly.addPoint(75, 125);
		poly.addPoint(0, 125);

		GPolyButton gPolyButton = new GPolyButton(new Vector2(450, 370), "CLICK ME", FontScheme.defaultFont, poly);

		gPolyButton.setActionListener(new GActionListener()
		{
			@Override
			public void onHover()
			{

			}


			@Override
			public void onClick()
			{
				System.out.println("Clicked GPolyButton!");
			}
		});
		
		gPolyButton.getStyle().setAlpha(0.7f);
		gPolyButton.getStyle().setTextAlign(1);
		
		return gPolyButton;
	}
	
	private GPolyButton getPolyButton1()
	{
		Polygon poly = new Polygon();
		
		poly.addPoint(0, 0);
		poly.addPoint(100, 0);
		poly.addPoint(150, 50);
		poly.addPoint(150, 100);
		poly.addPoint(125, 125);
		poly.addPoint(0, 250);
		
		GPolyButton gPolyButton = new GPolyButton(new Vector2(250, 310), "Button", FontScheme.defaultFont, poly);
		
		gPolyButton.setActionListener(new GActionListener()
		{
			@Override
			public void onHover()
			{
				
			}
			
			@Override
			public void onClick()
			{
				System.out.println("Clicked GPolyButton!");
			}
		});
		
		gPolyButton.getStyle().setColor(Color.RED);
		gPolyButton.getStyle().setTextAlign(1);
		gPolyButton.getStyle().setTextTransition(new Vector2(0, -40));
		
		return gPolyButton;
	}
	
	private void initComponents()
	{
		Rectangle windowRepresentation = new Rectangle(400f, 0f, 600, 400);
		
		GBorderProperty borderProperties = new GBorderProperty(10, 5);
		
		// Tell the Viewport it is simulated (in a simulated display environment) by passing 'null' to its constructor.
		viewportGWindow0 = new Viewport(true);
		
		window0 = new GWindow("Sample window", smallerFont, windowRepresentation, borderProperties, null);
		window1 = new GWindow("..Second window..", smallerFont, windowRepresentation, borderProperties, null);

		rect = new GRectangle(0, -50, new Dimension(800, 136), Color.RED, 0.5f);
		rect.getStyle().getBorderProperties().setBorderRadiusPx(14);
		
		// The button ("start" variable) is focused later during runtime instead.
		rect.getLogic().setFocusable(false);
		
		options = new ArrayList<GSelectionOption>();
		
		GSelectionOption option0 = new GSelectionOption("Win a price", false), option1 = new GSelectionOption("Loose everything", true), option2 = new GSelectionOption("Loose your vibes", false);
		
		option0.getStyle().setPadding(10);
		option1.getStyle().setPadding(10);
		option2.getStyle().setPadding(10);
		
		options.add(option0);
		options.add(option1);
		options.add(option2);
		
		// The first option should have a different background color.
		option0.getStyle().setColor(new Color(0f, 1f, 0f, 1f));
		
		gSB = new GSelectionBox(new Vector2(200, 150), options);
		
		checkbox1 = new GCheckbox(new Vector2(20, 200), true, 20);
		
		checkbox1.setActionListener(new GActionListener()
		{
			@Override
			public void onClick()
			{
				layer2_shared.setEnabled(checkbox1.isChecked());
			}

			@Override
			public void onHover()
			{
				
			}
		});
		
		moveButton = new GButton(new Vector2(150, 75), "Move Viewport right", FontScheme.defaultFont);
		
		moveButton.setActionListener(new GActionListener()
		{
			@Override
			public void onHover()
			{
				System.out.println("Hovered move button!");
			}

			@Override
			public void onClick()
			{
				viewport.getOffset().add(1, 0);
			}
		});

		moveButton.getLogic().setDoubleClickingAllowed(true);
		moveButton.getLogic().setDelayMs(50);
		moveButton.getLogic().setActionOnHover(false);
		moveButton.getLogic().setActionOnClick(true);
		moveButton.getLogic().setMultithreading(false); // This will run parallel (with threads) which is in some cases faster (of course unnecessary if you just want to print something to the console).

		increaseScale = new GButton(new Vector2(150, 100), "increase scale", FontScheme.defaultFont);
		
		increaseScale.setActionListener(new GActionListener()
		{
			@Override
			public void onHover()
			{
				
			}

			@Override
			public void onClick()
			{
				//viewport.setScale(viewport.getScale() + 0.0001f);
			}
		});

		increaseScale.getLogic().setDoubleClickingAllowed(true);

		// These both settings are especially interesting for creating zoom-maps, browser-related content or similar stuff.
		// It prevents the "increase scale button" (+) to be changed by the Viewport when scrolling or zooming in/out (as an example).
		{
			increaseScale.getStyle().setMovableForViewport(false);
		}
		
		exit = new GButton(new Vector2(20, 150), "EXIT", FontScheme.defaultFont);
		
		exit.setActionListener(new GActionListener()
		{
			@Override
			public void onHover()
			{
				
			}

			@Override
			public void onClick()
			{
				System.out.println("[Main]: Exiting..");
				System.exit(0);
			}
		});

		exit.getLogic().setActionOnHover(false);
		exit.getLogic().setActionOnClick(true);

		input1 = new GTextfield(new Vector2(20, 300), "GERMAN", 10, FontScheme.defaultFont);
		
		input1.setActionListener(new GActionListener()
		{
			@Override
			public void onClick(){}
			
			@Override
			public void onHover()
			{
				System.out.println("Hover");
			}
		});
		
		input1.getLogic().setInteractable(false);
		input1.getLogic().setActionOnClick(false);

		input2 = new GTextfield(new Vector2(20, 375), "DEUTSCH", 10, FontScheme.defaultFont);
		input3 = new GTextfield(new Vector2(20, 450), "ALEMAN", 10, FontScheme.defaultFont);

		Texture t = ImageToolkit.loadImage(Path.USER_DIR + File.separator + "media" + File.separator + "MountainLake.jpg");

		img0 = new GImage(new Vector2(0, 0), 600, false , t);
		img0.getLogic().setActionOnHover(false);
	}

	public void init()
	{
		biggerFont.setFontColor(Color.RED);

		//DesignColor designColor = new DesignColor(Color.BLACK, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.GRAY, Color.BLACK);

		GBorderProperty borderProperty = new GBorderProperty(0, 1);
		
		GPaddingProperty paddingProperty = new GPaddingProperty(5);

		//design = new Classic(designColor, borderProperty, paddingProperty);

		//display = new Display(1280, 640);

		viewport = new Viewport(false);
		viewport.setOffset(new Vector2(0, 75));
		//viewport.setScale(1f);

		displayContext.setViewport(viewport);

		initComponents();
	}

	public void setupDisplayLayers()
	{
		description = new GDescription(new Vector2(20, 520), "Money here for nothing!", FontScheme.defaultFont);

		layer0.add(img0);

		layer1.add(getPolyButton0());
		layer1.add(getPolyButton1());
		layer1.add(increaseScale);
		layer1.add(moveButton);
		layer1.add(checkbox1);

		layer2_shared.add(description);
		layer2_shared.add(exit);

		layer2_shared.add(input3);
		layer2_shared.add(input2);
		layer2_shared.add(input1);

		layer2_shared.add(gSB);
		layer2_shared.add(rect);


		//displayContext.getViewport().getWindowManager().addWindow(window1);
		//displayContext.getViewport().getWindowManager().addWindow(window0);
	}
	
	public void setupGWindow0()
	{
		GLayer layer5 = new GLayer(0, true);

		Texture imgMountain = ImageToolkit.loadImage(Path.USER_DIR + File.separator + "media" + File.separator + "MountainLake.jpg");

		GImage img0 = new GImage(new Rectangle(0,0,100, 365), imgMountain);

		layer5.add(img0);

		viewportGWindow0.addLayer(layer5);
		viewportGWindow0.addLayer(layer2_shared);
		viewportGWindow0.addLayer(layer1);

		//window0.setViewport(viewportGWindow0);
	}

	public void run()
	{
		layer0 = new GLayer(10, true);
		layer1 = new GLayer(11, true);
		layer2_shared = new GLayer(12, true);
		layer3 = new GLayer(13, true);
		layer4 = new GLayer(14, true);

		setupDisplayLayers();

		displayContext.getViewport().addLayer(layer0);
		displayContext.getViewport().addLayer(layer1);
		displayContext.getViewport().addLayer(layer2_shared);
		displayContext.getViewport().addLayer(layer3);
		displayContext.getViewport().addLayer(layer4);

		setupGWindow0();
	}
}
