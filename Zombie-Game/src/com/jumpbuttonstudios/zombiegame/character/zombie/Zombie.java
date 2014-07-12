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

package com.jumpbuttonstudios.zombiegame.character.zombie;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.collision.CollisionFilters;

/**
 * 
 * @author Stephen Gibson
 */
public abstract class Zombie extends Character {

	/** The target the zombie is heading towards */
	private Character target;

	/** If the target is in range or not */
	private boolean targetInRange = false;

	public Zombie(World world, Character target, float x, float y) {
		this.world = world;
		this.target = target;

	}

	@Override
	public void draw(SpriteBatch batch) {

		/*
		 * Check if we are in a walking animation, if so we need to adjust the
		 * position of the sprite to stay within the bounding box
		 */
		if(currentAnimation != null){
		if (currentAnimation.equals(getAnimation("walking"))) {
			if (getBody().getLinearVelocity().x < 0) {
				currentAnimation.draw(batch, getBody().getPosition().x,
						getBody().getPosition().y, width, height,
						body.getAngle() * MathUtils.radDeg);
			} else {
				currentAnimation.draw(batch, getBody().getPosition().x,
						getBody().getPosition().y, width, height,
						body.getAngle() * MathUtils.radDeg);

			}

		} else if (currentAnimation.equals(getAnimation("attacking"))) {
			if (getFacing() == Facing.RIGHT) {
				currentAnimation.draw(batch, getBody().getPosition().x - 0.12f,
						getBody().getPosition().y, width, height,
						body.getAngle() * MathUtils.radDeg);
				System.out.println("here");
			} else {
				currentAnimation.draw(batch, getBody().getPosition().x + 0.12f,
						getBody().getPosition().y, width, height,
						body.getAngle() * MathUtils.radDeg);

			}
		} else {
			super.draw(batch);
		}}

	}

	/**
	 * Sets up the collision filters for this object
	 */
	public void setCollisionFilters() {

		/* Tell box2d this is a zombie */
		fd.filter.groupIndex = -1;

	}

	/**
	 * Makes the zombie face the direction of the target, the player
	 */
	public void faceTarget() {
		if (target.getX() < getX()) {
			facing = Facing.LEFT;
			/* Check if we need to flip the animation to match the new direction */
			if (!currentAnimation.isFlipX())
				currentAnimation.flipFrames(true, false);
		} else {
			facing = Facing.RIGHT;
			/* Check if we need to flip the animation to match the new direction */
			if (currentAnimation.isFlipX())
				currentAnimation.flipFrames(true, false);
		}
	}

	/**
	 * 
	 * @return if the target is in range
	 */
	public boolean isTargetInRange() {
		return targetInRange;
	}

	/**
	 * Set if the target is still in range
	 * 
	 * @param targetInRange
	 */
	public void setTargetInRange(boolean targetInRange) {
		this.targetInRange = targetInRange;
	}

	public Character getTarget() {
		return target;
	}

}
