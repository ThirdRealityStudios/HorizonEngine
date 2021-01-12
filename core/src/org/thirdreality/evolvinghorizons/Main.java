package org.thirdreality.evolvinghorizons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import jdk.jfr.EventSettings;
import org.thirdreality.evolvinghorizons.screen.LoadingScreen;

import javax.swing.*;

public class Main extends Game {
	SpriteBatch batch;
	Texture img;

	LoadingScreen loadingScreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		loadingScreen = new LoadingScreen();

		setScreen(loadingScreen);

		org.thirdreality.evolvinghorizons.guinness.sample.Main.main(null);
	}

	@Override
	public void render () {
		getScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
