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

package com.jumpbuttonstudios.zombiegame.collision;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.jumpbuttonstudios.zombiegame.level.Level;

/**
 * 
 * @author Stephen Gibson
 */
public class GameContactFilter implements ContactFilter {

	Level level;

	public GameContactFilter(Level level) {
	}

	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		Object A = fixtureA.getBody().getUserData();
		Object B = fixtureB.getBody().getUserData();

//		if (A instanceof Zombie && B instanceof Player) {
//			return false;
//
//		} else if (B instanceof Zombie && A instanceof Player) {
//			return false;
//
//		} else if (A instanceof Zombie && A instanceof Zombie) {
//			return false;
//		}
		return true;
	}

}
