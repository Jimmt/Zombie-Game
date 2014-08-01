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

package com.jumpbuttonstudios.zombiegame.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.gibbo.gameutil.box2d.Box2DObject;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.collision.CollisionFilters;

/**
 * 
 * @author Stephen Gibson
 */
public class Bullet extends Box2DObject {

	/** The weapon that fired this bullet */
	Weapon parent;

	/** Width of he bullet */
	final float width = 0.425f;

	/** Height of the bullet */
	final float height = 0.275f;
	
	/***************************
	 *************************** 
	 ********* Stats ***********
	 *************************** 
	 **************************/
	
	/** How many targets this bullet has penetrated */
	private int penetrated;

	/**
	 * The spread of a the bullet, every bullet suffers the same spread and is
	 * adjusted with the guns accuracy
	 */
	final float BULLET_SPREAD = 8;

	/** The bullets sprite */
	private Sprite sprite;

	public Bullet(Texture sprite, Weapon weapon) {
		this.parent = weapon;
		this.sprite = new Sprite(sprite);
		this.sprite.setSize(this.sprite.getWidth() * Constants.scale, this.sprite.getHeight()
				* Constants.scale);

	}
	
	private Bullet(){
	}

	public void create(Vector2 direction) {

		createBody(parent.getParentArm().getParentCharacter().getLevel().getWorld(), BodyType.DynamicBody, Vector2.Zero, false);
		createPolyFixture(sprite.getWidth() / 2, sprite.getHeight() / 2,
				0.15f, 0, 0, false);
		getBody().setGravityScale(0.0f);
		getBody().setBullet(true);

		Filter filter = fixture.getFilterData();
		filter.categoryBits = (short) CollisionFilters.BULLET
				;
		filter.maskBits = (short) (CollisionFilters.GROUND
				 | CollisionFilters.ZOMBIE | CollisionFilters.BOUNDARY
				);
		getBody().getFixtureList().get(0).setFilterData(filter);

		// set bullet to extended arm position
		getBody()
				.setTransform(
						parent.muzzle.getPivot().x
								+ (MathUtils.cosDeg(direction.angle()) * parent.muzzle
										.getDistance()),
						parent.muzzle.getPivot().y
								+ (MathUtils.sinDeg(direction.angle()) * parent.muzzle
										.getDistance()),
						parent.getParentArm().getDirection().angle()
								* MathUtils.degreesToRadians);

		getBody()
				.setLinearVelocity(
						parent.getMuzzleVelocity()
								* (MathUtils.cosDeg(direction.angle()
										+ MathUtils.random(
												-BULLET_SPREAD
														* parent.getAccuracyMultiplier()
														* MathUtils.degRad,
												BULLET_SPREAD
														* parent.getAccuracyMultiplier()
														* MathUtils.degRad))),
						parent.getMuzzleVelocity()
								* (MathUtils.sinDeg(direction.angle()
										+ MathUtils.random(
												-BULLET_SPREAD
														* parent.getAccuracyMultiplier()
														* MathUtils.degRad,
												BULLET_SPREAD
														* parent.getAccuracyMultiplier()
														* MathUtils.degRad))));
		body.setUserData(this);

		// getBody().setLinearVelocity(
		// direction.nor().scl(weapon.getMuzzleVelocity()));

	}

	public void update() {
		// TODO maybe add some fancy bullet mechanics, so we will need to update
		// them

	}

	public void draw(SpriteBatch batch) {
		if (sprite != null) {
			sprite.setOrigin(width / 2, height / 2);
			sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
					body.getPosition().y - sprite.getHeight() / 2);
			sprite.setRotation(body.getAngle() * MathUtils.radDeg);
			sprite.draw(batch);
		}
	}
	

	public Bullet clone() {
		Bullet bullet = new Bullet();
		bullet.sprite = sprite;
		bullet.parent = parent;
		return bullet;

	}
	
	/**@return {@link #penetrated} */
	public int getPenetrated() {
		return penetrated;
	}
	
	public void setPenetrated(int penetrated) {
		this.penetrated = penetrated;
	}
	
	/**@return {@link #parent} */
	public Weapon getParent() {
		return parent;
	}

	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void dispose() {

	}

}
