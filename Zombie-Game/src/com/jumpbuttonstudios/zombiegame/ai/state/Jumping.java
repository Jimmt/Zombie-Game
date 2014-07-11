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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.gibbo.gameutil.ai.state.State;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.Character.Facing;

/**
 * 
 * @author Stephen Gibson
 */
public class Jumping implements State {

	private static Jumping instance = new Jumping();

	/** Player instance */
	Character character;

	/** Previous gun anchor point */
	Vector2 tmp = new Vector2();

	/** How much drag is in the air */
	float drag = 1.5f;

	/**
	 * How much the player can dampening the movement and control movement in
	 * the air
	 */
	float damp = 5;

	@Override
	public void enter(Object object) {
		character = (Character) object;

		character.setCurrentAnimation("jumping");
		character.jump();

		if (character.getFacing() == Facing.RIGHT) {
			character.getCurrentAnimation().setX(-0.25f); // setting correct
															// jumping animation
															// position
			if (character.getCurrentAnimation().isFlipX()) {
				// character.getCurrentAnimation().flipFrames(true, false);

			}

		}
		if (character.getFacing() == Facing.LEFT) {
			if (!character.getCurrentAnimation().isFlipX()) {
				// character.getCurrentAnimation().flipFrames(true, false);
			}
			character.getCurrentAnimation().setX(-0.25f);
		}

		tmp.set(character.getArms().getFrontAnchor());
	}

	@Override
	public void execute(Object object) {
		character = (Character) object;
		Body body = character.getBody();

		character.getArms().getFrontAnchor().set(tmp.x + 0.25f, tmp.y - 0.285f);

		if (body.getLinearVelocity().x > 0)
			body.applyForceToCenter(-drag, 0, true);

		if (body.getLinearVelocity().x < 0)
			body.applyForceToCenter(drag, 0, true);

		if (Gdx.input.isKeyPressed(Keys.A)) {
			/* Keep below max speed */
			if (Math.abs(body.getLinearVelocity().x) <= character.getMaxSpeed()) {
				body.applyForceToCenter(-damp, 0, true);
				System.out.println("here");
			}
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			/* Keep below max speed */
			if (Math.abs(body.getLinearVelocity().x) <= character.getMaxSpeed()) {
				body.applyForceToCenter(damp, 0, true);
			}
		}

		if (character.getBody().getLinearVelocity().y == 0) {
			character.setGrounded(true);
			character.getStateMachine().changeState(Idle.instance());
		}

	}

	@Override
	public void exit(Object object) {
		character.getArms().getFrontAnchor().set(tmp);
		// character = (Character) object;
		// character.getStateMachine().changeState(Idle.instance());
	}

	public static Jumping instance() {
		return instance;
	}

}
