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
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudios.zombiegame.character.player.Player;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.weapons.Magazine;
import com.jumpbuttonstudios.zombiegame.weapons.Weapon;

/**
 * 
 * @author Stephen Gibson
 */
public class WeaponDrop extends Drop<Player> {

	/** The weapon in this drop */
	Weapon weapon;
	
	/** Some magazines for the weapon within the drop */
	Array<Magazine> magazines = new Array<Magazine>();

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
		
		/* Adds between 2 and 4 extra mags to the weapon in this drop */
		int count = MathUtils.random(2, 4);
		for(int mags = 0; mags < count; mags++)
			magazines.add(weapon.getMagazine().clone());
	}


	@Override
	public void pickup(Player parent) {
		/*
		 * Set the alpha for the icon back to 1, incase the character picked it
		 * up during fade out
		 */
		weapon.getIcon().setAlpha(1);
		/*
		 * Give the character the new weapon and a random assortment of
		 * magazines for it
		 */
		parent.setSecondaryWeapon(weapon);
		
		/* Add the magazines from this weapon drop to the character picking up the drop */
		parent.getSecondaryMagazines().addAll(magazines);

		/* Set this weapon drop as picked up */
		pickedUp = true;
		
		/* Play the pickup sound */
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
