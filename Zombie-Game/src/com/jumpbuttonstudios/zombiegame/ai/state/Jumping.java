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

	/** Previous gun pivot point */
	Vector2 tmp = new Vector2();
	/** Previous muzzle pivot point */
	Vector2 tmp2 = new Vector2();

	/** The direction facing at the start of the jump, to reset pivots */
	Facing prevFacing;

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

		/* Set the previous facing direction from the characters current */
		prevFacing = character.getFacing();

		/* Set the animation as jumping */
		character.setCurrentAnimation("jumping");

		/* Make the character jump */
		character.jump();

		// setting correct jumping animation position
		if (character.getFacing() == Facing.RIGHT) {
			character.getCurrentAnimation().setX(-0.25f);

		}
		if (character.getFacing() == Facing.LEFT) {
			character.getCurrentAnimation().setX(-0.25f);
		}

		/* Store the old pivot points in a tmp place */
		tmp.set(character.getArm().getBodyPivot().getRelativePos());
		tmp2.set(character.getArm().getWeapon().getMuzzle().getPivot()
				.getRelativePos());
	}

	@Override
	public void execute(Object object) {
		character = (Character) object;
		Body body = character.getBody();

		/* Sets the pivots up to adjust position in the air */
		character.getArm().getBodyPivot().getRelativePos()
				.set(tmp.x + 0.25f, tmp.y - 0.285f);
		character.getArm().getWeapon().getMuzzle().getPivot().getRelativePos()
				.set(tmp2.x + 0.25f, tmp2.y - 0.285f);

		/*
		 * If the velocity is more than 0, we want to add a negative force to
		 * create air drag
		 */
		if (body.getLinearVelocity().x > 0)
			body.applyForceToCenter(-drag, 0, true);

		/*
		 * If the velocity is more less 0, we want to add a positive force to
		 * create air drag
		 */
		if (body.getLinearVelocity().x < 0)
			body.applyForceToCenter(drag, 0, true);

		/*
		 * If the A key is pressed, we check if the body velocity is below max,
		 * if so we can apply forces to control the character in the air
		 */
		if (Gdx.input.isKeyPressed(Keys.A)) {
			/* Keep below max speed */
			if (Math.abs(body.getLinearVelocity().x) <= character.getMaxSpeed()) {
				body.applyForceToCenter(-damp, 0, true);
			}
			/*
			 * If the D key is pressed, we check if the body velocity is below
			 * max, if so we can apply forces to control the character in the
			 * air
			 */
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			/* Keep below max speed */
			if (Math.abs(body.getLinearVelocity().x) <= character.getMaxSpeed()) {
				body.applyForceToCenter(damp, 0, true);
			}
		}

		/*
		 * If the velocity of the player is zero on the Y axis, must be on
		 * surface, set the player as grounded TODO Add a proper way to detect
		 * collision along the bottom of the fixture, it is much better and less
		 * buggy
		 */
		if (character.getBody().getLinearVelocity().y == 0) {
			character.setGrounded(true);
			/* Go back to an idle state */
			character.getStateMachine().changeState(Idle.instance());
		}

	}

	@Override
	public void exit(Object object) {
		character = (Character) object;
		/*
		 * Check if the character is facing the same way he was when he jumped,
		 * if not we flip the pivot values
		 */
		if (character.getFacing() != prevFacing){
			tmp.set(-tmp.x, tmp.y);
			tmp2.set(-tmp2.x, tmp2.y);
		}
		/* Finally reset the pivots to their original positions */
		character.getArm().getBodyPivot().getRelativePos().set(tmp);
		character.getArm().getWeapon().getMuzzle().getPivot().getRelativePos()
		.set(tmp2);
	}

	public static Jumping instance() {
		return instance;
	}

}
