package com.roaringcatgames.bunkerjunker;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.roaringcatgames.bunkerjunker.screens.GameScreen;
import com.roaringcatgames.bunkerjunker.screens.LoadingScreen;
import com.roaringcatgames.bunkerjunker.screens.MenuScreen;
import com.roaringcatgames.kitten2d.gdx.helpers.IGameProcessor;
import com.roaringcatgames.kitten2d.gdx.helpers.IPreferenceManager;
import com.roaringcatgames.kitten2d.gdx.helpers.K2PreferenceManager;

public class BunkerJunker extends Game implements IGameProcessor {
	SpriteBatch batch;
	OrthographicCamera cam;
	OrthographicCamera guiCam;
	Viewport viewport;

	private InputMultiplexer multiplexer;
	private IPreferenceManager prefManager = new K2PreferenceManager("ld37_prefs");

	////////////
	//Screens
	////////////
	private MenuScreen menuScreen;
	private GameScreen gameScreen;

	public BunkerJunker(){
	   super();
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new OrthographicCamera(AppConstants.W, AppConstants.H);
		viewport = new ExtendViewport(AppConstants.W, AppConstants.H, cam);
		viewport.apply();
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		AppConstants.setAppWH(viewport.getWorldWidth(), viewport.getWorldHeight());

		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0f);


		multiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(multiplexer);

		Assets.load();

		setScreen(new LoadingScreen(this));

		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);

	}

	private float r = 181f/255f;
	private float g = 216f/255f;
	private float b = 255f/255f;
	private float a = 255f/255f;

	@Override
	public void render () {
		Gdx.gl.glClearColor(r, g, b, a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		guiCam.update();
		super.render();
	}

	@Override
	public void switchScreens(String screenName) {

		multiplexer.clear();

		switch(screenName){
			case "MENU":
				setScreen(menuScreen);
				break;
			case "GAME":
				setScreen(gameScreen);
				break;
		}
	}

	@Override
	public void addInputProcessor(InputProcessor processor) {
		this.multiplexer.addProcessor(processor);
	}

	@Override
	public void removeInputProcessor(InputProcessor processor) {
		this.multiplexer.removeProcessor(processor);
	}

	@Override
	public void pauseBgMusic() {

	}

	@Override
	public void playBgMusic(String musicName) {

	}

	@Override
	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public OrthographicCamera getCamera() {
		return cam;
	}

	@Override
	public OrthographicCamera getGUICamera() {
		return guiCam;
	}

	@Override
	public Viewport getViewport() {
		return viewport;
	}

	@Override
	public IPreferenceManager getPreferenceManager() {
		return prefManager;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if(viewport != null) {
			viewport.update(width, height);
			cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		}
	}
}
