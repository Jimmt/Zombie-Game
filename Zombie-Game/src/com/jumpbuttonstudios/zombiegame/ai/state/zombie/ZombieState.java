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

package com.jumpbuttonstudios.zombiegame.ai.state.zombie;

import com.gibbo.gameutil.ai.state.State;
import com.jumpbuttonstudios.zombiegame.character.zombie.Zombie;

/**
 * 
 * @author Stephen Gibson
 */
public class ZombieState implements State {	

	/** The zombie that is using this state */
	Zombie zombie;

	@Override
	public void enter(Object object) {
		zombie = (Zombie) object;
	}

	@Override
	public void execute(Object object) {
		zombie = (Zombie) object;

	}

	@Override
	public void exit(Object object) {
		zombie = (Zombie) object;

	}

}


