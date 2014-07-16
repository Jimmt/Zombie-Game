package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.Game;
import com.jumpbuttonstudios.zombiegame.screens.MenuScreen;
import com.jumpbuttonstudios.zombiegame.screens.SplashScreen;

public class ZombieGame extends Game{
	
	/** If the debug lines should be drawn */
	public static boolean DEBUG = true;
	

	
	@Override
	public void create() {		
		setScreen(new SplashScreen(this));
		DEBUG = false;
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
