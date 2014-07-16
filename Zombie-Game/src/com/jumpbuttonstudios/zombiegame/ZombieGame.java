package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.jumpbuttonstudios.zombiegame.screens.SplashScreen;

public class ZombieGame extends Game {

	/** If the debug lines should be drawn */
	public static boolean DEBUG = true;

	/**
	 * All assets are located in here for easy access, later we will add
	 * Animations and what not via custom loaders
	 */
	public static AssetManager assets = new AssetManager();

	@Override
	public void create() {	
		
		/* Load the splash screen */
		assets.load("UI/JBSLogo.png", Texture.class);
		/* Load the cursor */
		assets.load("UI/Cursors/potHand.png", Pixmap.class);
		
		DEBUG = false;
		assets.finishLoading();
		setScreen(new SplashScreen(this));
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

	/**
	 * Loads all assets, textures, sounds, music etc etc. You name it, it loads
	 * it.
	 */
	public void loadAssets() {

	}
}
