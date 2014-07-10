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

/**
 * 
 * @author Gibbo
 * @author Jimmt
 * 
 */
public class Weapon {

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

	/** The accuracy multiplier of this gun */

	/***************************
	 *************************** 
	 ***** Firing Mechanics*****
	 *************************** 
	 **************************/

	/** The bullet this weapon uses to fire */
	protected Bullet bullet;

	/** If the weapon can fire or not */
	private boolean canFire = true;

	/** The last time the gun fired */
	private double lastShot;

	public Weapon(String path, Character owner) {
		this.owner = owner;
		sprite = new Sprite(new Texture(Gdx.files.internal(path)));
		sprite.setSize(sprite.getWidth() * Constants.scale, sprite.getHeight() * Constants.scale);

		muzzleFlash = AnimationBuilder.create(1, 1, 3, Constants.scale, Constants.scale,
				"Effect/Gunfire.png", null);
		muzzleFlash.getAnimation().setPlayMode(Animation.NORMAL);
		muzzleFlash.stop();
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
		} else if (muzzleFlash.isAnimationFinished()) {
		}
	}

	/**
	 * Fire the weapon
	 */
	public void fire(Vector2 direction) {	
		if (canFire) {
			Sprite s = owner.getArms().getFrontArm();
			if (owner.getFacing() == Facing.RIGHT) {
				muzzlePos.set((s.getX() + s.getOriginX()) + bullet.getSprite().getWidth() / 2 + (MathUtils.cosDeg(direction.angle()) * 1.5f),
						(s.getY() + s.getOriginY()) + bullet.getSprite().getHeight() / 2 + (MathUtils.sinDeg(direction.angle()) * 1.5f));
// muzzlePos.set(
// owner.getBody().getPosition().x
// + (MathUtils.cosDeg(direction.angle()) * 1.4f),
// owner.getBody().getPosition().y
// + (MathUtils.cosDeg(direction.angle()) * 0.35f));
			} else {
				muzzlePos.set((s.getX() + s.getOriginX()) + bullet.getSprite().getWidth() / 2 + (MathUtils.cosDeg(direction.angle()) * 1.5f),
						(s.getY() + s.getOriginY()) + bullet.getSprite().getHeight() / 2 + (MathUtils.sinDeg(direction.angle()) * 1.5f));
// muzzlePos.set(
// owner.getBody().getPosition().x
// + (MathUtils.cosDeg(direction.angle()) * 1.4f),
// owner.getBody().getPosition().y
// + -(MathUtils.cosDeg(direction.angle()) * 0.35f));

			}
			Bullet bullet = this.bullet.clone();
			bullet.create(direction);
			Level.bullets.add(bullet);
			lastShot = TimeUtils.nanoTime();
			canFire = false;
			muzzleFlash.play();
		}
	}

	public AnimatedSprite getMuzzleFlash() {
		return muzzleFlash;
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
