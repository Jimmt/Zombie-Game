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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.character.Character.Facing;
import com.jumpbuttonstudios.zombiegame.character.player.Player;
import com.jumpbuttonstudios.zombiegame.screens.LevelScreen;
import com.jumpbuttonstudios.zombiegame.weapons.Weapon;
import com.jumpbuttonstudios.zombiegame.weapons.Weapon.WeaponOrdinal;

/**
 * 
 * @author Stephen Gibson
 */
public class Arm {

	/** The parent Player of this arm */
	private Player parent;

	/** The offset on the X axis, used to determine sprite origin from centre */
	float offsetX;

	/** The offset on the Y axis, used to determine sprite origin from centre */
	float offsetY;

	/** Direction arm is facing */
	Vector2 direction = new Vector2(), tmp = new Vector2();

	/** The weapon for this arm, if applicable */
	private Weapon weapon;

	/** World coordinates for facing the arm */
	Vector3 worldCoords = new Vector3();

	public Arm(Player parent) {
		this.parent = parent;
	}

	/**
	 * Updates the weapon and pivot point for the arm
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		if (weapon != null) {
			if (weapon.getWeaponOrdinal() == WeaponOrdinal.SECONDARY
					&& weapon.getMagazine().getCapacity() == 0
					&& parent.getSecondaryMagazines().size == 0)
				changeWeapon(parent.getPrimaryWeapon());
			weapon.update(delta);
		}
	}

	public void draw(SpriteBatch batch) {
		if (weapon != null) {
			/*
			 * This does not draw the weapon sprite, instead it draws the weapon
			 * effects such as muzzle flash
			 */

			weapon.draw(batch);
			/* Set the origin of the sprite to match the origin pivot */
			weapon.getSprite().setOrigin(
					weapon.getSprite().getWidth() / 2
							- weapon.getOriginJoint().x,
					weapon.getSprite().getHeight()
							/ 2
							- (weapon.getSprite().isFlipY() ? -weapon
									.getOriginJoint().y : weapon
									.getOriginJoint().y));
			/* Set the position o the sprite to the pivots */
			weapon.getSprite()
					.setPosition(
							weapon.getBodyJoint().x
									- (((weapon.getSprite().getWidth() / 2)) - weapon
											.getOriginJoint().x),
							weapon.getBodyJoint().y
									- (weapon.getSprite().getHeight() / 2 - (weapon
											.getSprite().isFlipY() ? -0.05f
											: 0.05f)));
			/*
			 * This draws the entire arm, which may or may not have a weapon in
			 * it
			 */
			weapon.getSprite().draw(batch);
		}
	}

	public void rotateTowards(Vector2 vec) {

		// Set world coordinates and then project to screen coordinates for
		// direction calculations
		if (weapon.getMuzzle() != null) {
			worldCoords.set(weapon.getMuzzle().getPivot().x, weapon.getMuzzle()
					.getPivot().y, 0);
			LevelScreen.b2dCam.project(worldCoords);
			worldCoords.scl(Constants.scale);
			tmp.set(worldCoords.x, worldCoords.y);
		}

		direction = vec.sub(tmp).nor();

		/*
		 * Check what side of the screen the angle is on by checking degree
		 * scope
		 */
		if (direction.angle() < 90 || direction.angle() > 270) {

			/*
			 * Set the origin of the sprite to rotate around the middle + an
			 * offset in X or Y
			 */
			weapon.getSprite().setOrigin(
					(weapon.getSprite().getWidth() / 2) - offsetX,
					(weapon.getSprite().getHeight() / 2) - offsetY);
			/*
			 * Set the rotation of the sprite to the angle from the directional
			 * vector
			 */
			weapon.getSprite().setRotation(direction.angle());

			/*
			 * Check if the characters animation is currently flipped, if so we
			 * flip it back as we are facing right
			 */
			if (parent.currentAnimation.isFlipX()) {
				parent.currentAnimation.flipFrames(true, false);
				parent.setFacing(Facing.RIGHT);
			}
			/*
			 * Check if front arm was flipped, we want to flip it back, here we
			 * adjust the anchor point that the sprite sits on, we do this here
			 * so it only executes once as a flip can only occur once
			 */
			if (weapon.getSprite().isFlipY()) {
				weapon.getSprite().flip(false, true);
				if (weapon.getBodyJoint().isFlippedX())
					weapon.getBodyJoint().flipPos(true, false);
				if (!weapon.getOriginJoint().isFlippedY())
					weapon.getOriginJoint().flipPos(false, true);
				weapon.getMuzzle().getPivot().flipPos(true, false);
			}
		} else {
			/*
			 * If we are facing left this block is called, it is almost
			 * identical to the above block
			 */
			weapon.getSprite().setOrigin(
					(weapon.getSprite().getWidth() / 2) - offsetX,
					(weapon.getSprite().getHeight() / 2) - offsetY);
			weapon.getSprite().setRotation(direction.angle());

			/*
			 * Check if the characters animation is not flipped, if so we flip
			 * it so we are facing left
			 */
			if (!parent.currentAnimation.isFlipX()) {
				parent.currentAnimation.flipFrames(true, false);
				parent.setFacing(Facing.LEFT);

			}
			/*
			 * Check if front arm was not flipped, we want to flip it, here we
			 * adjust the anchor point that the sprite sits on, we do this here
			 * so it only executes once as a flip can only occur once
			 */
			if (!weapon.getSprite().isFlipY()) {
				weapon.getSprite().flip(false, true);
				if (!weapon.getBodyJoint().isFlippedX())
					weapon.getBodyJoint().flipPos(true, false);
				if (!weapon.getOriginJoint().isFlippedY())
					weapon.getOriginJoint().flipPos(false, true);
				weapon.getMuzzle().getPivot().flipPos(true, false);
			}
		}

	}

	/**
	 * 
	 * @return the character this arm belongs to
	 */
	public Player getParentCharacter() {
		return parent;
	}

	/**
	 * 
	 * @return the direction the arms are facing
	 */
	public Vector2 getDirection() {
		return direction;
	}

	/**
	 * Changes to a new weapon
	 * 
	 * @param weapon
	 */
	public void changeWeapon(Weapon weapon) {
		this.weapon = weapon;
		this.weapon.setParentArm(this);
	}

	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * For all your arm building needs
	 * 
	 * @author Gibbo
	 * 
	 */
	public static class ArmBuilder {

		/**
		 * Create a new arm
		 * 
		 * @param parent
		 *            the character that has this arm
		 * @param bodyPivot
		 *            the pivot point on the body where the arm will rotate
		 *            around
		 * @param originPivot
		 *            the origin local to sprite coordinates
		 * @param weapon
		 *            a weapon for the arm, may be null and passed later or not
		 *            at all
		 * @param path
		 *            the path to the arm sprite
		 * @return
		 */
		public static Arm create(Player parent, PivotJoint bodyPivot,
				PivotJoint originPivot, Weapon weapon, String path,
				String iconPath) {
			Arm arm = new Arm(parent);
			arm.parent = parent;
			arm.weapon = weapon;
			arm.weapon.setBodyJoint(bodyPivot);
			arm.weapon.setOriginJoint(originPivot);
			weapon.setSprite(new Sprite(new Texture(Gdx.files.internal(path))));
			weapon.getSprite().setSize(
					weapon.getSprite().getWidth() * Constants.scale,
					weapon.getSprite().getHeight() * Constants.scale);
			weapon.setParentArm(arm);

			return arm;
		}

	}

}
