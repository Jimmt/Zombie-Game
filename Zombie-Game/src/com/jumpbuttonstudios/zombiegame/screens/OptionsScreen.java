package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.jumpbuttonstudios.zombiegame.ZombieGame;

public class OptionsScreen extends AbstractScreen {
	private CheckBox soundBox;

	public OptionsScreen(ZombieGame zg) {
		super(zg);
		
		Table table = super.getTable();
	
		table.setFillParent(true);
		
		soundBox = new CheckBox("Sound On", getSkin());
		soundBox.getCells().get(0).size(20, 20);

		table.add(soundBox);
	}
	
	@Override
	public void show(){
		super.show();
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}

}
