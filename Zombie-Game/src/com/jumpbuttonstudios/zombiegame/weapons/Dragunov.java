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
public class Dragunov extends Weapon {

	public Dragunov() {
		
		clipSize = 10;
		muzzleVelocity = 120; // Tweak later and get proper/appropriate number
		rof = TimeConversion.secondToNanos(1f); // Tweak later
		recoil = 180;
		accuracyMultiplier = 30;
		
		bullet = new Bullet("Guns/Dragunov/Bullet.png", this);
		muzzle = new Muzzle(this, Pivots.getPivotJoint("muzzle"), 0.1f, 0.75f, 2.15f);
	}


}


