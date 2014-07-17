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
import com.badlogic.gdx.utils.TimeUtils;

/**
 * A wave that is commencing
 * 
 * @author Stephen Gibson
 */
public class Wave {

	/** Wave generator */
	WaveGenerator waveGenerator;
	
	/** If the wave is finished */
	boolean isFinished;

	/** The previous wave */
	Wave prevWave;

	/** The amount of zombies in this wave */
	private int zombieCount = 10;

	/** How much the wave scales compared to the last */
	private int scale = 1;

	/** Each zombie spawner at either side of the level */
	ZombieSpawner[] spawners = new ZombieSpawner[2];

	/**
	 * Create a new wave of zombies
	 * 
	 * @param prevWave
	 *            the wave before this one
	 */
	public Wave(WaveGenerator waveGenerator, Wave prevWave) {
		this.waveGenerator = waveGenerator;
		this.prevWave = prevWave;		

		/* Create a zombie spawner at each side of the screen */
		spawners[0] = new ZombieSpawner(waveGenerator.getLevel(), -15, 2);
		spawners[1] = new ZombieSpawner(waveGenerator.getLevel(), 28, 2);

		/* If we are on the first wave, there is no previous wave so forget this code */
		if (waveGenerator.getWave() != 1) {
			/*
			 * We increase the zombie count between 1 and 3 multiplied by scale,
			 * or the last level
			 */
			this.zombieCount = this.prevWave.getZombieCount()
					+ (MathUtils.random(2, 5) * this.prevWave.getScale());
			/*
			 * Check if the wave level is a multiple of 3, if so lets scale the
			 * wave
			 */
			this.scale = this.prevWave.getScale()
					+ (waveGenerator.getWave() % 3 == 0 ? 1 : 0);

		}
		
		/* Output debug info */
		waveGenerator.getLogger().debug("Wave " + waveGenerator.getWave() + " - ");
		waveGenerator.getLogger().debug("Total Zombies = " + zombieCount);
		

	}

	public void update() {
		/* Update the spawners */
		for (ZombieSpawner spawner : spawners) {
			spawner.update();
		}

	}

	/**
	 * Distributes the zombies randomly between the 2 spawners
	 */
	public void distributeZombies() {
		int chance;
		for (int i = 0; i < zombieCount; i++) {
			/* Generate 1 or 0 */
			chance = MathUtils.random(0, 1);
			/*
			 * Grab that index of zombie spawners, lets add one onto that
			 * spawners max zombie count
			 */
			spawners[chance]
					.setZombiesLeft(spawners[chance].getZombiesLeft() + 1);
		}
		
		/* Output debug info about zombie distribution */
		waveGenerator.getLogger().debug("Left Spawner = " + spawners[0].getZombiesLeft());
		waveGenerator.getLogger().debug("Right Spawner = " + spawners[1].getZombiesLeft());
	}

	/** @return the current time in nanoseconds */
	public double getFinishedTime() {
		return TimeUtils.nanoTime();
	}

	/** @return {@link #zombieCount} */
	public int getZombieCount() {
		return zombieCount;
	}

	/** @return {@link #scale} */
	public int getScale() {
		return scale;
	}
}
