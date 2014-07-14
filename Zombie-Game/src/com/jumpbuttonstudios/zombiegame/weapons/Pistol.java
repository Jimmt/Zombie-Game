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

package com.jumpbuttonstudios.zombiegame.weapons;

import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint.Pivots;

/**
 * 
 * @author Stephen Gibson
 */
public class Pistol extends Weapon {

	public Pistol() {
		super();

		magazine = new Magazine(this, 7);
		muzzleVelocity = 40; // Tweak later and get proper/appropriate number
		rof = TimeConversion.secondToNanos(0.35f);
		reloadTime = TimeConversion.secondToNanos(1.25f);
		recoil = 15;
		accuracyMultiplier = 20;
		damage = 8;
		
		shotPref[0] = 0.5f;
		shotPref[1] = 1.35f;
				
		
		bullet = new Bullet("Guns/M1911/Bullet.png", this);
		muzzle = new Muzzle(this, Pivots.getPivotJoint("muzzle"), 0f, 0.65f, 1.6f);
		
		

	}

}
