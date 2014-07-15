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
import com.jumpbuttonstudios.zombiegame.character.Character.Facing;
import com.jumpbuttonstudios.zombiegame.character.player.Player;

/**
 * 
 * @author Stephen Gibson
 */
public class AttackState extends ZombieState {

	@Override
	public void enter(Object object) {
		super.enter(object);

		/* Set the current animation to attacking */
		zombie.setCurrentAnimation("attacking");
		/* Set the animation to loop */
		zombie.getCurrentAnimation().getAnimation().setPlayMode(Animation.LOOP);
		/* Play the animation */
		zombie.getCurrentAnimation().play();

	}

	@Override
	public void execute(Object object) {
		super.execute(object);

		/* First check which direction the zombie is facing */
		if (zombie.getFacing() == Facing.RIGHT) {
			/* If the target x is less than the zombie x, we have to turn him */
			if (zombie.getTarget().getX() > zombie.getX()) {
				zombie.setFacing(Facing.LEFT);
				/* Check if the current animation needs flipped */
				if (zombie.getCurrentAnimation().isFlipX()) {
					zombie.getCurrentAnimation().flipFrames(true, false);
				}
			}
		} else {
			/* If the target x is less than the zombie x, we have to turn him */
			if (zombie.getTarget().getX() < zombie.getX()) {
				zombie.setFacing(Facing.RIGHT);
				/* Check if the current animation needs flipped */
				if (!zombie.getCurrentAnimation().isFlipX()) {
					zombie.getCurrentAnimation().flipFrames(true, false);
				}

			}
		}

		/*
		 * Check if the current animation is finished and if the target is out
		 * of range of the zombie
		 */
		if (zombie.getCurrentAnimation().getAnimatedSprite()
				.isAnimationFinished()
				&& !zombie.isTargetInRange()) {
			/* Let go of the player */
			zombie.release((Player) zombie.getTarget());
			/* Switch back to a walking state */
			zombie.getStateMachine().changeState(new WalkingState());
		} else if (zombie.getCurrentAnimation().getAnimatedSprite()
				.isAnimationFinished() && zombie.isTargetInRange()) {
			// TODO We should put damage to target here, this means the damage
			// happens at the end of the attack animation
		}
	}

	@Override
	public void exit(Object object) {
		super.exit(object);
		/* Stop the animation */
		zombie.getCurrentAnimation().stop();
	}

}
