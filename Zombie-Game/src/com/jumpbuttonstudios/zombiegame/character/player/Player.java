package com.jumpbuttonstudios.zombiegame.character.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ai.state.player.IdlePlayerState;
import com.jumpbuttonstudios.zombiegame.ai.state.player.JumpingState;
import com.jumpbuttonstudios.zombiegame.character.Arm.ArmBuilder;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint.Pivots;
import com.jumpbuttonstudios.zombiegame.collision.CollisionFilters;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.weapons.Pistol;

/**
 * The protagonist
 * 
 * @author Gibbo
 * @author Jimmt
 * 
 */
public class Player extends Character {


	/** The position of the mouse cursor */
	Vector2 mouse = new Vector2();

	/** Whether the player is in defense menu or not */
	boolean inMenu;

	/**
	 * Creates a new player
	 * 
	 * @param world
	 */
	public Player(Level level, World world) {
		this.level = level;
		this.world = world;

		/* Create animations */
		/* Idle animation, we get the size of the sprites from this as well */
		Vector2 tmp = addAnimation(AnimationBuilder.createb2d(1, 1, 1,
				Constants.scale, Constants.scale, "still.png", null), "idle");
		/* Running animation */
		addAnimation(AnimationBuilder.createb2d(0.06f, 2, 6, Constants.scale,
				Constants.scale, "Sprites/Characters/Male/Run/WithoutArms.png",
				new int[] { 11 }), "running");
		/* Jump animation */
		addAnimation(AnimationBuilder.createb2d(1, 1, 2, Constants.scale,
				Constants.scale,
				"Sprites/Characters/Male/Jump/WithoutArms.png", null),
				"jumping");

		/* Setup the width and height from our animations sprites */
		width = tmp.x * Constants.scale;
		height = tmp.y * Constants.scale;

		/* Setup Box2D stuff */
		createBody(world, BodyType.DynamicBody, new Vector2(0, 2), true);

		createPolyFixture(width / 2, height / 2, 0.25f, 0.70f, 0.05f, false);
		body.setUserData(this);

		Filter filter = body.getFixtureList().get(0).getFilterData();
		filter.categoryBits = (short) CollisionFilters.PLAYER;
		filter.maskBits = (short) (CollisionFilters.GROUND | CollisionFilters.ZOMBIE | CollisionFilters.DROP);
		body.getFixtureList().get(0).setFilterData(filter);

		/* Character starts facing right */
		setFacing(Facing.RIGHT);
		/* Set the current animation */
		setCurrentAnimation("idle");

		/* Setup a bunch of pivot points for the player */
		Pivots.addPivotJoint("shoulder", this, -0.08f, 0.35f);
		Pivots.addPivotJoint("muzzle", this, -0.08f, 0.65f);
		Pivots.addPivotJoint("M1911", null, 0.6f, 0.05f);
		Pivots.addPivotJoint("AK74u", null, .9f, 0);
		Pivots.addPivotJoint("Dragunov", null, .9f, 0);

		/* Create an arm with a pistol as the weapon */
		// arm = ArmBuilder.create(this, Pivots.getPivotJoint("shoulder"),
		// Pivots.getPivotJoint("Dragunov"), new Dragunov(),
		// "Guns/Dragunov/WithArm.png", "Guns/Dragunov/Icon.png");
		arm = ArmBuilder.create(this, Pivots.getPivotJoint("shoulder"),
				Pivots.getPivotJoint("M1911"), new Pistol(),
				"Guns/M1911/WithArm.png", "Guns/M1911/Icon.png");
		// arm = ArmBuilder.create(this, Pivots.getPivotJoint("shoulder"),
		// Pivots.getPivotJoint("AK74u"), new AK74U(),
		// "Guns/AK74u/WithArm.png", "Guns/AK74u/Icon.png");

		/* Setup state machine */
		stateMachine.changeState(IdlePlayerState.instance());

		/* Setup character properties */
		maxSpeed = 7;
		acceleration = 10;
		jumpPower = 200;

	}

	public void setInMenu(boolean inMenu) {
		this.inMenu = inMenu;
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (!inMenu) {
			/* Check if the left mouse button was pressed */
			if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
				if (arm.getWeapon() != null) {
					/* Fire the weapon if it is not null */
					arm.getWeapon().fire(arm.getDirection());
				}

			}

			/* Check if space is pressed, if so we want to enter a jumping state */
			if (Gdx.input.isKeyPressed(Keys.SPACE) && isGrounded())
				getStateMachine().changeState(JumpingState.instance());
		}
		/* Update the arm */
		arm.update(delta);

	}

	@Override
	public void draw(SpriteBatch batch) {

		/*
		 * Check if we are in a running animation, if so we need to adjust the
		 * position of the sprite to stay within the bounding box
		 */
		if (currentAnimation.equals(getAnimation("running"))) {
			if (getBody().getLinearVelocity().x < 0) {
				currentAnimation.draw(batch, getBody().getPosition().x - 0.49f,
						getBody().getPosition().y, width, height,
						body.getAngle() * MathUtils.radDeg);
			} else {
				currentAnimation.draw(batch, getBody().getPosition().x - 0.39f,
						getBody().getPosition().y, width, height,
						body.getAngle() * MathUtils.radDeg);

			}

		} else {
			super.draw(batch);
		}

		/* Draw the arm on top of everything else */
		arm.draw(batch);

		/* Set the mouse position up */
		mouse.set(Gdx.input.getX(), Constants.HEIGHT - Gdx.input.getY());
		mouse.scl(Constants.scale);
		/* Rotate the mouse towards the arm */
		arm.rotateTowards(mouse);

	}


}
