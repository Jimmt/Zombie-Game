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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.jumpbuttonstudios.zombiegame.character.Character.Facing;

/**
 * 
 * @author Stephen Gibson
 */
public class RunningState extends PlayerState {

	private static RunningState instance = new RunningState();

	@Override
	public void enter(Object object) {
		super.enter(object);

		/* Set the animation to running */
		player.setCurrentAnimation("running");
		/* Set the animation to loop */
		player.getCurrentAnimation().getAnimation().setPlayMode(Animation.LOOP);
		/* Play the animation */
		player.getCurrentAnimation().play();

	}

	@Override
	public void execute(Object object) {
		super.enter(object);
		Body body = player.getBody();

		if (Gdx.input.isKeyPressed(Keys.A)) {

			if (player.getFacing() == Facing.RIGHT)
				player.getCurrentAnimation().getAnimation()
						.setPlayMode(Animation.LOOP_REVERSED);

			/* Keep below max speed */
			if (Math.abs(body.getLinearVelocity().x) <= player.getMaxSpeed()) {
				body.applyForceToCenter(-player.getAcceleration(), 0, true);
			}

		} else if (Gdx.input.isKeyPressed(Keys.D)) {

			/* Keep below max speed */
			if (Math.abs(body.getLinearVelocity().x) <= player.getMaxSpeed()) {
				body.applyForceToCenter(player.getAcceleration(), 0, true);
			}

		} else if (Gdx.input.isKeyPressed(Keys.SPACE) && player.isGrounded()) {
			player.getStateMachine().changeState(JumpingState.instance());
		} else if (player.isGrounded()) {
			/* Go idle if no keys pressed */
			player.getStateMachine().changeState(IdleState.instance());
		}

	}

	@Override
	public void exit(Object object) {
		super.exit(object);
		player.getCurrentAnimation().stop();
		/* Stop the running sound */
	}

	public static RunningState instance() {
		return instance;
	}

}
