import com.badlogic.gdx.graphics.g2d.Batch;
import org.lwjgl.Sys;
import org.thirdreality.horizonengine.HorizonApplication;
import org.thirdreality.horizonengine.HorizonEngine;
import org.thirdreality.horizonengine.core.GameLayer;
import org.thirdreality.horizonengine.core.GameObject;

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
				ArrayList<GameObject> objects = new ArrayList<GameObject>();

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

				objects.add(object0);
				objects.add(object1);
				objects.add(object2);

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

				objects.add(gameLayer0);

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

				objects.add(gameLayer1);

				horizonApp.manager.addGameObjects(objects);

				horizonApp.manager.update();
				horizonApp.manager.printReadyGameObjects();

				System.out.println("END");
			}

			@Override
			public void loop()
			{

			}

			@Override
			public void post()
			{

			}
		};

		HorizonEngine.start(horizonApp);
	}
}
