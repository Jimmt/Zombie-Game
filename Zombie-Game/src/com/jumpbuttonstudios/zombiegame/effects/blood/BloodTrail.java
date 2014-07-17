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

package com.jumpbuttonstudios.zombiegame.effects.blood;

import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.character.Character;

/**
 * 
 * @author Stephen Gibson
 */
public class BloodTrail extends Blood {

	public BloodTrail(Character parent) {
		super(parent, AnimationBuilder.create(1, 1, 1, Constants.scale,
				Constants.scale, "Effect/Blood/Trail.png", null));
		
		sprite.setPosition(
				(parent.getX() - sprite.getWidth() / 2), (parent.getY()
						- parent.getHeight() / 2));
	}
	

}
