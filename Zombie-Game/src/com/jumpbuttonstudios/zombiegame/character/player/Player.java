package com.jumpbuttonstudios.zombiegame.character.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ai.state.Idle;
import com.jumpbuttonstudios.zombiegame.ai.state.Jumping;
import com.jumpbuttonstudios.zombiegame.character.Character;

public class Player extends Character {

	/** The arm closest to the user */
	private LimbJoint frontArm;
	/** The arm furthers from the user */
	private LimbJoint backArm;

	public Player(World world) {
		this.world = world;

		Sprite sprite = new Sprite(new Texture(Gdx.files.internal("still.png")));
		width = sprite.getWidth() * Constants.scale;
		height = sprite.getHeight() * Constants.scale;

		/* Setup Box2D stuff */
		createBody(world, BodyType.DynamicBody, new Vector2(0, 2), true);
		createPolyFixture(width / 2, height / 2, 0.25f, 0.70f, 0.05f, false);

		/* Create animations */
		addAnimation(AnimationBuilder.create(1, 1, 1, width, height, true,
				"still.png"), "idle");
		addAnimation(AnimationBuilder.create(0.06f, 1, 11, width * 1.9f,
				height * 1.05f, false,
				"Sprites/Characters/Male/Run/WithArms2.png"), "running");
		addAnimation(AnimationBuilder.create(1, 1, 2, width * 2, height * 0.5f, false,
				"Sprites/Characters/Male/Jump/WithArms.png"), "jumping");

		setFacing(Facing.RIGHT);
		/* Set the current animation */
		setCurrentAnimation("idle");

		// TODO Implement this later
		/** Create limbs */

		backArm = new LimbJoint(this, 15 * Constants.scale,
				5 * Constants.scale,
				new Weapon("Guns/M1911/WithArm.png", 7, 2));

		/* Setup state machine */
		stateMachine.setDefaultState(Idle.instance());
		stateMachine.changeState(Idle.instance());

		/* Setup character properties */
		maxSpeed = 8;
		acceleration = 120;
		jumpPower = 200;

	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (Gdx.input.isKeyPressed(Keys.R))
			setGrounded(true);

	}

	@Override
	public void draw(SpriteBatch batch) {
		backArm.update(batch);
		
		if (Gdx.input.isKeyPressed(Keys.SPACE) && isGrounded())
			getStateMachine().changeState(Jumping.instance());
		
		if (currentAnimation.equals(getAnimation("running"))) {
			if (getBody().getLinearVelocity().x < 0) {
				currentAnimation.draw(batch, getBody().getPosition().x
						- (width / 2), getBody().getPosition().y, width,
						height, body.getAngle() * MathUtils.radDeg);
			} else {
				currentAnimation.draw(batch, getBody().getPosition().x
						- (width / 2) + 0.15f, getBody().getPosition().y,
						width, height, body.getAngle() * MathUtils.radDeg);

			}
			
		} else {
			super.draw(batch);

		}

	}


	public LimbJoint getBackArm() {
		return backArm;
	}

	public void setFrontArm(LimbJoint frontArm) {
		this.frontArm = frontArm;
	}

	public void setBackArm(LimbJoint backArm) {
		this.backArm = backArm;
	}

	/**
	 * Simple limb anchor class
	 * 
	 * @author JBS
	 * 
	 */
	public class LimbJoint {

		/** The parent of this limb */
		private Character parent;

		/** The position at which the limb is anchored */
		private Vector2 position = new Vector2();

		private float width, height;

		private Weapon weapon;

		/**
		 * Creates a new joint or "anchor" point for a limb to be placed
		 * 
		 * @param x
		 *            the x coordinate on the sprite this limb is anchored to
		 * @param y
		 *            the y coordinate on the sprite this limb is anchored to
		 * @param path
		 *            internal path to assets folder
		 */
		public LimbJoint(Character parent, float x, float y, Weapon weapon) {
			this.weapon = weapon;
			position.set(x, y);
			this.parent = parent;
			weapon.setPosition(parent.getX() + x, parent.getY() + y);
		}

		/**
		 * Update the limb and keep its position relative to the parent
		 * 
		 * @param delta
		 */
		public void update(Batch batch) {
			weapon.draw(batch);
			
			if(weapon.isFlipX()){
			weapon.setPosition(parent.getX() - position.x - weapon.getWidth(), parent.getY() + position.y);
			} else {
				weapon.setPosition(parent.getX() + position.x, parent.getY() + position.y);
			}
		}

		public void setWeapon(Weapon weapon) {
			this.weapon = weapon;
		}
		
		public Weapon getWeapon(){
			return weapon;
		}

		public Vector2 getPosition() {
			return position;
		}
	}

}
