package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ZombieGame;
import com.jumpbuttonstudios.zombiegame.asset.Assets;

public class MenuScreen extends AbstractScreen {

	public MenuScreen(ZombieGame zg) {
		super(zg);
		// TODO Auto-generated constructor stub
	}

	public void show() {
		super.show();
		Table table = super.getTable();
		table.setFillParent(true);
		table.debug();

		table.add("Zombie Game");

		table.row();
		
		Image background = new Image(new Texture(Gdx.files.internal(Assets.MENU_BACKGROUND)));
		background.setFillParent(true);
		table.addActor(background);
		
		
		
		TextButton startButton = new TextButton("Start", getSkin());
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				zg.setScreen(new LevelScreen(zg));
			}
		});
		table.add(startButton).fill().prefWidth(Constants.WIDTH / 3)
				.prefHeight(Constants.HEIGHT / 10);
		TextButton optionsButton = new TextButton("Options", getSkin());
		optionsButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){

				zg.setScreen(new OptionsScreen(zg));
			}
			
		});
		table.row();
		table.add(optionsButton).fill().prefWidth(Constants.WIDTH / 3)
				.prefHeight(Constants.HEIGHT / 10);
		
	}

}
