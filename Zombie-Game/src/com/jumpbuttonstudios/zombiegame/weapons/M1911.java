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
public class M1911 extends Weapon {

	public M1911() {
		super(Pivots.getPivotJoint("shoulder"), Pivots.getPivotJoint("M1911"),
				ZombieGame.assets.get(Assets.GUN_M1911_ICON, Texture.class),
				ZombieGame.assets.get(Assets.GUN_M1911_ARM, Texture.class));

		weaponOrdinal = WeaponOrdinal.PRIMARY;
		weaponMode = WeaponMode.SEMI_AUTOMATIC;

		magazine = new M1911Magazine(this);
		muzzleVelocity = 40;
		rof = TimeConversion.secondToNanos(0.35f);
		rof = TimeConversion.secondToNanos(0.0035f);
		reloadTime = TimeConversion.secondToNanos(1.25f);
		recoil = 15;
		accuracyMultiplier = 20;
		damage = 8;

		shotPref[0] = 0.5f;
		shotPref[1] = 1.35f;

		bullet = new Bullet(ZombieGame.assets.get(Assets.GUN_M1911_BULLET, Texture.class), this);
		muzzle = new Muzzle(this, Pivots.getPivotJoint("muzzle"), 0f, 0.65f,
				1.6f);

	}

	/**
	 * Specific magazine for M1911
	 * 
	 * @author Gibbo
	 * 
	 */
	public class M1911Magazine extends Magazine {

		public M1911Magazine(Weapon parent) {
			super(parent, 7);
		}

	}

}
