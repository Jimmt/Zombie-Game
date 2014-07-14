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

package com.jumpbuttonstudios.zombiegame.effects.zombiedeath;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.zombie.WalkingZombie;
import com.jumpbuttonstudios.zombiegame.character.zombie.Zombie;

/**
 * 
 * @author Stephen Gibson
 */
public class ZombieBodyParts extends Array<BodyPart> {

	/** The zombie these body parts belong to */
	Zombie zombie;

	public ZombieBodyParts(Character parent) {
		this.zombie = (Zombie) parent;

		/*
		 * Just to determine different body parts, as some have 2 parts such as
		 * upper and lower arm
		 */
		int chance = MathUtils.random(1, 3);

		/* Add arms */

		if (chance == 1) {
			add(new BodyPart(zombie, "Sprites/Zombies/Regular/Arm/Bottom.png"));
			add(new BodyPart(zombie, "Sprites/Zombies/Regular/Arm/Bottom.png"));
		} else if (chance == 2) {
			add(new BodyPart(zombie, "Sprites/Zombies/Regular/Arm/Top.png"));
			add(new BodyPart(zombie, "Sprites/Zombies/Regular/Arm/Top.png"));
		} else {
			add(new BodyPart(zombie, "Sprites/Zombies/Regular/Arm/Bottom.png"));
			add(new BodyPart(zombie, "Sprites/Zombies/Regular/Arm/Bottom.png"));
			add(new BodyPart(zombie, "Sprites/Zombies/Regular/Arm/Top.png"));
			add(new BodyPart(zombie, "Sprites/Zombies/Regular/Arm/Top.png"));

		}

		chance = MathUtils.random(1, 2);

		/* Add head */
		add(new BodyPart(zombie, "Sprites/Zombies/Regular/Head.png"));


		if (zombie instanceof WalkingZombie) {
			/* Add torso */
			if (chance == 1) {
				add(new BodyPart(zombie,
						"Sprites/Zombies/Regular/Body/Full.png"));
			} else {
				add(new BodyPart(zombie, "Sprites/Zombies/Regular/Body/Top.png"));

			}
			/* Add legs */
			add(new BodyPart(zombie, "Sprites/Zombies/Regular/Leg.png"));
			add(new BodyPart(zombie, "Sprites/Zombies/Regular/Leg.png"));
		} else {
			add(new BodyPart(zombie, "Sprites/Zombies/Regular/Body/Top.png"));

		}

	}

}
