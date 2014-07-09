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

package com.jumpbuttonstudios.zombiegame.ai.state;

import com.gibbo.gameutil.ai.state.State;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.Character.Facing;
import com.jumpbuttonstudios.zombiegame.character.player.Player;

/**
 * 
 * @author Stephen Gibson
 */
public class Jumping implements State {

	private static Jumping instance = new Jumping();

	/** Player instance */
	Character character;

	@Override
	public void enter(Object object) {
		character = (Character) object;

		character.setCurrentAnimation("jumping");
		character.jump();

		if (character.getFacing() == Facing.RIGHT) {
			character.getCurrentAnimation().setX(-0.25f); //setting correct jumping animation position
			if (character.getCurrentAnimation().isFlipX()) {
				character.getCurrentAnimation().flipFrames(true, false);
				System.out.println("lreft");

			}

		}
		if (character.getFacing() == Facing.LEFT) {
			if (!character.getCurrentAnimation().isFlipX()) {
				System.out.println("left");
				character.getCurrentAnimation().flipFrames(true, false);
			}
			character.getCurrentAnimation().setX(-0.25f);
		}
	}

	@Override
	public void execute(Object object) {

		if (character.getBody().getLinearVelocity().y == 0) {
			character.setGrounded(true);
		}
		character = (Character) object;
		if (character.isGrounded())
			character.getStateMachine().changeState(Idle.instance());

	}

	@Override
	public void exit(Object object) {
// character = (Character) object;
// character.getStateMachine().changeState(Idle.instance());
	}

	public static Jumping instance() {
		return instance;
	}

}
