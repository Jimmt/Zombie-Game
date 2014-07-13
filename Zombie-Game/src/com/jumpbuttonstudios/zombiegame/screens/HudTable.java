package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HudTable extends Table {
	Heart[] hearts = new Heart[3];

	public HudTable(Skin skin) {
		super(skin);

		setFillParent(true);
		

		for(int i = 0; i < hearts.length; i++){
			Heart h = new Heart();
			hearts[i] = h;
			
			this.add(h);
		}
		

	}
}
