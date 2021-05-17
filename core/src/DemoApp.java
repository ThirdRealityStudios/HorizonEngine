import com.badlogic.gdx.graphics.g2d.Batch;
import org.thirdreality.horizonengine.HorizonApplication;
import org.thirdreality.horizonengine.HorizonEngine;
import org.thirdreality.horizonengine.core.game.environment.GameLayer;
import org.thirdreality.horizonengine.core.game.environment.GameObject;
import org.thirdreality.horizonengine.core.game.management.LOD;

import java.util.ArrayList;

public class DemoApp
{
	private HorizonApplication horizonApp;

	public static void main (String[] arg)
	{
		DemoApp launcher = new DemoApp();

		launcher.run();
	}

	private void run()
	{
		horizonApp = new HorizonApplication()
		{
			@Override
			public void pre()
			{
				ArrayList<GameObject> objectsNearby = new ArrayList<GameObject>();

				GameObject object0 = new GameObject()
				{
					@Override
					public void render(Batch batch)
					{

					}
				};
				object0.setAlias("Object 0");

				GameObject object1 = new GameObject()
				{
					@Override
					public void render(Batch batch)
					{

					}
				};
				object1.setAlias("Object 1");

				GameObject object2 = new GameObject()
				{
					@Override
					public void render(Batch batch)
					{

					}
				};
				object2.setAlias("Object 2");

				objectsNearby.add(object0);
				objectsNearby.add(object1);
				objectsNearby.add(object2);

				GameObject object3 = new GameObject()
				{
					@Override
					public void render(Batch batch)
					{

					}
				};
				object3.setAlias("Object 3");

				GameLayer gameLayer0 = new GameLayer(2);
				gameLayer0.setAlias("Game Layer 0");
				gameLayer0.add(object3);

				objectsNearby.add(gameLayer0);

				GameObject object4 = new GameObject()
				{
					@Override
					public void render(Batch batch)
					{

					}
				};
				object4.setAlias("Object 4");

				GameLayer gameLayer1 = new GameLayer(5);
				gameLayer1.setAlias("Game Layer 1");
				gameLayer1.add(object4);

				GameLayer gameLayer2 = new GameLayer(7);
				gameLayer2.setAlias("Game Layer 2");
				gameLayer2.add(object4);

				gameLayer1.add(gameLayer2);

				objectsNearby.add(gameLayer1);

				LOD lodNearby = horizonApp.manager.lod.addObjectsDirectly(objectsNearby);

				GameObject object5 = new GameObject() {
					@Override
					public void render(Batch batch) {

					}
				};
				object5.setAlias("Object 5");

				GameObject object6 = new GameObject() {
					@Override
					public void render(Batch batch) {

					}
				};
				object6.setAlias("Object 6");

				ArrayList<GameObject> objectsFar = new ArrayList<GameObject>();
				objectsFar.add(object5);
				objectsFar.add(object6);

				LOD lodFar = new LOD(200);
				lodFar.addObjects(objectsFar);

				horizonApp.manager.lod.add(lodFar);

				lodNearby.printReadyGameObjects();
				lodFar.printReadyGameObjects();
			}

			@Override
			public void loop()
			{

			}

			@Override
			public void post()
			{
				System.out.println("End of procasdasd");
			}
		};

		HorizonEngine.start(horizonApp);
	}
}
