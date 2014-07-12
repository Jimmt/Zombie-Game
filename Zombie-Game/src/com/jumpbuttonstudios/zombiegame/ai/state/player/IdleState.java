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

package com.jumpbuttonstudios.zombiegame.ai.state.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

/**
 * 
 * @author Stephen Gibson
 */
public class IdleState extends PlayerState {

	/** Single instance */
	private static IdleState instance = new IdleState();


	@Override
	public void enter(Object object) {
		super.enter(object);

		player.setCurrentAnimation("idle");
		if (player.getBody().getLinearVelocity().x < 0) {
			if (!player.getCurrentAnimation().isFlipX()) {
//				character.getCurrentAnimation().flipFrames(true, false);
			}
		} else {
			if (player.getCurrentAnimation().isFlipX()) {
//				character.getCurrentAnimation().flipFrames(true, false);
			}

		}
		player.getBody().setLinearDamping(10);

	}

	@Override
	public void execute(Object object) {
		super.execute(object);

		if (player.getBody().getLinearVelocity().y == 0) {
			player.setGrounded(true);
		}

		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)) {
			player.getStateMachine().changeState(RunningState.instance());
		} else if (Gdx.input.isKeyPressed(Keys.SPACE) && player.isGrounded()) {
			player.getStateMachine().changeState(JumpingState.instance());
		}

	}

	@Override
	public void exit(Object object) {
		super.execute(object);
		player.getBody().setLinearDamping(0);

	}

	public static IdleState instance() {
		return instance;
	}

}
