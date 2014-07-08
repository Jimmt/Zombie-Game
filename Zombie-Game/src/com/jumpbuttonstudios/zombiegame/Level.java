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

package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpbuttonstudios.zombiegame.character.player.Player;

/**
 * The level class holds entities in the level, controls spawning and what not
 * 
 * @author Stephen Gibson
 */
public class Level {
	
	/** The Box2D world for the current level */
	public static World world = new World(new Vector2(0, -10f), true);	
	
	/** The player present in the game */
	public Player player;
	
	/** The ground */
	public Forest forest;

	public Level() {
		
		forest = new Forest(world);
		player = new Player(world);
		
	}
	
	/**
	 * Update the level logic and simualte physics
	 * @param delta
	 */
	public void update(float delta){
		world.step(1f/60f, 5, 8);
		
		player.update(delta);
	}

}
