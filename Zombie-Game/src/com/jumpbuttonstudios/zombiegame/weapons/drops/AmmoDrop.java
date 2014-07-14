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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.level.Level;

/**
 * 
 * @author Stephen Gibson
 */
public class AmmoDrop extends Drop {


	/** How many magazines this drop has */
	int magazines;

	public AmmoDrop(Level level, Vector2 position) {
		super(level, position, new Sprite(new Texture(
				Gdx.files.internal("Environment/Ammo.png"))));

		/* Randomly select a number of magazines */
		magazines = MathUtils.random(1, 5);

	}


	@Override
	public void pickup(Character parent) {
		/* Give the character x amount of magazines */
		for (int x = 1; x < magazines; x++) {
			parent.getMagazines().add(
					parent.getArm().getWeapon().getMagazine().clone());
		}
		
		pickedUp = true;
	}


	@Override
	public void dispose() {

	}

}
