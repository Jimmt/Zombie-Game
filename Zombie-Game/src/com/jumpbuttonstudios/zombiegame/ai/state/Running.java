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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudios.zombiegame.Animator;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.Player;

/**
 * 
 * @author Stephen Gibson
 */
public class Running implements State {

	private static Running instance = new Running();
	private Animator animator;
	private float stateTime = 0f;
	private TextureRegion currentFrame;

	/** The direction the character is running or facing */
	public enum Direction {
		LEFT, RIGHT;
	}

	Direction direction = Direction.RIGHT;

	/** Player instance */
	Player player;

	@Override
	public void enter(Object object) {
		player = (Player) object;
		Vector2 velocity = player.getB2d().getBody().getLinearVelocity();

		if (velocity.x < 0) {
			if (direction == Direction.RIGHT) {
				for (TextureRegionDrawable t : player.getDrawables()) {
					if (!t.getRegion().isFlipX())
						t.getRegion().flip(true, false);
				}

			}

			direction = Direction.LEFT;
		} else if (velocity.x > 0) {
			if (direction == Direction.LEFT)
				for (TextureRegionDrawable t : player.getDrawables()) {
					if (t.getRegion().isFlipX())
						t.getRegion().flip(true, false);
				}

			direction = Direction.RIGHT;
		}

		animator = new Animator(0.35f, 11, 1, "Sprites/Characters/Male/Run/WithArms1.png");

	}

	@Override
	public void execute(Object object) {
		player = (Player) object;
		Body body = player.getB2d().getBody();

		// Animate here
		stateTime += Gdx.graphics.getDeltaTime();
		if (stateTime >= animator.getAnimation().animationDuration) {
			stateTime = 0;
		}
		animator.getAnimation().setPlayMode(3);
		currentFrame = animator.getAnimation().getKeyFrame(stateTime, true);
		((TextureRegionDrawable) player.getDrawable()).setRegion(currentFrame);

		if (Gdx.input.isKeyPressed(Keys.A)) {
			body.setLinearVelocity(-player.getSpeed(), 0);
			if (direction == Direction.RIGHT) {

				for (TextureRegionDrawable t : player.getDrawables()) {
					if (!t.getRegion().isFlipX()) {
						t.getRegion().flip(true, false);
						currentFrame.flip(true, false);
					}
				}

				player.getBackArm().getPosition()
						.set(-95 * Constants.scale, player.getBackArm().getPosition().y);
				player.getFrontArm().getPosition()
						.set(45 * Constants.scale, player.getFrontArm().getPosition().y);

				direction = Direction.LEFT;
			}

		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			body.setLinearVelocity(player.getSpeed(), 0);
			if (direction == Direction.LEFT) {

				for (TextureRegionDrawable t : player.getDrawables()) {
					if (t.getRegion().isFlipX()) {
						t.getRegion().flip(true, false);
						currentFrame.flip(true, false);
					}
				}

				player.getBackArm().getPosition()
						.set(45 * Constants.scale, player.getBackArm().getPosition().y);
				player.getFrontArm().getPosition()
						.set(1 * Constants.scale, player.getFrontArm().getPosition().y);

				direction = Direction.RIGHT;
			}

		} else {
			stateTime = 0;
			player.getStateMachine().changeState(Idle.instance());
		}

	}

	public static Running instance() {
		return instance;
	}

	@Override
	public void exit(Object object) {

	}

}
