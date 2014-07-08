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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.gibbo.gameutil.ai.state.State;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.Character.Facing;
import com.jumpbuttonstudios.zombiegame.character.player.Player;

/**
 * 
 * @author Stephen Gibson
 */
public class Running implements State {

	private static Running instance = new Running();

	/** Player instance */
	Character character;

	@Override
	public void enter(Object object) {
		character = (Character) object;

		character.setCurrentAnimation("running");
		character.getCurrentAnimation().getAnimation()
				.setPlayMode(Animation.LOOP);
		character.getCurrentAnimation().play();

	}

	@Override
	public void execute(Object object) {
		character = (Character) object;
		Body body = character.getBody();
		

		if (Gdx.input.isKeyPressed(Keys.A)) {
			
			/* Check if character changed directed */
			if (character.getFacing() == Facing.RIGHT
					&& !character.getCurrentAnimation().isFlipX()) {
				character.getCurrentAnimation().flipFrames(true, false);
				((Player) character).getBackArm().getWeapon().flip(true, false);
			}
			character.setFacing(Facing.LEFT);
			
			/* Keep below max speed */
			if (Math.abs(body.getLinearVelocity().x) <= character.getMaxSpeed()) {
				body.applyForceToCenter(-character.getAcceleration(), 0, true);
			}
			if (Gdx.input.isKeyPressed(Keys.SPACE) && character.isGrounded())
				character.getStateMachine().changeState(Jumping.instance());
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			
			/* Check if character changed directed */
			if (character.getFacing() == Facing.LEFT
					&& character.getCurrentAnimation().isFlipX()) {
				character.getCurrentAnimation().flipFrames(true, false);
				((Player) character).getBackArm().getWeapon().flip(true, false);
			}
			character.setFacing(Facing.RIGHT);
			
			/* Keep below max speed */
			if (Math.abs(body.getLinearVelocity().x) <= character.getMaxSpeed()) {
				body.applyForceToCenter(character.getAcceleration(), 0, true);
			}
			if (Gdx.input.isKeyPressed(Keys.SPACE) && character.isGrounded())
				character.getStateMachine().changeState(Jumping.instance());

		} else if (Gdx.input.isKeyPressed(Keys.SPACE) && character.isGrounded()) {
			character.getStateMachine().changeState(Jumping.instance());
		} else if (character.isGrounded()) {
			/* Go idle if no keys pressed */
			character.getStateMachine().changeState(Idle.instance());
		}
		


	}

	@Override
	public void exit(Object object) {
		character.getCurrentAnimation().stop();
	}

	public static Running instance() {
		return instance;
	}

}
