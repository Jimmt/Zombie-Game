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

package com.jumpbuttonstudios.zombiegame.asset;

import net.dermetfan.utils.libgdx.AnnotationAssetManager;
import net.dermetfan.utils.libgdx.AnnotationAssetManager.Asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

/**
 * All of the assets path locations for easy access via the
 * {@link AnnotationAssetManager}
 * <p>
 * Any assets added should be put in a logical order, as in group things
 * together
 * 
 * @author Stephen Gibson
 */
public class Assets {
	
	/* Player sprites */
	@Asset
	public static final AssetDescriptor<Texture>
	PLAYER_IDLE = new AssetDescriptor<Texture>(
			"Sprites/Characters/Male/Still.png", Texture.class ),
			PLAYER_RUN_ARMS = new AssetDescriptor<Texture>(
					"Sprites/Characters/Male/Run/WithArms.png", Texture.class),
			PLAYER_RUN_NOARMS = new AssetDescriptor<Texture>(
					"Sprites/Characters/Male/Run/WithoutArms.png",
					Texture.class),
			PLAYER_JUMP_NOARMS = new AssetDescriptor<Texture>(
					"Sprites/Characters/Male/Jump/WithoutArms.png",
					Texture.class),
			PLAYER_ROLL = new AssetDescriptor<Texture>(
					"Sprites/Characters/Male/Roll.png", Texture.class),
			PLAYER_MELEE_HEAVY = new AssetDescriptor<Texture>(
					"Sprites/Characters/Male/Melee/Heavy.png",
					Texture.class),
			PLAYER_MELEE_LIGHT = new AssetDescriptor<Texture>(
					"Sprites/Characters/Male/Melee/Light.png",
					Texture.class);

	/* Player body parts, head down */
	@Asset(type = Texture.class)
	public static final String PLAYER_HEAD = "Sprites/Characters/Male/BodyParts/Head.png",
			PLAYER_BODY = "Sprites/Characters/Male/BodyParts/Body.png",
			PLAYER_ARM_BACK_BENT = "Sprites/Characters/Male/BodyParts/Arms/Back/Bent.png",
			PLAYER_ARM_BACK_STRAIGHT = "Sprites/Characters/Male/BodyParts/Arms/Back/Straight.png",
			PLAYER_ARM_FRONT_BENT = "Sprites/Characters/Male/BodyParts/Arms/Front/Straight.png",
			PLAYER_ARM_FRONT_STRAIGHT = "Sprites/Characters/Male/BodyParts/Arms/Front/Straight.png",
			PLAYER_ARM_SEPARATED_TOP = "Sprites/Characters/Male/BodyParts/Arms/Separated/Top.png",
			PLAYER_ARM_SEPARATED_BOTTOM = "Sprites/Characters/Male/BodyParts/Arms/Separated/Bottom.png";

	/* Zombie sprites */
	@Asset
	public static final AssetDescriptor<Texture> ZOMBIE_IDLE = new AssetDescriptor<Texture>(
			"Sprites/Zombies/Regular/Still.png", Texture.class),
			ZOMBIE_FULL_WALK = new AssetDescriptor<Texture>(
					"Sprites/Zombies/Regular/Full/Walk.png",
					Texture.class),
			ZOMBIE_HALF_WALK = new AssetDescriptor<Texture>(
					"Sprites/Zombies/Regular/Half/Walk.png",
					Texture.class),
			ZOMBIE_FULL_ATTACK = new AssetDescriptor<Texture>(
					"Sprites/Zombies/Regular/Full/Attack.png",
					Texture.class),
			ZOMBIE_HALF_ATTACK = new AssetDescriptor<Texture>(
					"Sprites/Zombies/Regular/Half/Attack.png",
					Texture.class);

	/* Zombie body parts, head down */
	@Asset(type = Texture.class)
	public static final String ZOMBIE_HEAD = "Sprites/Zombies/Regular/Head.png",
			ZOMBIE_BODY_TOP = "Sprites/Zombies/Regular/Body/Top.png",
			ZOMBIE_BODY_BOTTOM = "Sprites/Zombies/Regular/Body/Bottom.png",
			ZOMBIE_BODY_FULL = "Sprites/Zombies/Regular/Body/Full.png",
			ZOMBIE_ARM_TOP = "Sprites/Zombies/Regular/Arm/Top.png",
			ZOMBIE_ARM_BOTTOM = "Sprites/Zombies/Regular/Arm/Bottom.png",
			ZOMBIE_LEG = "Sprites/Zombies/Regular/Leg.png";
	
	

	/* Weapons */
	/* M1911 */
	@Asset(type = Texture.class)
	public static final String GUN_M1911_ARM = "Guns/M1911/WithArm.png",
			GUN_M1911_ICON = "Guns/M1911/Icon.png",
			GUN_M1911_BULLET = "Guns/M1911/Bullet.png";

	/* AK74u */
	@Asset(type = Texture.class)
	public static final String GUN_AK74U_ARM = "Guns/AK74u/WithArm.png",
			GUN_AK74U_ICON = "Guns/AK74u/Icon.png",
			GUN_AK74U_BULLET = "Guns/AK74u/Bullet.png";

	/* Dragunov */
	@Asset(type = Texture.class)
	public static final String GUN_DRAGUNOV_ARM = "Guns/Dragunov/WithArm.png",
			GUN_DRAGUNOV_ICON = "Guns/Dragunov/Icon.png",
			GUN_DRAGUNOV_BULLET = "Guns/Dragunov/Bullet.png";

	/* Environment */
	/* Background */
	@Asset(type = Texture.class)
	public static final String BACKGROUND_LEFT = "Environment/BG/Left.png",
			BACKGROUND_MIDDLE = "Environment/BG/Middle.png",
			BACKGROUND_RIGHT = "Environment/BG/Right.png";

	/* Ground */
	@Asset(type = Texture.class)
	public static final String GROUND_LEFT = "Environment/Ground/Left.png",
			GROUND_MIDDLE = "Environment/Ground/Middle.png",
			GROUND_RIGHT = "Environment/Ground/Right.png";

	/* Defences */
	@Asset(type = Texture.class)
	public static final String DEFENSE_BLOCK_HIGHLIGHTED = "Environment/Defense/Block/Highlighted.png",
			DEFENSE_BLOCK_ICON = "Environment/Defense/Block/Icon.png",
			DEFENSE_DOOR_HIGHLIGHTED = "Environment/Defense/Door/Highlighted.png",
			DEFENSE_DOOR_ICON = "Environment/Defense/Door/Icon.png",
			DEFENSE_FLOOR_HIGHLIGHTED = "Environment/Defense/Floor/Highlighted.png",
			DEFENSE_FLOOR_ICON = "Environment/Defense/Floor/Icon.png",
			DEFENSE_LADDER_HIGHLIGHTED = "Environment/Defense/Ladder/Highlighted.png",
			DEFENSE_LADDER_ICON = "Environment/Defense/Ladder/Icon.png",
			DEFENSE_WALL_HIGHLIGHTED = "Environment/Defense/Wall/Highlighted.png",
			DEFENSE_WALL_ICON = "Environment/Defense/Wall/Icon.png";
	
	/* Menu screen background */
	@Asset(type = Texture.class)
	public static final String MENU_BACKGROUND = "UI/MenuBG.png";

	/* Ammo drop */
	@Asset(type = Texture.class)
	public static final String AMMO_DROP = "Environment/Ammo.png";

	/* Effects */
	/* Blood */
	@Asset
	public static final AssetDescriptor<Texture> EFFECT_BLOOD_SPRAY = new AssetDescriptor<Texture>(
			"Effect/Blood/Spray.png", Texture.class);
	@Asset(type = Texture.class)
	public static final String EFFECT_BLOOD_SPLATTER = "Effect/Blood/Splatter.png",
			EFFECT_BLOOD_TRAIL = "Effect/Blood/Trail.png",
			EFFECT_BLOOD_OVERLAY = "Effect/Blood/Overlay.png";

	/* Guns */
	@Asset
	public static final AssetDescriptor<Texture> EFFECT_GUNFIRE = new AssetDescriptor<Texture>(
			"Effect/Gunfire.png", Texture.class);

	/* Environmental */
	@Asset(type = Texture.class)
	public static final String EFFECT_FOG = "Effect/Fog.png",
			EFFECT_HAZE = "Effect/Haze.png";

	/* Sounds */
	public static final String SOUND_GUNFIRE = "SFX/Pistol.wav",
			SOUND_GUN_CLICK = "SFX/Click2.wav",
			SOUND_GUN_RELOAD = "SFX/Reload.wav",
			SOUND_PLAYER_LAND = "SFX/Landing.wav",
			SOUND_PLAYER_RUN = "SFX/Running.wav",
			SOUND_ZOMBIE_DEATH = "SFX/ZombieDeath.wav",
			SOUND_OTHER_PICKUP = "SFX/Pickup.wav";

}
