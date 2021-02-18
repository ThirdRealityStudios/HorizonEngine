package org.thirdreality.evolvinghorizons;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.math.LinTools;
import org.thirdreality.evolvinghorizons.engine.settings.Path;
import org.thirdreality.evolvinghorizons.engine.DisplayContext;
import org.thirdreality.evolvinghorizons.engine.Viewport;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.GImage;
import org.thirdreality.evolvinghorizons.engine.gui.component.decoration.GRectangle;
import org.thirdreality.evolvinghorizons.engine.gui.component.input.GTextfield;
import org.thirdreality.evolvinghorizons.engine.gui.component.optional.GActionListener;
import org.thirdreality.evolvinghorizons.engine.gui.component.placeholder.GWindow;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.GCheckbox;
import org.thirdreality.evolvinghorizons.engine.gui.component.selection.list.GTickBoxList;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.GButton;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.GDescription;
import org.thirdreality.evolvinghorizons.engine.gui.component.standard.GPolyButton;
import org.thirdreality.evolvinghorizons.engine.container.style.property.GBorderProperty;
import org.thirdreality.evolvinghorizons.engine.gui.font.Font;
import org.thirdreality.evolvinghorizons.engine.gui.layer.GLayer;
import org.thirdreality.evolvinghorizons.engine.io.ComponentHandler;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class SampleApplication extends Game
{
	private GRectangle rect;

	private GButton moveButton, increaseScale, exit;

	private GTextfield input1, input2, input3;

	private GImage img0;

	private GDescription description;

	private GCheckbox checkbox1;

	private GTickBoxList gSB;

	private GWindow window0, window1;

	private GLayer layer0, layer1, layer2_shared, layer3, layer4;

	private FileHandle mcFont;

	private Font biggerFont, smallerFont;

	private Viewport primaryViewport;

	// For simulating on a GWindow.
	private Viewport viewportGWindow0;

	private DisplayContext displayContext;

	private ComponentHandler input;

	private CopyOnWriteArrayList<Viewport> viewports;

	private Screen gameScreen;

	private boolean exitGame = false;

	private InputProcessor gameInput;

	@Override
	public void create()
	{
		getPolyButton0();

		gameInput = new InputProcessor()
		{
			@Override
			public boolean keyDown(int keycode)
			{
				return false;
			}

			@Override
			public boolean keyUp(int keycode)
			{
				return false;
			}

			@Override
			public boolean keyTyped(char character)
			{
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button)
			{
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button)
			{
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer)
			{
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY)
			{
				return false;
			}

			@Override
			public boolean scrolled(float amountX, float amountY)
			{
				return false;
			}
		};

		mcFont = Gdx.files.internal("font/DEFAULT_MONO.ttf");
		smallerFont = new Font(mcFont, 18);
		biggerFont = new Font(mcFont, 25);

		initViewport();

		// You always need to have at least one input processor active currently..
		this.input = new ComponentHandler(primaryViewport, gameInput);

		Gdx.input.setInputProcessor(input);

		this.displayContext = new DisplayContext();
		this.displayContext.setViewport(primaryViewport);

		initComponents();

		postInit();

		gameScreen = new Screen()
		{
			@Override
			public void show()
			{

			}

			@Override
			public void render(float delta)
			{
				displayContext.render(delta);
			}

			@Override
			public void resize(int width, int height)
			{

			}

			@Override
			public void pause()
			{

			}

			@Override
			public void resume()
			{

			}

			@Override
			public void hide()
			{

			}

			@Override
			public void dispose()
			{

			}
		};

		setScreen(gameScreen);
	}

	@Override
	public void render()
	{
		// Renders the screens with the superclass method.
		super.render();
	}
	
	@Override
	public void dispose(){}

	private GPolyButton getPolyButton0()
	{
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

		pixmap.setColor(Color.RED);
		pixmap.fill();

		Texture texture = new Texture(pixmap);

		TextureRegion textureRegion = new TextureRegion(texture, 1, 1);

		//float[] vertices = new float[]{0,100,50,100,66.66f,50,116.66f,100,100,150,83.33f,116.66f,50,150};

		//float[] vertices = new float[]{0,129.5f, 24,128, 58,134.5f, 78,119.5f, 109.5f,116, 107,83, 77.5f,93, 104,73.5f, 96,38.5f, 76,26.5f, 57,49, 56,72, 43,37.5f, 70,0, 108.5f,22, 123,56.5f, 128,95, 137.5f,127.5f, 75.5f,183, 71,176, 89.5f,144.5f, 5,159};

		//float[] vertices = new float[]{0,2.5f, 34.5f,11.5f, 27.5f,0, 54.5f,14, 62.5f,4, 74,12, 84.5f,5.5f, 116,21, 83.5f,64.5f, 64.5f,105, 35.5f,63, 27.5f,66.5f};

		float[] vertices = new float[]{0,0, 10,0, 10,10, 20,10};

		org.thirdreality.evolvinghorizons.engine.math.Polygon poly = new org.thirdreality.evolvinghorizons.engine.math.Polygon(vertices);

		Line2D.Float line3 = new Line2D.Float(0,0,20,10);
		Line2D.Float line2 = new Line2D.Float(0,10,20,10);
		Line2D.Float line1 = new Line2D.Float(0,-10,20,10);

		Line2D.Float line0 = new Line2D.Float(0,0,20,10);

		ArrayList<Line2D.Float> lines = new ArrayList<Line2D.Float>();
		lines.add(line0);

		Line2D.Float line = new Line2D.Float(0,100,50,100);

		//short[] triangles = new short[]{0,1,6, 1,5,6, 1,3,5, 1,2,3, 3,5,4};

		//PolygonRegion polygonRegion = new PolygonRegion(textureRegion, poly.getVertices(), triangles);//poly.getTriangles());

		//PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
		//polygonSprite.setOrigin(450, 370);

		/*
		GPolyButton gPolyButton = new GPolyButton(polygonSprite, "CLICK ME", smallerFont);

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

		gPolyButton.getStyle().setTextAlign(1);

		return gPolyButton;

		 */

		return null;
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

		GPolyButton gPolyButton = null;//new GPolyButton(poly, "Button", smallerFont, poly);

		/*
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
		*/

		return gPolyButton;
	}

	private void initComponents()
	{
		Rectangle windowRepresentation = new Rectangle(400, 0, 600, 400);

		GBorderProperty borderProperties = new GBorderProperty(10, 5);

		// Tell the Viewport it is simulated (in a simulated display environment) by passing 'null' to its constructor.
		viewportGWindow0 = new Viewport(true);

		//window0 = new GWindow("Sample window", smallerFont, windowRepresentation, borderProperties, null);
		//window1 = new GWindow("..Second window..", smallerFont, windowRepresentation, borderProperties, null);

		Color transparentRed = new Color(1f, 0, 0, 0.5f);

		rect = new GRectangle(new Rectangle(0, -50, 800, 136), new Color(1f, 0f, 0f, 0.1f));
		rect.getStyle().getBorderProperties().setBorderRadiusPx(14);

		// The button ("start" variable) is focused later during runtime instead.
		rect.getLogic().setFocusable(false);

		gSB = new GTickBoxList(new Vector2(200, 150), true, smallerFont);
		gSB.addOption("Hello");
		gSB.addOption("ASDF - -");
		gSB.addOption("Hell asdas dasd".toUpperCase());
		gSB.addOption("Hellö, it's me.");

		checkbox1 = new GCheckbox(new Vector2(20, 200), true);

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

		moveButton = new GButton(new Vector2(150, 75), "Move Viewport right", smallerFont);

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
				primaryViewport.getOffset().add(1, 0);
			}
		});

		moveButton.getLogic().setDoubleClickingAllowed(true);
		moveButton.getLogic().setDelayMs(50);
		moveButton.getLogic().setActionOnHover(false);
		moveButton.getLogic().setActionOnClick(true);
		moveButton.getLogic().setMultithreading(false); // This will run parallel (with threads) which is in some cases faster (of course unnecessary if you just want to print something to the console).

		increaseScale = new GButton(new Vector2(150, 100), "increase scale", smallerFont);

		increaseScale.setActionListener(new GActionListener()
		{
			@Override
			public void onHover()
			{

			}

			@Override
			public void onClick()
			{
				//primaryViewport.setScale(primaryViewport.getScale() + 0.0001f);
			}
		});

		increaseScale.getLogic().setDoubleClickingAllowed(true);

		// These both settings are especially interesting for creating zoom-maps, browser-related content or similar stuff.
		// It prevents the "increase scale button" (+) to be changed by the Viewport when scrolling or zooming in/out (as an example).
		{
			increaseScale.getStyle().setMovableForViewport(false);
		}

		exit = new GButton(new Vector2(20, 150), "EXIT", smallerFont);

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

		input1 = new GTextfield(new Vector2(20, 300), "GERMAN", 10, smallerFont);

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

		input2 = new GTextfield(new Vector2(20, 375), "DEUTSCH", 10, smallerFont);

		input3 = new GTextfield(new Vector2(20, 450), "ALEMAN", 10, smallerFont);

		Texture t = new Texture(Gdx.files.internal(Path.MEDIA_FOLDER + File.separator + "MountainLake.jpg"));

		img0 = new GImage(new Vector2(0, 0), 600, false , t);
		img0.getLogic().setActionOnHover(false);
	}

	public void initViewport()
	{
		primaryViewport = new Viewport(false);
		primaryViewport.setOffset(new Vector2(0, 75));
		//primaryViewport.setScale(1f);
	}

	public void setupDisplayLayers()
	{
		description = new GDescription(new Vector2(20, 520), "Money here for nothing!", smallerFont);

		layer0.add(img0);

		//layer1.add(getPolyButton0());
		//layer1.add(getPolyButton1());
		layer1.add(increaseScale);
		//layer1.add(moveButton);
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

		Texture imgMountain = new Texture(Gdx.files.internal(Path.MEDIA_FOLDER + File.separator + "MountainLake.jpg"));

		GImage img0 = new GImage(new Rectangle(0,0,100, 365), imgMountain);

		layer5.add(img0);

		viewportGWindow0.addLayer(layer5);
		viewportGWindow0.addLayer(layer2_shared);
		viewportGWindow0.addLayer(layer1);

		//window0.setViewport(viewportGWindow0);
	}

	public void postInit()
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
