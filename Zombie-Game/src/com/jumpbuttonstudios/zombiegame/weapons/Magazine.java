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

/**
 * 
 * @author Stephen Gibson
 */
public class Magazine {

	/** The parent weapon */
	private Weapon parent;
	
	/** The max capacity of this magazine */
	int maxCapacity;

	/** The capacity of the magazine */
	int capacity;

	public Magazine(Weapon parent, int capacity) {
		this.parent = parent;
		this.capacity = capacity;
		this.maxCapacity = capacity;
	}

	/**
	 * Shifts a bullet from the magazine into the chamber, for all your killing
	 * needs
	 * 
	 * @return returns false if the magazine failed to feed a bullet, no ammo
	 *         left
	 */
	public void feed() {
		capacity--;
	}

	/** @return {@link #capacity} */
	public int getCapacity() {
		return capacity;
	}

	/** @return {@link #parent} */
	public Weapon getParentWeapon() {
		return parent;
	}

	/** @return A new instance of this magazine */
	public Magazine clone() {
		return new Magazine(parent, maxCapacity);
	}

}
