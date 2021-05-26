import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import org.thirdreality.horizonengine.HorizonApp;
import org.thirdreality.horizonengine.HorizonEngine;
import org.thirdreality.horizonengine.core.game.HorizonGame;
import org.thirdreality.horizonengine.core.game.environment.Map;
import org.thirdreality.horizonengine.core.game.object.GameObject;

import java.util.ArrayList;

public class DemoApp
{
	private HorizonApp horizonApp;
	private HorizonGame demoGame;

	public static void main (String[] arg)
	{
		DemoApp launcher = new DemoApp();

		launcher.run();
	}

	public DemoApp()
	{
		horizonApp = new HorizonApp("Horizon App - Demo", 1280, 720);

		HorizonEngine.start(horizonApp);
	}

	private void run()
	{
		demoGame = new HorizonGame()
		{
			public void init()
			{
				setMap(createMap());

				HorizonEngine.info("Created Map contains " + getMap().amountOfTiles() + " Tile(s)");
			}
		};

		horizonApp.setGame(demoGame);
	}

	private Map createMap()
	{
		Map map = new Map();

		ArrayList<GameObject> objects = createObjects();

		map.initialize(objects);

		return map;
	}

	private GameObject createEmptyObject(String alias, Vector2 position)
	{
		GameObject object = new GameObject()
		{
			@Override
			public void render(Batch batch)
			{

			}
		};

		object.setAlias(alias);
		object.setPosition(position);

		return object;
	}

	private ArrayList<GameObject> createObjects()
	{
		ArrayList<GameObject> objects = new ArrayList<GameObject>();

		objects.add(createEmptyObject("Object_0", new Vector2(100, 100)));
		objects.add(createEmptyObject("Object_1", new Vector2(0, 100)));

		return objects;
	}
}
