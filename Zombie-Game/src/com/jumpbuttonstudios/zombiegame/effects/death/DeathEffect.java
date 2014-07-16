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

package com.jumpbuttonstudios.zombiegame.effects.death;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudios.zombiegame.character.Character;

/**
 * 
 * @author Stephen Gibson
 */
public class DeathEffect {

	/** The parent of this death effect */
	Character parent;

	/**
	 * The body parts that will be thrown all over the place with the zombie
	 * dies
	 */
	Array<BodyPart> bodyParts = new Array<BodyPart>();
	
	/** If this death effect has been created, this prevents constant creation of box2d bodies */
	private boolean isCreated;

	/**
	 * Create a new death effect, this will create a bunch of body parts around
	 * the parent and throw them at random
	 * 
	 * @param parent
	 * @param bodyParts an array of body parts
	 */
	public <T> DeathEffect(Character parent, Array<BodyPart> bodyParts) {
		this.bodyParts = bodyParts;
		
	}
	
	public void draw(SpriteBatch batch){
		for(BodyPart part : bodyParts){
			part.draw(batch);
		}
	}
	
	/** Creates the death effect and throws each body part */
	public void create(){
		float angle = 180;
		bodyParts.shuffle();
		for(BodyPart part : bodyParts){
			part.createAndThrow(angle);
			angle -= 180 / bodyParts.size;
		}
		
		isCreated = true;
		
	}
	
	/** @return {@link #bodyParts} */
	public Array<BodyPart> getBodyParts() {
		return bodyParts;
	}
	
	/** @return {@link #isCreated}*/
	public boolean isCreated() {
		return isCreated;
	}


}
