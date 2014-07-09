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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.gibbo.gameutil.ai.state.State;
import com.jumpbuttonstudios.zombiegame.character.Character;

/**
 * 
 * @author Stephen Gibson
 */
public class Idle implements State {

	/** Single instance */
	private static Idle instance = new Idle();

	/** Character instance */
	Character character;

	@Override
	public void enter(Object object) {
		character = (Character) object;

		character.setCurrentAnimation("idle");
		if (character.getBody().getLinearVelocity().x < 0) {
			if (!character.getCurrentAnimation().isFlipX()) {
				character.getCurrentAnimation().flipFrames(true, false);
			}
		} else {
			if (character.getCurrentAnimation().isFlipX()) {
				character.getCurrentAnimation().flipFrames(true, false);
			}

		}
		character.getBody().setLinearDamping(10);

	}

	@Override
	public void execute(Object object) {
		character = (Character) object;

		if (character.getBody().getLinearVelocity().y == 0) {
			character.setGrounded(true);
		}
		

		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)) {
			character.getStateMachine().changeState(Running.instance());
		} else if (Gdx.input.isKeyPressed(Keys.SPACE) && character.isGrounded()) {
			character.getStateMachine().changeState(Jumping.instance());
		}

	}

	@Override
	public void exit(Object object) {
		character.getBody().setLinearDamping(0);

	}

	public static Idle instance() {
		return instance;
	}

}
