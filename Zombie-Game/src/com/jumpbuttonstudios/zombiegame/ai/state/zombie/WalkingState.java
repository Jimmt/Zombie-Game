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

package com.jumpbuttonstudios.zombiegame.ai.state.zombie;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.gibbo.gameutil.ai.state.State;
import com.jumpbuttonstudios.zombiegame.character.Character.Facing;
import com.jumpbuttonstudios.zombiegame.character.zombie.Zombie;

/**
 * 
 * @author Stephen Gibson
 */
public class WalkingState implements State<Zombie> {

	@Override
	public void enter(Zombie zombie) {

		/* Set the zombies current animation to walking */
		zombie.setCurrentAnimation("walking");
		/* Set the animation to loop */
		zombie.getCurrentAnimation().getAnimation().setPlayMode(Animation.LOOP);
		/* Play the animation */
		zombie.getCurrentAnimation().play();
	}

	@Override
	public void execute(Zombie zombie) {
		Body body = zombie.getBody();

		/* First check which direction the zombie is facing */
		if (zombie.getFacing() == Facing.RIGHT) {
			/* If the target x is less than the zombie x, we have to turn him */
			if (zombie.getTarget().getX() < zombie.getX()) {
				zombie.setFacing(Facing.LEFT);
				/* Check if the current animation needs flipped */
				if (!zombie.getCurrentAnimation().isFlipX()) {
					zombie.getCurrentAnimation().flipFrames(true, false);
				}
			} else {
				/* Keep the speed below the max */
				if (Math.abs(body.getLinearVelocity().x) <= zombie
						.getMaxSpeed()) {
					body.applyForceToCenter(zombie.getAcceleration(), 0, true);
				}
			}
		} else {
			/* If the target x is less than the zombie x, we have to turn him */
			if (zombie.getTarget().getX() > zombie.getX()) {
				zombie.setFacing(Facing.RIGHT);
				/* Check if the current animation needs flipped */
				if (zombie.getCurrentAnimation().isFlipX()) {
					zombie.getCurrentAnimation().flipFrames(true, false);
				}
			} else {
				/* Keep the speed below the max */
				if (Math.abs(body.getLinearVelocity().x) <= zombie
						.getMaxSpeed()) {
					body.applyForceToCenter(-zombie.getAcceleration(), 0, true);
				}

			}
		}

		if (zombie.isTargetInRange())
			zombie.getStateMachine().changeState(new AttackState());

		// TODO Implement collision resolving
	}

	@Override
	public void exit(Zombie zombie) {
		zombie.getCurrentAnimation().stop();
	}

}
