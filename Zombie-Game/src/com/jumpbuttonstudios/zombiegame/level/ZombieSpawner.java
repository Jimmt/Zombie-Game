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

package com.jumpbuttonstudios.zombiegame.level;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.zombiegame.character.zombie.CrawlingZombie;
import com.jumpbuttonstudios.zombiegame.character.zombie.WalkingZombie;

/**
 * Spawn zombies at a given location at a set interval
 * 
 * @author Stephen Gibson
 */
public class ZombieSpawner {

	/** The level instance */
	Level level;

	/** The position of the spawner */
	Vector2 position = new Vector2();

	/** The amount of zombies that can spawn before it goes inactive */
	int zombiesLeft;

	/** The last time a zombie was spawned */
	double lastSpawn;

	/** How often the an zombie can spawn */
	double freq = TimeConversion.secondToNanos(2f);

	public ZombieSpawner(Level level, float x, float y) {
		this.level = level;
		position.set(x, y);
	}

	public void update() {
		/* Check if the spawner has any zombies left */
		if (zombiesLeft != 0) {
			/* Check if it is due to spawn */
			if (TimeUtils.nanoTime() - lastSpawn > freq) {
				spawn();
			}
		}
	}

	/** Spawn a zombie */
	public void spawn() {
		int type = MathUtils.random(1, 2);
		/* If the type is 1, we spawn a walking zombie */
		if (type == 1) {
			level.getCharacters().add(
					new WalkingZombie(level, level.getWorld(), level
							.getPlayer(), MathUtils.random(0.5f, 1.5f),
							position.x, position.y));
		} else {
			/* Else we just spawn a crawler */
			level.getCharacters().add(
					new CrawlingZombie(level, level.getWorld(), level
							.getPlayer(), MathUtils.random(0.75f, 1.2f),
							position.x, position.y));

		}
		/* Decrease the zombies left in spawner */
		zombiesLeft--;
		/* Set the time of last spawn to now */
		lastSpawn = TimeUtils.nanoTime();
		/* Give the next spawn time some randomness */
		freq = TimeConversion.secondToNanos(MathUtils.random(0.25f, 2f));
	}

	/** Reduces the frequency of zombie spawns, in seconds */
	public void reduceSpawnTime(double freq) {
		this.freq -= TimeConversion.secondToNanos(freq);
	}

	/** @return {@link #zombiesLeft} */
	public int getZombiesLeft() {
		return zombiesLeft;
	}

	/**
	 * Sets the maximum amount of zombies that can be spawned
	 * 
	 * @param maxZombies
	 */
	public void setZombiesLeft(int maxZombies) {
		this.zombiesLeft = maxZombies;
	}

}
