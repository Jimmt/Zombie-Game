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
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.level.Level;

/**
 * 
 * @author Stephen Gibson
 */
public class DropSpawner {

	/** Level instance */
	Level level;

	/** The character that is trying to create a drop */
	Character character;

	/** Chance to drop ammo */
	float ammoChance = 45;
	/** Chance to drop an AK47 */
	float ak47uChance = 10f;
	/** Chance to drop a Dragunov */
	float dragunovChance = 5f;

	public DropSpawner(Level level) {
		this.level = level;
	}

	/**
	 * TODO comment this terrible method
	 */
	public void attemptSpawn() {

		float chance = MathUtils.random(0f, 100f);
		

		if (chance <= ammoChance && chance >= ak47uChance) {
			level.getDrops().add(
					new AmmoDrop(level, character.getBody().getPosition()));
		} else if (chance <= ak47uChance && chance >= dragunovChance) {
			level.getDrops().add(
					new AK74uDrop(level, character.getBody().getPosition()));
		} else if (chance <= dragunovChance) {
			level.getDrops().add(
					new DragunovDrop(level, character.getBody().getPosition()));

		}
	}

	/**
	 * Notifies the drop spawner when a drop should try to spawn
	 * 
	 * @param character
	 *            character executing this drop
	 */
	public void notify(Character character) {
		this.character = character;
		float chance = MathUtils.random(0, 100);

//		if (chance < 50)
			attemptSpawn();
	}

}
