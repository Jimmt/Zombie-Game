package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jumpbuttonstudios.zombiegame.ZombieGame;

public class SplashScreen extends AbstractScreen {
	
	/** The JBS Logo */
	private Image splashImage;

	public SplashScreen(ZombieGame zg) {
		super(zg);

	}

	public void show() {
		super.show();
		
		cursor = new Pixmap(Gdx.files.internal("UI/Cursors/potHand.png"));

		Gdx.input.setCursorImage(cursor, 17, 16);
		
		splashImage = new Image(new Texture(Gdx.files.internal("UI/JBSLogo.png")));
		splashImage.setFillParent(true);
		splashImage.setColor(splashImage.getColor().r, splashImage.getColor().g, splashImage.getColor().b, 0);

		Action switchScreenAction = new Action() {
			public boolean act(float delta) {
				zg.setScreen(new LoadingScreen(zg));
				return true;
			}
		};

		if(ZombieGame.DEBUG){
			splashImage.addAction(switchScreenAction);
		} else {
		splashImage.addAction(Actions.sequence(Actions.fadeIn(0.5f), Actions.delay(0.5f),
				Actions.fadeOut(0.5f), switchScreenAction));
		}
		stage.addActor(splashImage);
	}
}
