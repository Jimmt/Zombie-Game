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
import com.gibbo.gameutil.ai.state.State;
import com.jumpbuttonstudios.zombiegame.character.player.Player;

/**
 * 
 * @author Stephen Gibson
 */
public class IdlePlayerState implements State<Player> {

	/** Single instance */
	private static IdlePlayerState instance = new IdlePlayerState();



	public static IdlePlayerState instance() {
		return instance;
	}

	@Override
	public void enter(Player player) {
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
	public void execute(Player player) {

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
	public void exit(Player player) {
		player.getBody().setLinearDamping(0);
		
	}

}
