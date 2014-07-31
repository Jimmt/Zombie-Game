package com.jumpbuttonstudios.zombiegame;

import net.dermetfan.utils.libgdx.AnnotationAssetManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Logger;
import com.jumpbuttonstudios.zombiegame.screens.SplashScreen;

public class ZombieGame extends Game {

	/** If the debug lines should be drawn */
	public static boolean DEBUG = true;

	/**
	 * All assets are located in here for easy access, later we will add
	 * Animations and what not via custom loaders
	 */
	public static AnnotationAssetManager assets = new AnnotationAssetManager();

	@Override
	public void create() {	
		
		/* Set logger to output debug messages */
		Gdx.app.setLogLevel(Logger.DEBUG);
		
		/* Load the splash screen */
		assets.load("UI/JBSLogo.png", Texture.class);
		/* Load the cursor */
		assets.load("UI/Cursors/potHand.png", Pixmap.class);
		
		DEBUG = true;
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
