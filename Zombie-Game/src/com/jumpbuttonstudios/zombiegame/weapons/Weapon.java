package com.jumpbuttonstudios.zombiegame.weapons;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.character.Arm;
import com.jumpbuttonstudios.zombiegame.character.Character.Facing;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint;
import com.jumpbuttonstudios.zombiegame.screens.LevelScreen;

/**
 * 
 * @author Gibbo
 * @author Jimmt
 * 
 */
public abstract class Weapon {

	/** The arm that is holding this weapon */
	protected Arm parent;

	/***************************
	 *************************** 
	 ******** Graphics *********
	 *************************** 
	 **************************/

	/** The sprite for the weapon */
	private Sprite sprite;

	/** The muzzle flash for all weapons */
	private AnimatedSprite muzzleFlash;

	/***************************
	 *************************** 
	 ********* Sounds **********
	 *************************** 
	 **************************/

	/** The gunshot sound */
	Sound shot = Gdx.audio.newSound(Gdx.files.internal("SFX/Pistol.wav"));

	/** Preferences for the gunshot sound, Volume, Pitch and Pan */
	protected float[] shotPref = new float[3];

	// /** The empty magazine sound */
	Sound empty = Gdx.audio.newSound(Gdx.files.internal("SFX/Click2.wav"));

	/** The reload sound */
	Sound reload = Gdx.audio.newSound(Gdx.files.internal("SFX/Reload.wav"));

	/***************************
	 *************************** 
	 ********* Stats ***********
	 *************************** 
	 **************************/

	/** The guns damage */
	protected float damage;

	/** How fast the guns rate of fire is */
	protected double rof;

	/** How fast the bullet moves */
	protected float muzzleVelocity;

	/** How far the bullet can travel before breaking up in the air */
	protected double flightTime;

	/** The recoil force */
	protected float recoil;

	/** The accuracy multiplier of this gun */
	protected float accuracyMultiplier;

	/** How long it takes to reload the weapon */
	protected double reloadTime;

	/** How many penetrations a bullet is capable of when fired from this weapon */
	protected int penetration;

	/***************************
	 *************************** 
	 ** Rotation and position **
	 *************************** 
	 **************************/

	/** The origin of rotation and position */
	protected PivotJoint pivot;

	/***************************
	 *************************** 
	 ***** Firing Mechanics*****
	 *************************** 
	 **************************/

	/** The magazine for the weapon */
	protected Magazine magazine;

	/** The muzzle of the gun */
	protected Muzzle muzzle;

	/** The bullet this weapon uses to fire */
	protected Bullet bullet;

	/** If the weapon is reloading */
	private boolean reloading;

	/** If the weapon can fire or not */
	private boolean canFire = true;

	/** The last time the gun fired */
	private double lastShot;

	/** When a reload started */
	private double reloadStart;

	/** The direction the barrel is facing */
	Vector2 direction;

	public Weapon() {

		muzzleFlash = AnimationBuilder.create(0.050f, 1, 3, Constants.scale,
				Constants.scale, "Effect/Gunfire.png", null);
		muzzleFlash.getAnimation().setPlayMode(Animation.NORMAL);
		muzzleFlash.setKeepSize(true);

		/* All gunshots use the same pan */
		shotPref[2] = 0.5f;

	}

	public Magazine getMagazine() {
		return magazine;
	}

	/**
	 * Updates the weapon and keeps firing logic in check
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		/* Check if we are reloading */
		if (reloading) {
			/* See if the correct time has passed for a reload */
			if (TimeUtils.nanoTime() - reloadStart > reloadTime) {
				/** Play reloaded sound */
				reload.play(0.5f);
				reloading = false;
				/* Create an exact copy of the original magazine */
				magazine = magazine.clone();
			}
			/*
			 * If the time since the last shot is more than rof, we can shoot
			 * again
			 */
		} else if (!reloading) {
			if (TimeUtils.nanoTime() - lastShot > rof) {
				canFire = true;
				muzzleFlash.stop();
			}
		}

		if (!reloading && Gdx.input.isKeyPressed(Keys.R)) {
			reloading = true;
			reloadStart = TimeUtils.nanoTime();
		}

	}

	public void draw(SpriteBatch batch) {
		/* If the muzzle flash animation is playing, we want to draw it */
		if (muzzleFlash.isPlaying()) {
			muzzleFlash.draw(batch);
			getSprite()
					.setRotation(
							parent.getParentCharacter().getFacing() == Facing.LEFT ? getSprite()
									.getRotation() - 10 : getSprite()
									.getRotation() + 10);
		}
		/* Stop the muzzle animation if it has finished */
		if (muzzleFlash.isAnimationFinished()) {
			muzzleFlash.stop();
		}

	}

	/**
	 * Fire the weapon
	 */
	public void fire(Vector2 direction) {
		this.direction = direction;
		/*
		 * Check if we can fire, if we have bullets and that we are not
		 * reloading
		 */
		if (canFire && !reloading && magazine.getCapacity() > 0) {
			/* Set the parent to this */
			muzzle.parent = this;

			/*
			 * Every time we fire, we must update the muzzle's position relative
			 * to the pivot
			 */
			muzzle.update(direction);

			/* Set the origin of the muzzle flash sprite in the middle */
			muzzleFlash.setOrigin(muzzleFlash.getWidth() / 2,
					muzzleFlash.getHeight() / 2);

			// making muzzle flash appear at the tip of the gun
			muzzleFlash
					.setPosition(
							((muzzle.getPivot().x - (muzzleFlash.getWidth() / 2)) + (MathUtils
									.cosDeg(direction.angle()) * muzzle
									.getDistance())),
							((muzzle.getPivot().y - (muzzleFlash.getHeight() / 2)) + (MathUtils
									.sinDeg(direction.angle()))
									* muzzle.getDistance()));
			muzzleFlash.setRotation(direction.angle());

			/* Remove bullet from clip */
			magazine.feed();
			/* Create a bullet from the template */
			Bullet bullet = this.bullet.clone();
			/* We create the Box2D stuff with this method */
			bullet.create(direction);
			/* Add it to our array of bullets for updating and drawing */
			parent.getParentCharacter().getLevel().bullets.add(bullet);

			/* Set the las shot as now, so the rof works */
			lastShot = TimeUtils.nanoTime();

			/* We can no longer fire, dug */
			canFire = false;

			/* Play the muzzle flash animation */
			muzzleFlash.play();

			/* Make gunshot sound */
			shot.play(0.5f, 1.35f, getParentArm().getParentCharacter()
					.getFacing() == Facing.LEFT ? -0.5f : 0.5f);
			shot.play(
					shotPref[0],
					shotPref[1],
					getParentArm().getParentCharacter().getFacing() == Facing.LEFT ? -shotPref[2]
							: shotPref[2]);

			/*
			 * Check if the player is below max speed, if so we can apply a
			 * little kickback from the weapon
			 */
			if (Math.abs(parent.getParentCharacter().getBody()
					.getLinearVelocity().x) <= parent.getParentCharacter()
					.getMaxSpeed())
				parent.getParentCharacter()
						.getBody()
						.applyForceToCenter(
								parent.getParentCharacter().getFacing() == Facing.RIGHT ? -recoil
										: recoil, 0, true);

			/* Shake the screen a little */
			LevelScreen.b2dCam.startShake(0.25f, 0.05f, 0.25f);
			/*
			 * Check if the magazine is empty and that we are not already
			 * reloading
			 */
		} else if (magazine.getCapacity() == 0 && !reloading) {
			canFire = false;
			empty.play();
			reloading = true;
			reloadStart = TimeUtils.nanoTime();
		}
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Sprite getSprite() {
		return sprite;
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

	public void setParentArm(Arm parent) {
		this.parent = parent;
	}

	/** @return {@link #penetration} */
	public int getPenetration() {
		return penetration;
	}

	/** @return {@link #damage} */
	public float getDamage() {
		return damage;
	}

	/**
	 * 
	 * @return the Arm this Weapon belongs to
	 */
	public Arm getParentArm() {
		return parent;
	}

}
