package org.thirdreality.evolvinghorizons;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.evolvinghorizons.engine.gui.component.GComponent;
import org.thirdreality.evolvinghorizons.engine.gui.environment.UIScreen;
import org.thirdreality.evolvinghorizons.engine.settings.Path;
import org.thirdreality.evolvinghorizons.engine.HorizonGame;
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

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.ArrayList;

public class SampleApplication extends HorizonGame
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

	private UIScreen gui;

	private boolean exitGame = false;

	@Override
	public void create()
	{
		gui = new UIScreen()
		{
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
		};

		mcFont = Gdx.files.internal("font/DEFAULT_MONO.ttf");
		smallerFont = new Font(mcFont, 18);
		biggerFont = new Font(mcFont, 25);

		initComponents();

		postInit();

		setScreen(gui);

		GPolyButton polyButton = getPolyButton0();
		polyButton.setZoomable(true);

		gSB = new GTickBoxList(new Vector2(20, 20), 0, true, smallerFont);
		gSB.addOption("Hello");
		gSB.addOption("ASDF - -");
		gSB.addOption("Hell asdas dasd".toUpperCase());
		gSB.addOption("Hellö, it's me.");

		GComponent[] layer1_components = new GComponent[]{polyButton};
		GLayer layer1 = new GLayer(layer1_components, 1);

		GComponent[] layer0_components = new GComponent[]{input1, input2, input3, gSB};
		GLayer layer0 = new GLayer(layer0_components, 0);

		gui.setLayers(new GLayer[]{layer0, layer1});
		gui.setZoomAcceleration(1f);
		gui.setNavigationSpeed(10000);
		gui.allowFocusOnZoom(true);
	}

	private GPolyButton getPolyButton0()
	{
		//float[] vertices = new float[]{50,100, 150,100, 100,150};

		//float[] vertices = new float[]{0,100,50,100,66.66f,50,116.66f,100,100,150,83.33f,116.66f,50,150};

		//float[] vertices = new float[]{0,129.5f, 24,128, 58,134.5f, 78,119.5f, 109.5f,116, 107,83, 77.5f,93, 104,73.5f, 96,38.5f, 76,26.5f, 57,49, 56,72, 43,37.5f, 70,0, 108.5f,22, 123,56.5f, 128,95, 137.5f,127.5f, 75.5f,183, 71,176, 89.5f,144.5f, 5,159};

		//float[] vertices = new float[]{0,2.5f, 34.5f,11.5f, 27.5f,0, 54.5f,14, 62.5f,4, 74,12, 84.5f,5.5f, 116,21, 83.5f,64.5f, 64.5f,105, 35.5f,63, 27.5f,66.5f};

		//float[] vertices = new float[]{10,0, 20,0, 20,10, 30,10, 40,40, 25,50, 5f,10f, 10,1f};

		float[] germany = new float[]{13.50184631f,50.6336441f,12.51204777f,50.39725876f,12.33455372f,50.17174911f,12.18445301f,50.32232285f,12.10090065f,50.3180275f,12.54773045f,49.92034149f,12.40071774f,49.75384903f,12.65589333f,49.43454742f,13.8395071f,48.77160645f,13.72735977f,48.51300812f,13.5089922f,48.59060287f,13.33108902f,48.32435608f,12.75825882f,48.1263237f,13.00329494f,47.85031128f,12.90504646f,47.72364426f,13.08079433f,47.68702316f,13.00624561f,47.46398926f,12.78111458f,47.67414093f,12.25697231f,47.74300766f,12.20388222f,47.60681152f,11.63713932f,47.59425735f,11.272892f,47.39785385f,10.45445919f,47.55573654f,10.43642426f,47.38067627f,10.17397976f,47.27026367f,10.23619652f,47.38186264f,9.97089863f,47.545681f,9.56106186f,47.50240326f,8.56785202f,47.80848312f,8.40555954f,47.67451477f,8.60662365f,47.67211914f,8.58254242f,47.59610367f,7.67821646f,47.53295898f,7.51159f,47.69675446f,7.57758522f,48.12065506f,7.83974028f,48.64127731f,8.23263264f,48.96657181f,7.44564676f,49.1841507f,6.73860073f,49.1636734f,6.36708355f,49.46951294f,6.53068209f,49.80624008f,6.11256266f,50.05922699f,6.40787029f,50.3351326f,5.97486162f,50.79797363f,6.09392452f,50.92132568f,5.86709642f,51.04668045f,6.17541504f,51.1584816f,6.07265711f,51.24258804f,6.22613001f,51.36051941f,5.94502974f,51.82365799f,6.73260736f,51.89870834f,6.83041906f,51.98620605f,6.69465303f,52.06980133f,7.06586838f,52.24126053f,6.99180269f,52.467453f,6.68086815f,52.5533371f,7.05557728f,52.64337158f,7.24916697f,53.32986069f,6.99805403f,53.3718071f,7.23472214f,53.66485977f,8.02305508f,53.70902634f,8.17249966f,53.5545845f,8.07194424f,53.46708298f,8.25249958f,53.39902878f,8.27194309f,53.60985947f,8.57083416f,53.51819611f,8.48361111f,53.69430542f,8.68083382f,53.89208221f,9.27458382f,53.85757828f,9.58665752f,53.58641815f,9.68495083f,53.54785156f,9.81652641f,53.52934647f,9.9344368f,53.53992081f,9.55545521f,53.62073517f,9.57988071f,53.62354279f,9.52201939f,53.71337509f,9.32726669f,53.86026001f,8.90638828f,53.93347168f,8.81972218f,54.02430725f,8.98583221f,54.06124878f,8.92638779f,54.13236237f,8.83418083f,54.13680649f,8.80916691f,54.19152832f,8.95194435f,54.31289291f,8.84638786f,54.26291656f,8.5797224f,54.30958176f,9.02361107f,54.4734726f,8.80638885f,54.47041702f,8.89027691f,54.59263992f,8.59402561f,54.88165665f,8.41638756f,54.84708405f,8.31027794f,54.87430573f,8.27972221f,54.75180435f,8.2974987f,54.90791702f,8.40027905f,55.05374908f,8.42972088f,54.87763977f,8.94793892f,54.902565f,9.43669701f,54.78874588f,9.61316872f,54.87723541f,9.78574753f,54.73097229f,9.83707809f,54.74393082f,9.87020683f,54.74114609f,9.88074303f,54.72066498f,9.91403961f,54.73010254f,9.9214859f,54.70988083f,9.96193409f,54.71413803f,10.03361034f,54.69208145f,10.03750134f,54.68264008f,9.98250103f,54.70097351f,9.93805504f,54.67291641f,9.93305683f,54.62763977f,9.77027893f,54.58069611f,9.76861095f,54.56513977f,9.7137394f,54.53307343f,9.71218204f,54.52009583f,9.54860973f,54.51235962f,9.57361221f,54.47541809f,9.71472263f,54.49124908f,9.93805504f,54.62347412f,9.94805622f,54.67375183f,10.03416634f,54.66986084f,9.84027767f,54.46736145f,10.19916725f,54.45597076f,10.13194561f,54.31124878f,10.3186121f,54.43569565f,10.7047224f,54.30486298f,11.12805557f,54.39069366f,11.09361076f,54.19791794f,10.75416565f,54.05486298f,10.89083195f,53.95569611f,11.45250034f,53.89986038f,11.37805557f,53.99736023f,12.12472153f,54.15013885f,12.51083469f,54.48291779f,12.92695141f,54.42796326f,12.69316006f,54.43225479f,12.43583298f,54.37874985f,12.36361217f,54.26597214f,13.01972198f,54.43902588f,13.10583305f,54.28180695f,13.45694542f,54.09069443f,13.80694485f,54.10319519f,13.74416637f,54.0293045f,13.9141674f,53.92235947f,13.82472229f,53.86624908f,14.04694462f,53.99652863f,13.76916599f,54.01902771f,13.81249905f,54.09902954f,13.74916744f,54.1590271f,13.8036108f,54.17847061f,14.22644329f,53.92909241f,14.45056534f,53.26225281f,14.12293053f,52.83766174f,14.63911057f,52.57299805f,14.53435707f,52.39500809f,14.7590456f,52.06476212f,14.59014606f,51.82102585f,15.04179573f,51.27420807f,14.79686546f,50.82114792f,14.30185795f,51.05504608f,14.38763523f,50.89908981f,13.50184631f,50.6336441f};

		org.thirdreality.evolvinghorizons.engine.math.Polygon poly = new org.thirdreality.evolvinghorizons.engine.math.Polygon(germany, true);

		poly.setOrigin(poly.getBoundingRectangle().x + poly.getBoundingRectangle().width / 2, poly.getBoundingRectangle().y + poly.getBoundingRectangle().height / 2);
		poly.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		poly.setScale(80f,80f);

		GPolyButton gPolyButton = new GPolyButton(poly, 0, "CLICK ME", smallerFont);

		Line2D.Float line3 = new Line2D.Float(0,0,20,10);
		Line2D.Float line2 = new Line2D.Float(0,10,20,10);
		Line2D.Float line1 = new Line2D.Float(0,-10,20,10);

		Line2D.Float line0 = new Line2D.Float(0,0,20,10);

		ArrayList<Line2D.Float> lines = new ArrayList<Line2D.Float>();
		lines.add(line0);

		Line2D.Float line = new Line2D.Float(0,100,50,100);

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

		//window0 = new GWindow("Sample window", smallerFont, windowRepresentation, borderProperties, null);
		//window1 = new GWindow("..Second window..", smallerFont, windowRepresentation, borderProperties, null);

		Color transparentRed = new Color(1f, 0, 0, 0.5f);

		rect = new GRectangle(new Rectangle(0, -50, 800, 136), 0, new Color(1f, 0f, 0f, 0.1f));
		rect.getStyle().getBorderProperties().setBorderRadiusPx(14);

		// The button ("start" variable) is focused later during runtime instead.
		rect.getLogic().setFocusable(false);

		checkbox1 = new GCheckbox(new Vector2(20, 200), 0, true);

		moveButton = new GButton(new Vector2(150, 75), 0, "Move Viewport right", smallerFont);

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

			}
		});

		moveButton.getLogic().setDoubleClickingAllowed(true);
		moveButton.getLogic().setDelayMs(50);
		moveButton.getLogic().setActionOnHover(false);
		moveButton.getLogic().setActionOnClick(true);
		moveButton.getLogic().setMultithreading(false); // This will run parallel (with threads) which is in some cases faster (of course unnecessary if you just want to print something to the console).

		increaseScale = new GButton(new Vector2(150, 100), 0, "increase scale", smallerFont);

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

		exit = new GButton(new Vector2(20, 150), 0, "EXIT", smallerFont);

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

		input1 = new GTextfield(new Vector2(20, 300), 1, "GERMAN", 10, smallerFont);

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

		input2 = new GTextfield(new Vector2(20, 375), 2, "DEUTSCH", 10, smallerFont);

		input3 = new GTextfield(new Vector2(20, 450), 3, "ALEMAN", 10, smallerFont);

		Texture t = new Texture(Gdx.files.internal(Path.MEDIA_FOLDER + File.separator + "MountainLake.jpg"));

		img0 = new GImage(new Vector2(0, 0), 0, 600, false , t);
		img0.getLogic().setActionOnHover(false);
	}

	public void setupDisplayLayers()
	{
		description = new GDescription(new Vector2(20, 520), 0, "Money here for nothing!", smallerFont);

		/*
		layer0.add(img0);

		layer1.add(getPolyButton0());
		//layer1.add(getPolyButton1());
		layer1.add(increaseScale);
		layer1.add(moveButton);
		layer1.add(checkbox1);

		layer2_shared.add(description);
		layer2_shared.add(exit);

		layer2_shared.add(input3);
		layer2_shared.add(input2);
		layer2_shared.add(input1);

		layer2_shared.add(gSB);
		//layer2_shared.add(rect);
		 */


		//displayContext.getViewport().getWindowManager().addWindow(window1);
		//displayContext.getViewport().getWindowManager().addWindow(window0);
	}

	public void postInit()
	{
		setupDisplayLayers();

		//renderContext.getRenderScreen().addLayer(layer0);
		//renderContext.getRenderScreen().addLayer(layer1);
		//renderContext.getRenderScreen().addLayer(layer2_shared);
		//renderContext.getRenderScreen().addLayer(layer3);
		//renderContext.getRenderScreen().addLayer(layer4);
	}
}
