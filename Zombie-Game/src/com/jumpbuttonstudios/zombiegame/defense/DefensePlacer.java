package com.jumpbuttonstudios.zombiegame.defense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.screens.LevelScreen;

public class DefensePlacer {

	private Defense defense;
	private Level level;
	private boolean placing;
	private Vector3 mouse;
	private Vector2 rounded;

	public DefensePlacer(Level level) {
		this.level = level;
		mouse = new Vector3();
		rounded = new Vector2();
	}

	public void update(float delta) {

		if (placing) {
			mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			LevelScreen.b2dCam.unproject(mouse);
			rounded.set((int) (mouse.x), (int) (mouse.y));
			defense.getBody().setActive(false);
			defense.getSprite().setX(rounded.x);
			defense.getSprite().setY(rounded.y);
			defense.getSprite().setAlpha(0.5f);
			level.getPlayer().setInMenu(true);
			
			if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
				defense.getSprite().setAlpha(1.0f);
				defense.getBody().setActive(true);
				defense.setPosition(rounded.x, rounded.y);
				placing = false;
				level.getPlayer().setInMenu(true);
				
			}
			
			
		}
		
	}

	public void setPlacing(boolean placing) {
		this.placing = placing;
	}

	public boolean isPlacing() {
		return placing;
	}

	public void setDefense(Defense defense) {
		this.defense = defense;
		
	}

	public Defense getDefense() {
		return defense;
	}

}
