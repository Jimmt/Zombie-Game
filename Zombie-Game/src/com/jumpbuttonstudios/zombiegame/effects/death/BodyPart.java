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

package com.jumpbuttonstudios.zombiegame.effects.death;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.box2d.Box2DObject;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ZombieGame;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.collision.CollisionFilters;

/**
 * 
 * @author Stephen Gibson
 */
public class BodyPart extends Box2DObject {

	/** The character these body parts belong to */
	Character parent;

	/** The width of the body part */
	float width;
	/** The height of the body part */
	float height;

	/** The angle which the body part will be thrown in */
	float angle;

	/** The sprite to represent this body part */
	Sprite sprite;

	/** The time it takes for this body part to decay */
	double decayTime;

	/** The time this body part was created */
	double createTime;

	public BodyPart(Character parent, String asset) {
		this.parent = parent;
		this.sprite = new Sprite(ZombieGame.assets.get(asset, Texture.class));
		width = this.sprite.getWidth() * Constants.scale;
		height = this.sprite.getHeight() * Constants.scale;
		this.sprite.setSize(width, height);

		/* Set a random decay time for this body part */
		decayTime = TimeConversion.secondToNanos(MathUtils.random(5, 10));

	}

	public void draw(SpriteBatch batch) {
		if (sprite != null) {
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			sprite.setPosition(body.getPosition().x - (sprite.getWidth() / 2),
					body.getPosition().y - (sprite.getHeight() / 2));
			sprite.setRotation(body.getAngle() * MathUtils.radDeg);
			sprite.draw(batch);
		}
	}

	/**
	 * This will create a Box2DBody and fixture then throw it in a the given
	 * direction, this method can be quite expensive if called repeatedly
	 * 
	 * @param angle
	 */
	public void createAndThrow(float angle) {
		fd.filter.categoryBits = CollisionFilters.BODYPART;
		fd.filter.maskBits = CollisionFilters.GROUND;

		createBody(parent.getLevel().getWorld(), BodyType.DynamicBody,
				new Vector2(parent.getBody().getPosition()), false);
		createPolyFixture(width / 2 - 0.1f, height / 2 - 0.1f, 0.2f, 0.9f,
				0.1f, false);
		body.setTransform(body.getPosition(), MathUtils.random(0, 359)
				* MathUtils.degRad);
		body.applyForceToCenter(
				MathUtils.random(5, 10) * MathUtils.cosDeg(angle),
				MathUtils.random(5, 10) * MathUtils.sinDeg(angle), true);

		/* Set the decay time to now */
		createTime = TimeUtils.nanoTime();

	}

	/**
	 * Fade this body part out
	 * 
	 * @return true if the body part can be deleted
	  */
	public boolean fade(float delta) {
		sprite.setAlpha(sprite.getColor().a - 0.1f * delta);
		if (sprite.getColor().a <= 0){
			return true;
		}
		return false;
	}

	/** @return true if this body part has decayed and can be delete */
	public boolean hasDecayed() {
		return TimeUtils.nanoTime() - createTime > decayTime;
	}

	@Override
	public void dispose() {

	}

}
