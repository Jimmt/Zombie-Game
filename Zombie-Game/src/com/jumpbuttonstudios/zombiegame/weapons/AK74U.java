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
import com.jumpbuttonstudios.zombiegame.character.Character;

/**
 * 
 * @author Stephen Gibson
 */
public class AK74U extends Weapon {

	public AK74U(String path, Character owner) {
		super(path, owner);
		
		clipSize = 45;
		muzzleVelocity = 35; // Tweak later and get proper/appropriate number
		rof = TimeConversion.secondToNanos(0.075f); // Tweak later
		recoil = 25;
		accuracyMultiplier = 30;
		
		bullet = new Bullet("Guns/AK74u/Bullet.png", this);
		muzzle = new Muzzle(this, 0, .9f, 0f, 0, 2.15f);
		
	}


}


