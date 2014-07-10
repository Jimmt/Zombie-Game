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

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.character.Character.Facing;
import com.jumpbuttonstudios.zombiegame.weapons.Weapon;

/**
 * TODO Implement badass rotating arms
 * 
 * @author Stephen Gibson
 */
public class Arms {

	/** The parent owner of this set of Arms */
	Character parent;

	/** The anchor point on the parent for the back arm */
	Vector2 backAnchor = new Vector2();

	/** The anchor point on the parent for the front arm */
	Vector2 frontAnchor = new Vector2();

	/** The sprite for the back arm */
	Sprite backArm;

	/** The sprite for the front arm */
	Sprite frontArm;

	/** The weapon that belongs to this set of Arms */
	Weapon weapon;

	/** Direction arm is facing */
	Vector2 direction, tmp;

	public Arms(Character parent) {
		this.parent = parent;
		tmp = new Vector2();

	}
	
	public Vector2 getDirection(){
		return direction;
	}
	
	public Sprite getFrontArm(){
		return frontArm;
	}
	
	public Weapon getWeapon(){
		return weapon;
	}

	public void rotateTowards(Vector2 vec) {

		tmp.set(4.8f, 1.9f);

		direction = vec.sub(tmp).nor();

		if (direction.angle() < 90 || direction.angle() > 270) {
			frontArm.setOrigin(.1875f, 0.3f);
			frontArm.setRotation(direction.angle());

			if(parent.currentAnimation.isFlipX()){
				parent.currentAnimation.flipFrames(true, false);
				parent.setFacing(Facing.RIGHT);
			}
			if(frontArm.isFlipY()){
				frontArm.flip(false, true);
			}
		} else {
			frontArm.setOrigin(0.35f, 0.3f);
			frontArm.setRotation(direction.angle());
			if(!parent.currentAnimation.isFlipX()){
				parent.currentAnimation.flipFrames(true, false);
				parent.setFacing(Facing.LEFT);

			}
			if(!frontArm.isFlipY()){
				frontArm.flip(false, true);
			}
		}

	}

	/**
	 * Updating the arms will automatically update the weapon held by the
	 * character
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		if(weapon != null)
			weapon.update(delta);
	}

	/**
	 * Draws the front arm
	 * 
	 * @param batch
	 */
	public void drawFront(SpriteBatch batch) {

		/* Check if the weapon is null, if the weapon is null */
		if (weapon != null) {
			Sprite weapon = this.weapon.getSprite();
			if (weapon.isFlipX()) {
				weapon.setPosition(parent.getX() - frontAnchor.x - weapon.getWidth(), parent.getY()
						+ frontAnchor.y);
			} else {
				weapon.setPosition(parent.getX() + frontAnchor.x, parent.getY() + frontAnchor.y);
			}
			weapon.draw(batch);
		} else {
			frontArm.setSize(frontArm.getWidth() * Constants.scale, frontArm.getHeight()
					* Constants.scale);
			frontArm.setPosition(parent.getX() + frontAnchor.x, parent.getY() + frontAnchor.y);
			frontArm.draw(batch);
		}
		

	}

	/**
	 * Draws the back arm
	 * 
	 * @param batch
	 */
	public void drawBack(SpriteBatch batch) {

		backArm.setSize(backArm.getWidth() * Constants.scale, backArm.getHeight() * Constants.scale);
		backArm.setPosition(parent.getX() + backAnchor.x, parent.getY() + backAnchor.y);
		backArm.draw(batch);

	}

	/**
	 * Flips the sprites of the arms
	 */
	public void flip() {
		if (weapon != null) {
			weapon.getSprite().flip(true, false);
			backArm.flip(true, false);
		} else {
			frontArm.flip(true, false);
			backArm.flip(true, false);
		}
	}

	/**
	 * 
	 * @return if both arm sprites are flipped
	 */
	public boolean isFlippedX() {
		return frontArm.isFlipX() && backArm.isFlipX();
	}

	private void attachWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	private void setBackAnchor(float x, float y) {
		this.backAnchor.set(x, y);
	}

	public Vector2 getBackAnchor() {
		return backAnchor;
	}

	private void setFrontAnchor(float x, float y) {
		this.frontAnchor.set(x, y);
	}

	public Vector2 getFrontAnchor() {
		return frontAnchor;
	}

	private void setArmSprites(Sprite frontArm, Sprite backArm) {
		this.frontArm = frontArm;
		this.backArm = backArm;
	}

	/**
	 * Returns an exact copy of this arm set
	 */
	public Arms clone() {
		Arms arms = new Arms(parent);
		arms.setFrontAnchor(parent.getX() + frontAnchor.x, parent.getX() + frontAnchor.y);
		arms.setBackAnchor(parent.getX() + backAnchor.x, parent.getX() + backAnchor.y);
		arms.setArmSprites(frontArm, backArm);

		return arms;
	}

	/**
	 * Builds arm sets for characters
	 * 
	 * @author Gibbo
	 * 
	 */
	public static class ArmsBuilder {

		/**
		 * This will build a "weaponless" arm set, it basically draws plain arms
		 * 
		 * @param parent
		 *            the parent character
		 * @param frontAnchorX
		 *            the anchor point for the front arm on the x axis
		 * @param frontAnchorY
		 *            the anchor point for the front arm on the y axis
		 * @param frontArm
		 *            the sprite for the front arm
		 * @param backAnchorX
		 *            the anchor point for the back arm on the x axis
		 * @param backAnchorY
		 *            the anchor point for the back arm on the x axis
		 * @param backArm
		 *            the sprite for the back arm
		 * @return a set of arms for a character without a weapon
		 */
		public static Arms create(Character parent, float frontAnchorX, float frontAnchorY,
				Sprite frontArm, float backAnchorX, float backAnchorY, Sprite backArm) {
			Arms arms = new Arms(parent);
			arms.setFrontAnchor(parent.getX() + frontAnchorX, parent.getX() + frontAnchorY);
			arms.setBackAnchor(parent.getX() + backAnchorX, parent.getX() + backAnchorY);
			arms.setArmSprites(frontArm, backArm);
			return arms;
		}

		/**
		 * This will build am arm set with a weapon in the front arm
		 * 
		 * @param parent
		 *            the parent character
		 * @param frontAnchorX
		 *            the anchor point for the front arm on the x axis
		 * @param frontAnchorY
		 *            the anchor point for the front arm on the y axis
		 * @param frontWeapon
		 *            the weapon for the front arm
		 * @param backAnchorX
		 *            the anchor point for the back arm on the x axis
		 * @param backAnchorY
		 *            the anchor point for the back arm on the x axis
		 * @param backArm
		 *            the sprite for the back arm
		 * @return a set of arms for a character with a weapon in the the front
		 *         arm
		 */
		public static Arms create(Character parent, float frontAnchorX, float frontAnchorY,
				Weapon frontWeapon, float backAnchorX, float backAnchorY, Sprite backArm) {
			Arms arms = new Arms(parent);
			arms.setFrontAnchor(parent.getX() + frontAnchorX, parent.getX() + frontAnchorY);
			arms.setBackAnchor(parent.getX() + backAnchorX, parent.getX() + backAnchorY);
			arms.setArmSprites(frontWeapon.getSprite(), backArm);
			arms.attachWeapon(frontWeapon);
			return arms;
		}

	}

}
