package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LevelScreen extends AbstractScreen{
	private Player player;
	private Image background;

	public LevelScreen(ZombieGame zg) {
		super(zg);
		
		background = new Image(new Texture(Gdx.files.internal("Environment/BG/Left.png")));
		stage.addActor(background);
		
		player = new Player(getWorld());
		stage.addActor(player);
		
		
	}

	@Override
	public void show(){
		
	}
}
