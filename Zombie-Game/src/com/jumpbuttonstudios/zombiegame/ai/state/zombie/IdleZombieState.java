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

import com.gibbo.gameutil.ai.state.State;
import com.jumpbuttonstudios.zombiegame.character.zombie.Zombie;

/**
 * 
 * @author Stephen Gibson
 */
public class IdleZombieState implements State<Zombie> {

	@Override
	public void enter(Zombie zombie) {
		
		/* Set the zombies animation to idle */
		zombie.setCurrentAnimation("idle");
		/* If the zombie is got a negative velcocity, we check if the animation needs flipped */
		if (zombie.getBody().getLinearVelocity().x < 0) {
			/* Check if the animation needs flipped */
			if (!zombie.getCurrentAnimation().isFlipX()) {
				/* Flip animation */
				zombie.getCurrentAnimation().flipFrames(true, false);
			}
		} else {
			/* If the zombie is got a postive velcocity, we check if the animation needs flipped */
			/* Check if the animation needs flipped */
			if (zombie.getCurrentAnimation().isFlipX()) {
				/* Flip animation */
				zombie.getCurrentAnimation().flipFrames(true, false);
			}

		}
		
		/* Give the zombie some dampening so he can not slide everywhere */
		zombie.getBody().setLinearDamping(10);
	}

	@Override
	public void execute(Zombie zombie) {

	}

	@Override
	public void exit(Zombie zombie) {
		zombie.getBody().setLinearDamping(0);

	}

}
