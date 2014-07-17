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

package com.jumpbuttonstudios.zombiegame.effects;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jumpbuttonstudios.zombiegame.Constants;

/**
 * 
 * @author Stephen Gibson
 */
public class Effect {

	/** The position of the effect */
	Vector2 pos = new Vector2();

	/** The angle of this effect */
	float angle;

	/** The animation for this effect */
	AnimatedSprite sprite;

	/**
	 * Create a new effect that can be used as a base to duplicate the same
	 * effect at a new position and angle
	 * 
	 * @param sprite
	 * @param x
	 * @param y
	 * @param angle
	 */
	public Effect(AnimatedSprite sprite) {
		this.sprite = sprite;

	}

	/**
	 * Creates a new effect using an already define effect, with a new position
	 * and angle
	 * 
	 * @param base
	 * @param x
	 * @param y
	 * @param angle
	 */
	public Effect(Effect base, float x, float y, float angle) {
		this.sprite = new AnimatedSprite(base.sprite.getAnimation());
		pos.set(x, y);
		this.angle = angle;
		sprite.play();
		sprite.getAnimation().setPlayMode(Animation.NORMAL);
		sprite.setKeepSize(true);
		sprite.setSize(sprite.getWidth() * Constants.scale, sprite.getHeight() * Constants.scale);
	}

	/**
	 * Draw this effect
	 * 
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		if (sprite != null && !sprite.isAnimationFinished()) {
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			sprite.setPosition(pos.x - sprite.getWidth() / 2,
					pos.y - sprite.getHeight() / 2);
			sprite.setRotation(angle);
			sprite.draw(batch);
		}
	}

	/** @return if the effect has finished */
	public boolean finished() {
		return sprite.isAnimationFinished();
	}


}
