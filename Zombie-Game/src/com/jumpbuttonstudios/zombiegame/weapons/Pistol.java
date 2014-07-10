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
public class Pistol extends Weapon {

	public Pistol(String path, Character owner) {
		super(path, owner);

		clipSize = 7;
		muzzleVelocity = 1; // Tweak later and get proper/appropriate number
		rof = TimeConversion.secondToNanos(0.25f); // Tweak later
		
		bullet = new Bullet("Guns/M1911/Bullet.png", this);
		
		

	}

}
