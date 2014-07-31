package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudios.zombiegame.ZombieGame;

public class OptionsScreen extends AbstractScreen {
	private CheckBox soundBox;
	private TextButton backButton;

	public OptionsScreen(ZombieGame zg) {
		super(zg);
	}
	
	@Override
	public void show(){
		super.show();

		Table table = super.getTable();
	
		table.setFillParent(true);
		
		soundBox = new CheckBox("Sound On", getSkin());
		soundBox.getCells().get(0).size(20, 20);

		table.add(soundBox);
		
		table.row();
		
		
		backButton = new TextButton("Back", getSkin());
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				zg.setScreen(new MenuScreen(zg));
			}
		});
		table.add(backButton).padTop(20);
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}

}
