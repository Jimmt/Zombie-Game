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
public class AK74U extends Weapon {

	public AK74U() {
		super(Pivots.getPivotJoint("shoulder"), Pivots.getPivotJoint("AK74u"), "Guns/AK74u/Icon.png", "Guns/AK74u/WithArm.png");
		
		magazine = new Magazine(this, 45);
		muzzleVelocity = 50;
		rof = TimeConversion.secondToNanos(0.092f);
		reloadTime = TimeConversion.secondToNanos(1.5f);
		recoil = 25;
		accuracyMultiplier = 30;
		damage = 10;
		
		shotPref[0] = 0.6f;
		shotPref[1] = 1f;
		
		bullet = new Bullet("Guns/AK74u/Bullet.png", this);
		muzzle = new Muzzle(this, Pivots.getPivotJoint("muzzle"), 0f, 0.90f, 2.15f);
		
	}


}


