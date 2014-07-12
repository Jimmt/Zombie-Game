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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ai.state.zombie.WalkingState;
import com.jumpbuttonstudios.zombiegame.character.Character;

/**
 * 
 * @author Stephen Gibson
 */
public class WalkingZombie extends Zombie {

	public WalkingZombie(World world, Character target, float x, float y) {
		super(world, target, x, y);
		
		/* Create animations */
		/* Idle animation, we get the size of the sprites from this as well */
		Vector2 tmp = addAnimation(AnimationBuilder.createb2d(1, 1, 1,
				Constants.scale, Constants.scale,
				"Sprites/Zombies/Regular/Still.png", null), "idle");

		/* Walking animation */
		addAnimation(
				AnimationBuilder.createb2d(0.09f, 1, 7, Constants.scale,
						Constants.scale,
						"Sprites/Zombies/Regular/Full/Walk.png", null),
				"walking");

		/* Attack animation */
		addAnimation(AnimationBuilder.createb2d(0.085f, 1, 5, Constants.scale,
				Constants.scale, "Sprites/Zombies/Regular/Full/Attack.png",
				null), "attacking");

		/* Setup the width and height from our animations sprites */
		width = tmp.x * Constants.scale;
		height = tmp.y * Constants.scale;

		/* Setup Box2D stuff */
		createBody(world, BodyType.DynamicBody, new Vector2(x, y), true);
		setCollisionFilters();
		createPolyFixture(width / 2 - 0.25f, height / 2, 0.25f, 0.40f, 0.05f,
				false);
		body.setUserData(this);

		/* Set the current animation to idle */
		setCurrentAnimation("walking");

		/* Setup the zombie to face the target */
		faceTarget();

		/* Setup state machine */
		stateMachine.changeState(new WalkingState());

		/* Setup Zombie properties */
		maxSpeed = 1f;
		acceleration = 8;

	}

}


