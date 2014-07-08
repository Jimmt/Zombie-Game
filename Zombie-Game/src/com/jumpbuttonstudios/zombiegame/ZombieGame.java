package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.Game;
import com.jumpbuttonstudios.zombiegame.screens.MenuScreen;

public class ZombieGame extends Game{

	
	@Override
	public void create() {		
		setScreen(new MenuScreen(this));
		
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
