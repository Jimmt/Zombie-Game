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

package com.jumpbuttonstudios.zombiegame.level;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gibbo.gameutil.box2d.Box2DFactory;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint.Pivots;
import com.jumpbuttonstudios.zombiegame.character.player.Player;
import com.jumpbuttonstudios.zombiegame.character.zombie.CrawlingZombie;
import com.jumpbuttonstudios.zombiegame.character.zombie.WalkingZombie;
import com.jumpbuttonstudios.zombiegame.character.zombie.Zombie;
import com.jumpbuttonstudios.zombiegame.collision.GameContactListener;
import com.jumpbuttonstudios.zombiegame.weapons.Bullet;

/**
 * The level class holds entities in the level, controls spawning and what not
 * 
 * @author Stephen Gibson
 */
public class Level {

	/**
	 * The Box2D factory used to hold the world and delete box2d related stuff,
	 * fixtures and what not
	 */
	public static Box2DFactory factory = new Box2DFactory(0, -9.8f, true);

	/** All the bullets present in the game */
	public static Array<Bullet> bullets = new Array<Bullet>();

	/** All characters present in the level */
	private Array<Character> characters = new Array<Character>();

	/** The game scene */
	public Forest forest;

	public Level() {

		/* Create the forest scenery */
		forest = new Forest(getWorld());
		/* Add a player to the first index in the array */
		characters.add(new Player(getWorld()));

		/* Just a test zombie, move on :D */
		characters.add(new CrawlingZombie(getWorld(), getPlayer(), -10, 2));
		characters.add(new WalkingZombie(getWorld(), getPlayer(), -5, 2));

		/* Set up a contact listener */
		getWorld().setContactListener(new GameContactListener(this));

	}

	/**
	 * Update the level logic and simulate physics
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		getWorld().step(1f / 60f, 5, 8);

		if (MathUtils.random(0, 100) < 1) {
			if (MathUtils.random(1, 2) == 1) {
				characters.add(new CrawlingZombie(getWorld(), getPlayer(),-15, 2));
			}else{
				characters.add(new WalkingZombie(getWorld(), getPlayer(), 15, 2));
				
			}
		}


		/*
		 * Update all the defined pivots to keep them updated in world
		 * coordinats
		 */
		for (PivotJoint pivot : Pivots.getPivotJoints().values()) {
			if (pivot.getParentCharacter() != null)
				pivot.update();
		}

		/*
		 * Update all the games characters, this includes the player which is
		 * always first in the array
		 */
		for (Character player : characters) {
			player.update(delta);
		}
		

		/* Update the Box2D factory so things get deleted */
		factory.update();

	}

	/**
	 * 
	 * @return the world stored in the {@link Box2DFactory}
	 */
	public static World getWorld() {
		return factory.getWorld();
	}

	/**
	 * 
	 * @return all the characters in the level
	 */
	public Array<Character> getCharacters() {
		return characters;
	}

	/**
	 * 
	 * @return the character instance, this is the first entry in the array of
	 *         characters
	 */
	public Player getPlayer() {
		return (Player) characters.first();
	}

}
