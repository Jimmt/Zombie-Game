/**
 * Copyright 2014 Stephen Gibson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jumpbuttonstudios.zombiegame.weapons.drops;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.player.Player;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.weapons.Weapon;

/**
 * 
 * @author Stephen Gibson
 */
public class WeaponDrop extends Drop {

	/** The weapon in this drop */
	Weapon weapon;

	/**
	 * Creates a new weapon drop
	 * 
	 * @param level
	 *            the level instance
	 * @param the
	 *            position to create this weapon drop
	 * @param weapon
	 *            the weapon inside the weapon drop
	 */
	public WeaponDrop(Level level, Vector2 position, Weapon weapon) {
		super(level, position, weapon.getIcon());
		this.weapon = weapon;
	}


	@Override
	public void pickup(Character parent) {
		/*
		 * Set the alpha for the icon back to 1, incase the character picked it
		 * up during fade out
		 */
		weapon.getIcon().setAlpha(1);
		/*
		 * Give the character the new weapon and a random assortment of
		 * magazines for it
		 */
		((Player) parent).getArm().changeWeapon(weapon);

		int randomMags = MathUtils.random(1, 3);
		for (int x = 1; x < randomMags; x++) {
			parent.getMagazines().add(weapon.getMagazine().clone());
		}
		
		pickedUp = true;
		
		pickup.play();

	}

	@Override
	public boolean fade(float delta) {
		weapon.getIcon().setAlpha(weapon.getIcon().getColor().a - 0.1f * delta);
		if (weapon.getIcon().getColor().a <= 0)
			return true;
		return false;
	}

	@Override
	public void dispose() {

	}

	/** @return {@link #weapon} */
	public Weapon getWeapon() {
		return weapon;
	}

}
