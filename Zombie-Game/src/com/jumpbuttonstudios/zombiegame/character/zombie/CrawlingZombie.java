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

package com.jumpbuttonstudios.zombiegame.character.zombie;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ai.state.zombie.WalkingState;
import com.jumpbuttonstudios.zombiegame.asset.Assets;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.effects.blood.BloodTrail;
import com.jumpbuttonstudios.zombiegame.level.Level;

/**
 * 
 * @author Stephen Gibson
 */
public class CrawlingZombie extends Zombie {

	/** The last position of a blood trail */
	BloodTrail lastBloodTrail;

	public CrawlingZombie(Level level, World world, Character target,
			float speed, float x, float y) {
		super(level, world, target, speed, x, y);

		/* Create animations */
		/* Walking animation */
		Vector2 tmp = addAnimation(AnimationBuilder.createb2d(0.1f, 1, 6,
				Constants.scale, Constants.scale,
				Assets.ZOMBIE_HALF_WALK.fileName, null), "walking");

		/* Attack animation */
		addAnimation(AnimationBuilder.createb2d(0.085f, 1, 4, Constants.scale,
				Constants.scale, Assets.ZOMBIE_HALF_ATTACK.fileName,
				null), "attacking");

		/* Setup the width and height from our animations sprites */
		width = tmp.x;
		height = tmp.y;

		/* Setup Box2D stuff */
		createBody(world, BodyType.DynamicBody, new Vector2(x, y), true);
		createPolyFixture(width / 2 - 0.25f, height / 2, 0.45f, 0.40f, 0.05f,
				false);
		body.setUserData(this);

		/* Setup the last blood trail */
		lastBloodTrail = new BloodTrail(this);

		/* Set contact filters up */
		setCollisionFilters();

		/* Set the current animation to idle */
		setCurrentAnimation("walking");

		/* Setup the zombie to face the target */
		faceTarget();

		/* Setup state machine */
		stateMachine.changeState(new WalkingState());

		/* Setup Zombie properties */
		acceleration = 8;
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (Math.abs(((lastBloodTrail.getX() + lastBloodTrail.getWidth() / 2))
				- getX() + (facing == Facing.LEFT ? 0.3f : -0.3f)) > lastBloodTrail
				.getWidth()) {
			lastBloodTrail = new BloodTrail(this);
			level.getBloodEffects().add(lastBloodTrail);

		}

	}

	@Override
	public void draw(SpriteBatch batch) {
		/*
		 * Check if we are in a walking animation, if so we need to adjust the
		 * position of the sprite to stay within the bounding box
		 */
		if (currentAnimation.equals(getAnimation("walking"))) {
			if (getBody().getLinearVelocity().x < 0) {
				currentAnimation.draw(batch, getBody().getPosition().x,
						getBody().getPosition().y - 0.15f, width, height,
						body.getAngle() * MathUtils.radDeg);
			} else {
				currentAnimation.draw(batch, getBody().getPosition().x,
						getBody().getPosition().y - 0.15f, width, height,
						body.getAngle() * MathUtils.radDeg);

			}

		} else if (currentAnimation.equals(getAnimation("attacking"))) {
			if (getFacing() == Facing.RIGHT) {
				currentAnimation.draw(batch, getBody().getPosition().x - 0.12f,
						getBody().getPosition().y, width, height,
						body.getAngle() * MathUtils.radDeg);
			} else {
				currentAnimation.draw(batch, getBody().getPosition().x + 0.12f,
						getBody().getPosition().y, width, height,
						body.getAngle() * MathUtils.radDeg);

			}
		} else {
			super.draw(batch);
		}
	}

}
