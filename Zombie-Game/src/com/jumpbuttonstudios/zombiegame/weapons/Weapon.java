package com.jumpbuttonstudios.zombiegame.weapons;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.Character.Facing;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.screens.LevelScreen;

/**
 * 
 * @author Gibbo
 * @author Jimmt
 * 
 */
public abstract class Weapon {

	/** The character that owns this weapon */
	protected Character owner;

	/***************************
	 *************************** 
	 ******** Graphics *********
	 *************************** 
	 **************************/

	/** The muzzle flash for all weapons */
	private AnimatedSprite muzzleFlash;

	/** The Sprite of the weapon */
	protected Sprite sprite;

	/** The end of the barrel or the muzzle */
	protected Vector2 muzzlePos = new Vector2();

	/***************************
	 *************************** 
	 ********* Stats ***********
	 *************************** 
	 **************************/

	/** How fast the guns rate of fire is */
	protected double rof;

	/** How fast the bullet moves */
	protected float muzzleVelocity;

	/** How far the bullet can travel before breaking up in the air */
	protected double flightTime;

	/** How many bullets the gun can hold */
	protected int clipSize;

	/** The recoil force */
	protected float recoil;

	/** The accuracy multiplier of this gun */
	protected float accuracyMultiplier;

	/***************************
	 *************************** 
	 ***** Firing Mechanics*****
	 *************************** 
	 **************************/

	/** The muzzle of the gun */
	protected Muzzle muzzle;

	/** The bullet this weapon uses to fire */
	protected Bullet bullet;

	/** If the weapon can fire or not */
	private boolean canFire = true;

	/** The last time the gun fired */
	private double lastShot;

	/** The direction the barrel is facing */
	private Vector2 direction;

	public Weapon(String path, Character owner) {
		this.owner = owner;
		sprite = new Sprite(new Texture(Gdx.files.internal(path)));
		sprite.setSize(sprite.getWidth() * Constants.scale, sprite.getHeight()
				* Constants.scale);

		muzzleFlash = AnimationBuilder.create(0.030f, 1, 3, Constants.scale,
				Constants.scale, "Effect/Gunfire.png", null);
		muzzleFlash.getAnimation().setPlayMode(Animation.NORMAL);
		muzzleFlash.setKeepSize(true);

	}

	/**
	 * Updates the weapon and keeps firing logic in check
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		if (TimeUtils.nanoTime() - lastShot > rof) {
			canFire = true;
			muzzleFlash.stop();
		}

	}

	public void draw(SpriteBatch batch) {
		if (muzzleFlash.isPlaying()) {
			muzzleFlash.setSize(1, 1);
			muzzleFlash.draw(batch);
			owner.getArms()
					.getFrontArm()
					.setRotation(
							owner.getFacing() == Facing.LEFT ? owner.getArms()
									.getFrontArm().getRotation() - 10 : owner
									.getArms().getFrontArm().getRotation() + 10);
		}
		if (muzzleFlash.isAnimationFinished()) {
			muzzleFlash.stop();
		}

	}

	/**
	 * Fire the weapon
	 */
	public void fire(Vector2 direction) {
		this.direction = direction;
		if (canFire) {
			Vector2 tmp = owner.getArms().getFrontAnchor();
			Sprite s = owner.getArms().getFrontArm();

			/* Every time we fire, we must update the muzzle's position relative to the pivot */
			muzzle.update(direction);

			/* Set the origin of the muzzle flash sprite in the middle */
			muzzleFlash.setOrigin(muzzleFlash.getWidth() / 2,
					muzzleFlash.getHeight() / 2);

			// making muzzle flash appear at the tip of the gun
			muzzleFlash
					.setPosition(
							(muzzle.getPivot().x - (muzzleFlash.getWidth() / 2) + (MathUtils
									.cosDeg(direction.angle()) * muzzle
									.getDistance())),
							(muzzle.getPivot().y
									- (muzzleFlash.getHeight() / 2) + (MathUtils
									.sinDeg(direction.angle()))
									* muzzle.getDistance()));
			muzzleFlash.setRotation(direction.angle());

			/* Create a bullet from the template */
			Bullet bullet = this.bullet.clone();
			/* We create the Box2D stuff with this method */
			bullet.create(direction);
			/* Add it to our array of bullets for updating and drawing */
			Level.bullets.add(bullet);
			/* Set the las shot as now, so the rof works */
			lastShot = TimeUtils.nanoTime();
			/* We can no longer fire, dug */
			canFire = false;
			/* Play the muzzle flash animation */
			muzzleFlash.play();
			
			/* Check if the player is below max speed, if so we can apply a little kickback from the weapon */
			if (Math.abs(owner.getBody().getLinearVelocity().x) <= owner
					.getMaxSpeed())
				owner.getBody().applyForceToCenter(
						owner.getFacing() == Facing.RIGHT ? -recoil : recoil,
						0, true);
			
			/* Shake the screen a little */
			LevelScreen.b2dCam.startShake(0.15f, 0.05f, 0.25f);
		}
	}

	public Muzzle getMuzzle() {
		return muzzle;
	}

	public AnimatedSprite getMuzzleFlash() {
		return muzzleFlash;
	}

	public float getAccuracyMultiplier() {
		return accuracyMultiplier;
	}

	public float getMuzzleVelocity() {
		return muzzleVelocity;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public Character getOwner() {
		return owner;
	}

}
