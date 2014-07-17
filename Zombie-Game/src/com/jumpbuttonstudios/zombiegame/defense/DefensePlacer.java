package com.jumpbuttonstudios.zombiegame.defense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.screens.LevelScreen;

/**
 * @author Jimmt
 */

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
			//unproject screen coordinates to world coordinates
			LevelScreen.b2dCam.unproject(mouse);
			
			//round to nearest integer
			rounded.set((int) (mouse.x), (int) (mouse.y));
			defense.getBody().setActive(false);
			defense.setPosition(rounded.x, rounded.y);

			
			//ghost sprite
			defense.getSprite().setAlpha(0.5f);
			level.getPlayer().setInMenu(true);
			
			if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
				
				//revert 'ghost' sprite to normal alpha
				defense.getSprite().setAlpha(1.0f);
				
				//enable collisions
				defense.getBody().setActive(true);
				defense.setPosition(rounded.x, rounded.y);
				
				//no longer placing, no need to track mouse movement further
				placing = false;
				level.getPlayer().setInMenu(true);
				
			}
			if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
				placing = false;
				level.getPlayer().setInMenu(false);
				defense.destroy(level.getWorld());
				level.getDefenses().removeValue(defense, false);
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
