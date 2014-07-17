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

package com.jumpbuttonstudios.zombiegame.weapons.drops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.box2d.Box2DObject;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.zombiegame.collision.CollisionFilters;
import com.jumpbuttonstudios.zombiegame.level.Level;

/**
 * Base class for all drops, a drop stores whatever it contains within it and is
 * passed onto the character when picked up
 * 
 * @author Stephen Gibson
 */
public abstract class Drop<V> extends Box2DObject {

	/** Level instance */
	Level level;

	/** The icon for this drop */
	protected Sprite icon;

	/** The position of this drop */
	protected Vector2 position;

	/** The sound made when something is picked up */
	protected Sound pickup = Gdx.audio.newSound(Gdx.files
			.internal("SFX/Pickup.wav"));

	/** If the drop has been picked up */
	protected boolean pickedUp = false;

	/** How long this drop will remain on the ground before being disappearing */
	double expireTime;

	/** The create time of this drop */
	double createTime;

	/** if the drop has been created */
	protected boolean created;

	public Drop(Level level, Vector2 position, Sprite icon) {
		this.level = level;
		this.position = position;
		this.icon = icon;

		/* Give the expire time a bit of randomness */
		expireTime = TimeConversion.secondToNanos(MathUtils.random(15, 20));
		/* Set the create time to now */
		createTime = TimeUtils.nanoTime();

	}

	/**
	 * Draws this drop
	 * 
	 * @param bach
	 */
	public void draw(SpriteBatch batch) {
		if (icon != null) {
			icon.setOrigin(icon.getWidth() / 2, icon.getHeight() / 2);
			icon.setPosition(body.getPosition().x - (icon.getWidth() / 2),
					body.getPosition().y - (icon.getHeight() / 2));
			icon.setRotation(body.getAngle() * MathUtils.radDeg);
			icon.draw(batch);
		}
	}

	/**
	 * Picks up the drop and gives it to the pickup-ee
	 * 
	 * @param parent
	 *            the pickup-ee
	 */
	public abstract void pickup(V parent);

	/** Creates box2d stuff for this drop */
	public void create() {
		createBody(level.getWorld(), BodyType.DynamicBody, position, false);
		createPolyFixture(icon.getWidth() / 2, icon.getHeight() / 2, 0.1f,
				0.55f, 0.15f, false);
		created = true;
		Filter filter = new Filter();
		filter.categoryBits = CollisionFilters.DROP;
		filter.maskBits = (short) (CollisionFilters.GROUND | CollisionFilters.PLAYER);
		body.getFixtureList().get(0).setFilterData(filter);
		body.setUserData(this);
	}

	/** @return when this drops sprite has an alpha of zero */
	public boolean fade(float delta) {
		icon.setAlpha(icon.getColor().a - 0.1f * delta);
		if (icon.getColor().a <= 0)
			return true;
		return false;
	}

	/** @return if this drop has expired */
	public boolean hasExpired() {
		return TimeUtils.nanoTime() - createTime > expireTime;
	}

	/** @return {@link #created} */
	public boolean isCreated() {
		return created;
	}

	/** @return {@link #pickedUp} */
	public boolean isPickedUp() {
		return pickedUp;
	}

	/**
	 * Set if this drop has been picked up
	 * 
	 * @param pickedUp
	 */
	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

}
