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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;

/**
 * Controls waves of zombies using defined settings, such as spawn rate, max
 * zombies, max spawners and what not
 * 
 * @author Stephen Gibson
 */
public class WaveGenerator {

	/** Logger for wave generator */
	private Logger logger = new Logger("Wave Generator");

	/** For testing , displays debug info */
	public static boolean DEBUG_MODE = true;

	/** For testing, turns spawning on and off */
	public static boolean SPAWNING_ACTIVE = true;

	/** Level instance */
	Level level;

	/** The current wave number */
	private int wave = 0;

	/** The current wave */
	Wave prevWave;

	/** The previous wave */
	Wave currWave;

	/** When the last wave finished */
	double finishTime;

	/** How long between waves */
	double freq = TimeConversion.secondToNanos(1);

	public WaveGenerator(Level level) {
		this.level = level;

		/* Set the logger up */
		logger.setLevel(DEBUG_MODE == true ? Logger.DEBUG : Logger.NONE);

		nextWave();
	}

	/**
	 * Updates the wave generator and keeps timers in check, called each
	 * iteration
	 */
	public void update() {
		/* TODO Move to a better place */
		if (Gdx.input.isKeyPressed(Keys.Q) && level.getDefensePlacing()) {
			level.exitDefensePlacing();
		}
		if (SPAWNING_ACTIVE)
			/* Check if the wave has finished */
			if (!waveFinished()) {
				/* Update the current wave */
				currWave.update();
			} else {
				/* Check if the rest period is over */
				if (TimeUtils.nanoTime() - finishTime > freq) {
					
					/* Enter defense placement state */
					level.enterDefensePlacing();

				}
			}
	}

	/**
	 * Proceeds to the next wave
	 */
	public void nextWave() {
		wave++;
		prevWave = currWave;
		currWave = new Wave(this, prevWave);
		currWave.distributeZombies();
	}

	/**
	 * Checks if the array of characters only has 1 object left in it, the
	 * player. If so, wave has ended.
	 * 
	 * @return if the wave is finished or not
	 */
	public boolean waveFinished() {
		if (currWave.spawners[0].getZombiesLeft() == 0
				&& currWave.spawners[1].getZombiesLeft() == 0 && level.getCharacters().size == 1) {
			if (!currWave.isFinished)
				finishTime = currWave.getFinishedTime();
			currWave.isFinished = true;
			return true;
		}
		return false;
	}

	/** @return {@link #level} */
	public Level getLevel() {
		return level;
	}

	/** @return {@link #wave} */
	public int getWave() {
		return wave;
	}

	/** @return {@link #logger} */
	public Logger getLogger() {
		return logger;
	}

}
