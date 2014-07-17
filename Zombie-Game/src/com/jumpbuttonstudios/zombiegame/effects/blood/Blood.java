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

package com.jumpbuttonstudios.zombiegame.effects.blood;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.zombiegame.character.Character;

/**
 * 
 * @author Stephen Gibson
 */
public class Blood {

	/** The character this blood came from */
	protected Character parent;

	/** The animated sprite for this blood effect */
	protected AnimatedSprite sprite;

	/** How long it takes for this blood to decay */
	protected double decayTime = TimeConversion.secondToNanos(60);

	/** When this blood was created */
	protected double createTime;

	public Blood(Character parent, AnimatedSprite sprite) {
		this.parent = parent;
		this.sprite = sprite;

		/* Set the create time to now */
		createTime = TimeUtils.nanoTime();
		/*
		 * Set the position of the blood at the position of the parents feet
		 * with a little bit of random offset on Y and X
		 */
		sprite.setPosition(
				(parent.getX() - sprite.getWidth() / 2)
						+ MathUtils.random(-0.2f, 0.2f), (parent.getY()
						- (parent.getHeight() / 2) - (sprite.getHeight() / 2))
						+ MathUtils.random(0.4f, 0.55f));
		sprite.setKeepSize(true);
	}

	public void draw(SpriteBatch batch) {
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.draw(batch);
	}

	/**
	 * Fades the blood out slowly after it decays
	 * 
	 * @return true when the sprite has faded to zero alpha
	 */
	public boolean fade(float delta) {
		sprite.setAlpha(sprite.getColor().a - 0.025f * delta);
		if (sprite.getColor().a <= 0)
			return true;
		return false;
	}

	/** Creates an exact copy */
	public Blood clone() {
		return new Blood(parent, new AnimatedSprite(sprite.getAnimation()));
	}

	/** @return the width of this blood effect */
	public float getWidth() {
		return sprite.getWidth();
	}

	/** @return the height of this blood effect */
	public float getHeight() {
		return sprite.getHeight();
	}

	/** @return if the blood has decayed */
	public boolean hasDecayed() {
		return TimeUtils.nanoTime() - createTime > decayTime;
	}

	/** @return the X coordinate of the blood effect */
	public float getX() {
		return sprite.getX();
	}

	/** @return the Y coordinate of the blood effect */
	public float getY() {
		return sprite.getY();
	}

}
