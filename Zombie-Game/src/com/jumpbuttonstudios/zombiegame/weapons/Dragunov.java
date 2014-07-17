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

import com.badlogic.gdx.graphics.Texture;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.zombiegame.ZombieGame;
import com.jumpbuttonstudios.zombiegame.asset.Assets;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint.Pivots;

/**
 * 
 * @author Stephen Gibson
 */
public class Dragunov extends Weapon {

	public Dragunov() {
		super(Pivots.getPivotJoint("shoulder"), Pivots
				.getPivotJoint("Dragunov"), ZombieGame.assets.get(Assets.GUN_DRAGUNOV_ICON, Texture.class),
				ZombieGame.assets.get(Assets.GUN_DRAGUNOV_ARM, Texture.class));

		weaponOrdinal = WeaponOrdinal.SECONDARY;

		magazine = new DragunovMagazine(this);
		muzzleVelocity = 120;
		rof = TimeConversion.secondToNanos(1.2f); // Tweak later
		reloadTime = TimeConversion.secondToNanos(1.8f);
		recoil = 180;
		accuracyMultiplier = 30;
		damage = 18;
		penetration = 3;

		shotPref[0] = 0.85f;
		shotPref[1] = 0.5f;

		bullet = new Bullet(ZombieGame.assets.get(Assets.GUN_DRAGUNOV_BULLET, Texture.class), this);
		muzzle = new Muzzle(this, Pivots.getPivotJoint("muzzle"), 0.1f, 0.75f,
				2.15f);
	}

	/**
	 * Specific magazine for the Dragunov
	 * 
	 * @author Gibbo
	 * 
	 */
	public class DragunovMagazine extends Magazine {

		public DragunovMagazine(Weapon parent) {
			super(parent, 10);
		}

	}

}
