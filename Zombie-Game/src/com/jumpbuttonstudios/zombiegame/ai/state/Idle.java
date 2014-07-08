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
import com.jumpbuttonstudios.zombiegame.Player;

/**
 * 
 * @author Stephen Gibson
 */
public class Idle implements State {

	/** Single instance */
	private static Idle instance = new Idle();

	/** Player instance */
	Player player;

	@Override
	public void enter(Object object) {
		player = (Player) object;
		player.setFrontArm(player.getFrontArm());
		player.setBackArm(player.getBackArm());

	}

	@Override
	public void execute(Object object) {
		player = (Player) object;
		player.getB2d().getBody().setLinearVelocity(0, 0);
		
		if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)){
			player.getStateMachine().changeState(Running.instance());
		}

	}

	@Override
	public void exit(Object object) {
		
	}

	public static Idle instance() {
		return instance;
	}

}
