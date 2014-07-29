package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudios.zombiegame.ZombieGame;

public class GameOverScreen extends AbstractScreen {
	private TextButton backButton;

	public GameOverScreen(ZombieGame zg) {
		super(zg);
		
	}
	
	@Override
	public void show(){
		super.show();
		
		Table table = super.getTable();
		table.setFillParent(true);
		table.add("Game Over");
		table.row();	
		backButton = new TextButton("Back", getSkin());
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				zg.setScreen(new MenuScreen(zg));
			}
		});
		table.add(backButton);
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}

}
