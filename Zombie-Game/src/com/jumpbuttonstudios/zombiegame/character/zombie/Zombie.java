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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.asset.Assets;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.player.Player;
import com.jumpbuttonstudios.zombiegame.collision.CollisionFilters;
import com.jumpbuttonstudios.zombiegame.effects.Effect;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.weapons.Bullet;

/**
 * 
 * @author Stephen Gibson
 */
public abstract class Zombie extends Character {

	/** The target the zombie is heading towards */
	private Character target;

	/** If the target is in range or not */
	private boolean targetInRange = false;

	/** If the zombie currently has grabbed the player */
	private boolean grabbed = false;

	/** If this zombies health has reached zero */
	private boolean isDead;

	/** Blood splatter */
	Effect bloodSplatter = new Effect(AnimationBuilder.create(0.050f, 1, 5,
			Constants.scale, Constants.scale, Assets.EFFECT_BLOOD_SPRAY.fileName,
			null));

	/** The sound made when a zombie dies */
	static Sound deathSound = Gdx.audio.newSound(Gdx.files
			.internal("SFX/ZombieDeath.wav"));

	/** The offset of drawing on X */
	float drawOffsetX = 0.12f;
	/** The offset of drawing on Y */
	float drawOffsetY = 0.40f;

	/***************************
	 *************************** 
	 ********** Stats **********
	 *************************** 
	 **************************/

	/**
	 * How strong the zombie is, this determines the attack damage and how much
	 * the player is slowed down when the zombie grabs the player. A value of 1
	 * is normal, less than 1 is weaker and more than 1 is stronger
	 */
	private float strength;

	/**
	 * The zombies health before being killed, all zombies have a base health
	 * that is multiplied by other stats
	 */
	private float HP = 15;

	/** How much more damage a headshot will do to a zombie */
	final float HEADSHOT_MULTPLIER = 1.25f;

	public Zombie(Level level, World world, Character target, float speed,
			float x, float y) {
		this.level = level;
		this.maxSpeed = speed;
		this.world = world;
		this.target = target;

		strength = MathUtils.random(0.75f, 1.5f);
		HP *= strength;

	}

	@Override
	public void draw(SpriteBatch batch) {

		/*
		 * Check if we are in a walking animation, if so we need to adjust the
		 * position of the sprite to stay within the bounding box
		 */
		if (currentAnimation != null) {
			if (currentAnimation.equals(getAnimation("walking"))) {
				if (getBody().getLinearVelocity().x < 0) {
					currentAnimation.draw(batch, getBody().getPosition().x,
							getBody().getPosition().y + drawOffsetY, width,
							height, body.getAngle() * MathUtils.radDeg);
				} else {
					currentAnimation.draw(batch, getBody().getPosition().x,
							getBody().getPosition().y + 0.5f, width, height,
							body.getAngle() * MathUtils.radDeg);

				}

			} else if (currentAnimation.equals(getAnimation("attacking"))) {
				if (getFacing() == Facing.RIGHT) {
					currentAnimation.draw(batch, getBody().getPosition().x
							- drawOffsetX, getBody().getPosition().y + 0.5f,
							width, height, body.getAngle() * MathUtils.radDeg);
				} else {
					currentAnimation.draw(batch, getBody().getPosition().x
							+ drawOffsetX, getBody().getPosition().y
							+ drawOffsetY, width, height, body.getAngle()
							* MathUtils.radDeg);

				}
			} else {
				super.draw(batch);
			}
		}

	}

	/**
	 * Removes HP from the zombie and flags it dead if HP reaches zero, it also
	 * adds a blood splatter effect
	 * 
	 * @param bullet
	 */
	public void hurt(Bullet bullet, boolean headShot) {
		if (HP - bullet.getParent().getDamage()
				* (headShot == true ? HEADSHOT_MULTPLIER : 1) > 0) {
			if (headShot) {
				HP -= bullet.getParent().getDamage();
			} else {
				HP -= bullet.getParent().getDamage() * HEADSHOT_MULTPLIER;
			}
		} else {
			isDead = true;
			deathSound.play(0.75f, MathUtils.random(0.5f, 1.5f), bullet
					.getParent().getParentArm().getParentCharacter()
					.getFacing() == Facing.LEFT ? -0.5f : 0.5f);
			level.getDropSpawner().notify(this);
		}

		bloodSplatter = new Effect(bloodSplatter, getX()
				+ MathUtils.random(-getWidth() / 2, getWidth() / 2), getY()
				+ MathUtils.random(-getHeight() / 2, getHeight() / 2), bullet
				.getBody().getAngle() * MathUtils.radDeg);
		getLevel().getEffects().add(bloodSplatter);

	}

	/**
	 * Remove HP method for melee attacks
	 * 
	 * @param damage
	 * */
	public void hurt(float damage) {
		HP -= damage;
	}

	/**
	 * Sets up the collision filters for this object
	 */
	public void setCollisionFilters() {

		Filter filter = body.getFixtureList().get(0).getFilterData();
		filter.categoryBits = (short) CollisionFilters.ZOMBIE;
		filter.maskBits = (short) (CollisionFilters.PLAYER
				| CollisionFilters.BULLET | CollisionFilters.GROUND);
		for (Fixture fixture : body.getFixtureList()) {
			fixture.setFilterData(filter);
		}

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
	 * Causes the zombie to slow the players movement by simulating a grab, this
	 * will lower the players max speed
	 * 
	 * @param target
	 */
	public void grab(Player target) {
		/* Lower the players speed */
		if (target.getMaxSpeed() > 0) {
			target.setMaxSpeed(target.getMaxSpeed() - (2f * strength));
			grabbed = true;
			System.out.println(target.getMaxSpeed());
		}
	}

	/**
	 * Releases the zombies grip, this will return a portion of the players
	 * speed
	 * 
	 * @param target
	 */
	public void release(Player target) {
		/* Restore the players speed */
		target.setMaxSpeed(target.getMaxSpeed() + (2f * strength));
		System.out.println(target.getMaxSpeed());
		grabbed = false;
	}

	/** @return {@link #isDead} */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * 
	 * @return if the zombie has grabbed the player
	 */
	public boolean isGrabbed() {
		return grabbed;
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
