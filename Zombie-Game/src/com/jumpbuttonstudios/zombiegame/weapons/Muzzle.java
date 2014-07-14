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

import com.badlogic.gdx.math.Vector2;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint;

/**
 * The muzzle is the end of the gun barrel, it handles the flipping of the pivot
 * point so that bullets and a muzzle flash can be created at the correct
 * coordinates
 * 
 * @author Stephen Gibson
 */
public class Muzzle {

	/** The parent of this muzzle */
	Weapon parent;

	/** The pivot point the muzzle rotates around */
	private PivotJoint pivot;

	/** The offset in the X axis for rotation */
	float offsetX;

	/** The offset in the Y axis for rotation */
	float offsetY;

	/** X position on the body */
	float x;
	/** Y position on the body */
	float y;

	/** The distance the end of the barrel is from our pivot point */
	private float dst;

	public Muzzle(Weapon parent, PivotJoint pivot, float x, float y,
			float distance) {
		this.parent = parent;
		this.pivot = pivot;
		this.x = x;
		this.y = y;
		this.dst = distance;
	}

	public void update(Vector2 direction) {
		pivot.set(parent.getParentArm().getParentCharacter().getX() + x,
				parent.getParentArm().getParentCharacter().getY() + y);
		
		/*
		 * Check what side of the screen the angle is on by checking degree
		 * scope
		 */
		if (direction.angle() < 90 || direction.angle() > 270) {

			/*
			 * Check if parent was flipped, we want to flip it back, here we
			 * adjust the pivot so that the muzzle positions correctly, we do
			 * this here so it only executes once as a flip can only occur once
			 */
			if (parent.getSprite().isFlipY()) {
			}
		} else {
			/*
			 * Check if front arm was not flipped, we want to flip it, here we
			 * adjust the pivot so that the muzzle positions correctly, we do
			 * this here so it only executes once as a flip can only occur once
			 */
			if (!parent.getSprite().isFlipY()) {
			}
		}
	}

	/**
	 * 
	 * @return the Weapon this muzzle belongs to
	 */
	public Weapon getParentWeapon() {
		return parent;
	}

	public PivotJoint getPivot() {
		return pivot;
	}

	public float getDistance() {
		return dst;
	}

}
