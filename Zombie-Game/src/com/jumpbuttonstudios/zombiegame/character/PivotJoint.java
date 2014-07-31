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

package com.jumpbuttonstudios.zombiegame.character;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

/**
 * The pivot joint allows you to define a point on a characters body that will
 * be used as a rotational axis, such as a shoulder joint. This point is
 * relative to the centre of the box2d body of the character
 * 
 * TODO Find a way to set different types of parent, need a proper structure of
 * game objects
 * 
 * @author Stephen Gibson
 */
@SuppressWarnings("serial")
public class PivotJoint extends Vector2 {

	/** The character that is parent to this pivot */
	Character parent;

	/** The position relative to the parents box2d body */
	private Vector2 relativePos = new Vector2();

	/** If the pivot has been flipped on X */
	private boolean isFlippedX;
	/** If the pivot has been flipped on Y */
	private boolean isFlippedY;

	/**
	 * Create a new pivot joint for a character
	 * 
	 * @param parent
	 *            the character this pivot joint belongs to
	 * @param x
	 *            the position on the x axis from the centre of the box2d body
	 * @param y
	 *            the position on the y axis from the centre of the box2d body
	 */
	public PivotJoint(Character parent, float x, float y) {
		this.parent = parent;
		relativePos.set(x, y);
		set(x, y);

	}

	/**
	 * This should be called each frame in order to update the pivots world
	 * coordinates
	 */
	public void update() {
		set(parent.getX() + relativePos.x, parent.getY() + relativePos.y);
	}

	/**
	 * This will flip the pivot points, inverting them from positive to negative
	 * and vice versa
	 * 
	 * @param x
	 * @param y
	 */
	public void flipPos(boolean x, boolean y) {
		relativePos.set(x == true ? -relativePos.x : relativePos.x,
				y == true ? -relativePos.y : relativePos.y);
		if(x == true && !isFlippedX){
			isFlippedX = true;
			System.out.println("X Flipped");
		}else if(x == true && isFlippedX){
			isFlippedX = false;
			System.out.println("X Not Flipped");
		}
		if(y == true && !isFlippedY){
			isFlippedY = true;
			System.out.println("Y Flipped");
		}else if(y == true && isFlippedY){
			isFlippedY = false;
			System.out.println("Y Not Flipped");
		}
	}

	/**
	 * 
	 * @return the parent character
	 */
	public Character getParentCharacter() {
		return parent;
	}

	/**
	 * 
	 * @return the distance relative to the body centre
	 */
	public Vector2 getRelativePos() {
		return relativePos;
	}

	/** @return {@link #isFlippedX()} */
	public boolean isFlippedX() {
		return isFlippedX;
	}

	/** @return {@link #isFlippedY()} */
	public boolean isFlippedY() {
		return isFlippedY;
	}

	/**
	 * A place to store all defined pivots
	 * 
	 * @author Gibbo
	 * 
	 */
	public static class Pivots {

		/** All the pivot joints that can be used */
		static HashMap<String, PivotJoint> pivots = new HashMap<String, PivotJoint>();

		/**
		 * Adds a new pivot joint to the character, it is place relative to the
		 * centre of the box2d body
		 * 
		 * @param name
		 *            a friendly name used to fetch the pivot point
		 * @param x
		 * @param y
		 */
		public static void addPivotJoint(String name, Character parent,
				float x, float y) {
			pivots.put(name, new PivotJoint(parent, x, y));
		}

		/**
		 * 
		 * @return the characters collection of pivot joints
		 */
		public static HashMap<String, PivotJoint> getPivotJoints() {
			return pivots;
		}

		/**
		 * 
		 * @param name
		 *            the name given when added to the character
		 * @return the specified joint
		 */
		public static PivotJoint getPivotJoint(String name) {
			return pivots.get(name);
		}
	}

}
