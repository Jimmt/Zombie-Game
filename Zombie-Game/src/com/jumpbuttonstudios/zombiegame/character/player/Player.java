package com.jumpbuttonstudios.zombiegame.character.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ai.state.player.IdlePlayerState;
import com.jumpbuttonstudios.zombiegame.ai.state.player.JumpingState;
import com.jumpbuttonstudios.zombiegame.ai.state.player.MeleeState;
import com.jumpbuttonstudios.zombiegame.asset.Assets;
import com.jumpbuttonstudios.zombiegame.character.Arm;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint.Pivots;
import com.jumpbuttonstudios.zombiegame.collision.CollisionFilters;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.weapons.AK74U;
import com.jumpbuttonstudios.zombiegame.weapons.M1911;
import com.jumpbuttonstudios.zombiegame.weapons.Magazine;
import com.jumpbuttonstudios.zombiegame.weapons.Weapon;

/**
 * The protagonist
 * 
 * @author Gibbo
 * @author Jimmt
 * 
 */
public class Player extends Character implements InputProcessor {

	/** The position of the mouse cursor */
	Vector2 mouse = new Vector2();

	/** Whether the player is in defense menu or not */
	boolean inMenu;

	/** The players primary weapon */
	private Weapon primaryWeapon;

	/** The players secondary weapon */
	private Weapon secondaryWeapon;

	/** All the character this player has for their primary weapon */
	private Array<Magazine> primaryMags = new Array<Magazine>();

	/** All the character this player has for their secondary weapon */
	private Array<Magazine> secondaryMags = new Array<Magazine>();

	/** The players front arm */
	private Arm arm = new Arm(this);

	/** Timer variables for preventing melee spam */
	private float lastMeleeTime = 999f, meleeTimeCap = 1.0f;

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
				Constants.scale, Constants.scale, Assets.PLAYER_IDLE.fileName,
				null), "idle");

		/* Running animation */
		addAnimation(AnimationBuilder.createb2d(0.06f, 2, 6, Constants.scale,
				Constants.scale, Assets.PLAYER_RUN_NOARMS.fileName,
				new int[] { 11 }), "running");

		/* Jump animation */
		addAnimation(AnimationBuilder.createb2d(1, 1, 2, Constants.scale,
				Constants.scale, Assets.PLAYER_JUMP_NOARMS.fileName, null),
				"jumping");

		/* Melee animation */
		addAnimation(AnimationBuilder.createb2d(0.1f, 1, 5, Constants.scale,
				Constants.scale, Assets.PLAYER_MELEE_HEAVY.fileName, null),
				"melee heavy");

		/* Setup the width and height from our animations sprites */
		width = tmp.x;
		height = tmp.y;

		/* Setup Box2D stuff */
		createBody(world, BodyType.DynamicBody, new Vector2(0, 2), true);

		createPolyFixture(width / 2, height / 2, 0.25f, 0.70f, 0.05f, false);
		body.setUserData(this);

		Filter filter = body.getFixtureList().get(0).getFilterData();
		filter.categoryBits = (short) CollisionFilters.PLAYER;
		filter.maskBits = (short) (CollisionFilters.GROUND
				| CollisionFilters.ZOMBIE | CollisionFilters.DROP);
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

		/* Setup primary weapon */
		primaryWeapon = new M1911();
		/* Set the current weapon as the primary */
		arm.changeWeapon(primaryWeapon);

		secondaryWeapon = new M1911();

		/* Give the player a bunch of magazines for primary weapon */
		for (int x = 0; x < 3; x++)
			getPrimaryMagazines().add(primaryWeapon.getMagazine().clone());

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
			lastMeleeTime += delta;

			/* Check if the left mouse button was pressed */
			if (Gdx.input.isButtonPressed(Buttons.LEFT)
					&& !getCurrentAnimation().equals(
							getAnimation("melee heavy"))) {
				if (arm.getWeapon() != null) {
					/* Fire the weapon if it is not null */
					arm.getWeapon().fire(arm.getDirection());
				}

			}

			/* Check if space is pressed, if so we want to enter a jumping state */
			if (Gdx.input.isKeyPressed(Keys.SPACE) && isGrounded())
				getStateMachine().changeState(JumpingState.instance());

			if (Gdx.input.isKeyPressed(Keys.F) && lastMeleeTime > meleeTimeCap) {
				getStateMachine().changeState(MeleeState.instance());
				lastMeleeTime = 0;

			}
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

	/**
	 * @param seondaryWeapon
	 *            the weapon to be set as secondary
	 */
	public void setSecondaryWeapon(Weapon secondaryWeapon) {
		this.secondaryMags.clear();
		this.secondaryWeapon = secondaryWeapon;
		if (!arm.getWeapon().equals(secondaryWeapon))
			arm.changeWeapon(secondaryWeapon);

	}

	/** @return {@link #primaryWeapon} */
	public Weapon getPrimaryWeapon() {
		return primaryWeapon;
	}

	/** @return {@link #secondaryWeapon} */
	public Weapon getSecondaryWeapon() {
		return secondaryWeapon;
	}

	/** @return {@link #secondaryMags} */
	public Array<Magazine> getSecondaryMagazines() {
		return secondaryMags;
	}

	/** @return {@link #primaryMags} */
	public Array<Magazine> getPrimaryMagazines() {
		return primaryMags;
	}

	public Arm getArm() {
		return arm;
	}

	@Override
	public boolean keyDown(int keycode) {
		/* Check for keypresses for weapon switching */
		switch (keycode) {
		case Keys.NUM_1:
			/* Change to primary if 1 is pressed */
			if (!getArm().getWeapon().equals(primaryWeapon))
				getArm().changeWeapon(primaryWeapon);
			break;
		case Keys.NUM_2:
			/*
			 * Change to secondary if 2 is pressed, only if a gun exists in
			 * there
			 */
			if (secondaryWeapon != null
					&& !getArm().getWeapon().equals(secondaryWeapon))
				getArm().changeWeapon(secondaryWeapon);

			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
