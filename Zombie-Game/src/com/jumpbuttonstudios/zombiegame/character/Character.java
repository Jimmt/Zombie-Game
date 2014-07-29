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

import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gibbo.gameutil.ai.state.StateMachine;
import com.gibbo.gameutil.box2d.Box2DObject;
import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.weapons.Weapon;

/**
 * A concrete character class, all in-game characters share similar properties
 * and are a subclass of this.
 * 
 * @author Stephen Gibson
 */
public class Character extends Box2DObject {

	/** The level instance */
	protected Level level;

	/** A reference to the box2d world */
	protected World world;

	/** The width of the character */
	protected float width;
	/** The height of the character */
	protected float height;

	/***************************
	 *************************** 
	 ******* Animation *********
	 *************************** 
	 **************************/

	/** State machine handles animation for the character */
	protected StateMachine stateMachine = new StateMachine(this);

	/** A collection of pre-made animations this character can use */
	protected HashMap<String, AnimatedBox2DSprite> animations = new HashMap<String, AnimatedBox2DSprite>();

	/** The current animation the character is using */
	protected AnimatedBox2DSprite currentAnimation;

	/** The current frame for this character */
	protected TextureRegion currentFrame;

	/***************************
	 *************************** 
	 ********** Stats **********
	 *************************** 
	 **************************/

	/** The velocity of this character */
	protected Vector2 velocity = new Vector2();

	/** The speed this character can move */
	protected float maxSpeed;

	/**
	 * The rate at this character increases its velocity before reaching max
	 * speed
	 */
	protected float acceleration;

	/** The jump power of the character */
	protected float jumpPower;	

	/** The character's health */
	protected float health;

	/***************************
	 *************************** 
	 ******* Properties ********
	 *************************** 
	 **************************/

	/**
	 * 
	 * @author Stephen Gibson
	 * 
	 */
	public enum Facing {
		LEFT, RIGHT;
	}

	/** Direction character is facing */
	protected Facing facing;

	/** If the character is on the ground */
	private boolean isGrounded = true;




	public void update(float delta) {
		/* Update the state machine */
		stateMachine.update(this);

	}

	public void draw(SpriteBatch batch) {
		/* If the character has an animaton, draw it's frames */
		if (currentAnimation != null) {
			currentAnimation.draw(batch, getBody());
		}
	}

	/**
	 * Sets the bodies linear dampening to zero and applies a force to the
	 * centre of the body on the y axis using the characters jump power, this
	 * will also set a {@link #isGrounded} to false to prevent air jumps
	 */
	public void jump() {
		body.setLinearDamping(0);
		body.applyForceToCenter(0, jumpPower, true);
		setGrounded(false);
	}
	

	/**
	 * TODO modify health by an amount (addition)
	 * @param amt
	 */
	public void modHealth(float amt) {
		if ((health + amt) > 0) {
			health += amt;
		} else {
			health = 0;
		}
	}

	/**
	 * Adds a new animation this character can use
	 * 
	 * @param animation
	 *            the animations to use, you can use the
	 *            {@link AnimationBuilder} to create it
	 * @param name
	 *            friendly type safe name to use the animation
	 * @return this method will return a Vector2 representing the width and
	 *         height of a single frame of the animation, this allows you to
	 *         have a width/height value for any other calculations, such as
	 *         creating Box2D fixture
	 */
	public Vector2 addAnimation(AnimatedBox2DSprite animation, String name) {
//		animation.setAdjustSize(false);
//		animation.setKeepSize(true);
//		animation.setSize(animation.getWidth() * Constants.scale, animation.getWidth() * Constants.scale);
		this.animations.put(name, animation);
		return new Vector2(animation.getAnimatedSprite().getWidth() * Constants.scale, animation
				.getAnimatedSprite().getHeight() * Constants.scale);
	}
	
	

	/**
	 * Swap the weapons in the characters weapon limbjoint
	 * 
	 * @param weapon
	 * @return
	 */
	public Weapon swapWeapon(Weapon weapon) {
		// TODO Add a way to access limb joint weapon, and simply swap them
		return weapon;
	}

	/**
	 * Sets the current animation the character should be using from a
	 * predefined set
	 * 
	 * @param name
	 */
	public void setCurrentAnimation(String name) {
		this.currentAnimation = this.animations.get(name);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getY() {
		return getBody().getPosition().y;
	}

	public float getX() {
		return getBody().getPosition().x;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public float getAcceleration() {
		return acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}
	

	public void setHealth(float health) {
		this.health = health;
	}

	public float getHealth() {
		return health;
	}

	public Facing getFacing() {
		return facing;
	}

	public void setFacing(Facing facing) {
		this.facing = facing;
	}



	/**
	 * 
	 * @return the current animation being used
	 */
	public AnimatedBox2DSprite getCurrentAnimation() {
		return currentAnimation;
	}

	/**
	 * Gets an animation from the pre-define list
	 * 
	 * @param name
	 * @return
	 */
	public AnimatedBox2DSprite getAnimation(String name) {
		return animations.get(name);
	}

	public StateMachine getStateMachine() {
		return stateMachine;
	}

	public boolean isGrounded() {
		return isGrounded;
	}

	public void setGrounded(boolean isGrounded) {
		this.isGrounded = isGrounded;
	}

	public Level getLevel() {
		return level;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
