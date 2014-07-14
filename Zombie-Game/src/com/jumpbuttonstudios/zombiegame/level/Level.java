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

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gibbo.gameutil.box2d.Box2DFactory;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint.Pivots;
import com.jumpbuttonstudios.zombiegame.character.player.Player;
import com.jumpbuttonstudios.zombiegame.character.zombie.CrawlingZombie;
import com.jumpbuttonstudios.zombiegame.collision.GameContactListener;
import com.jumpbuttonstudios.zombiegame.defense.Defense;
import com.jumpbuttonstudios.zombiegame.effects.Effect;
import com.jumpbuttonstudios.zombiegame.effects.blood.Blood;
import com.jumpbuttonstudios.zombiegame.effects.zombiedeath.BodyPart;
import com.jumpbuttonstudios.zombiegame.effects.zombiedeath.DeathEffect;
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
	public Box2DFactory factory = new Box2DFactory(0, -9.8f, true);

	/** All the bullets present in the game */
	public Array<Bullet> bullets = new Array<Bullet>();

	/** All characters present in the level */
	private Array<Character> characters = new Array<Character>();

	/** All effects currently being updated and drawn */
	private Array<Effect> effects = new Array<Effect>();

	/** All the death effects */
	private Array<DeathEffect> deathEffects = new Array<DeathEffect>();

	/** All blood effects in the level */
	private Array<Blood> bloodEffects = new Array<Blood>();

	/** All the defense in the level */
	private Array<Defense> defenses = new Array<Defense>();

	/** The game scene */
	public Forest forest;

	/** The wave generator for spawning zombies */
	public WaveGenerator waveGenerator;

	public Level() {

		/* Create the forest scenery */
		forest = new Forest(getWorld());
		/* Add a player to the first index in the array */
		characters.add(new Player(this, getWorld()));

		/* Set up a contact listener */
		getWorld().setContactListener(new GameContactListener(this));

		/* Create wave generator */
		waveGenerator = new WaveGenerator(this);

	}

	/**
	 * Update the level logic and simulate physics
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		getWorld().step(1f / 60f, 5, 8);

		/* Update wave generator to create waves */
		waveGenerator.update();

		/*
		 * Update all the defined pivots to keep them updated in world
		 * coordinats
		 */
		for (PivotJoint pivot : Pivots.getPivotJoints().values()) {
			if (pivot.getParentCharacter() != null)
				pivot.update();
		}

		/* Check if the blood has decayed and remove it if so */
		for (Blood blood : bloodEffects) {
			if (blood.hasDecayed())
				if (blood.fade(delta))
					bloodEffects.removeValue(blood, true);
		}

		/*
		 * Update all the games characters, this includes the player which is
		 * always first in the array
		 */
		for (Character player : characters) {
			player.update(delta);
		}

		/* Create any death effects that are waiting for the time step to finish */
		if (!getWorld().isLocked())
			for (DeathEffect deathEffect : deathEffects) {
				/*
				 * If the death effect has no body parts, remove it from the
				 * array
				 */
				if (deathEffect.getBodyParts().size == 0) {
					deathEffects.removeValue(deathEffect, true);
				}
				/* If the death effect has not been created, create it */
				if (!deathEffect.isCreated()) {
					deathEffect.create();
				}
				/*
				 * Iterate over each body part and check for decay, fade them if
				 * they have decayed then delete them
				 */
				for (BodyPart part : deathEffect.getBodyParts()) {
					if (part.hasDecayed())
						if (part.fade(delta)) {
							factory.deleteBody(part.getBody());
							deathEffect.getBodyParts().removeValue(part, true);
						}

				}
			}

		/* Update the Box2D factory so things get deleted */
		factory.update();

	}

	public Array<Defense> getDefenses() {
		return defenses;
	}

	/**
	 * 
	 * @return the world stored in the {@link Box2DFactory}
	 */
	public World getWorld() {
		return factory.getWorld();
	}

	/** @return {@link #deathEffects} */
	public Array<DeathEffect> getDeathEffects() {
		return deathEffects;
	}

	/** @return {@link #effects} */
	public Array<Effect> getEffects() {
		return effects;
	}

	/** @return {@link #bloodEffects} */
	public Array<Blood> getBloodEffects() {
		return bloodEffects;
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