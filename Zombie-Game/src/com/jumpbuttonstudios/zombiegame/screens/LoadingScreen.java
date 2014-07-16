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

package com.jumpbuttonstudios.zombiegame.screens;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ZombieGame;

/**
 * 
 * @author Stephen Gibson
 */
public class LoadingScreen extends AbstractScreen {

	/** The Player animation for loading */
	AnimatedSprite player;

	/** The Zombie animation for loading */
	AnimatedSprite zombie;

	/** Instance of the assets manager */
	AssetManager assets = ZombieGame.assets;

	public LoadingScreen(ZombieGame zg) {
		super(zg);

		loadScreenAnimations();
		load();

	}

	@Override
	public void render(float delta) {
		super.render(delta);
		/* Keeping loading assets */
		if (assets.update()) {
			zg.setScreen(new MenuScreen(zg));
		}

		/* Draw some stuff while we load */
		batch.setProjectionMatrix(LevelScreen.b2dCam.combined);
		batch.begin();
		player.draw(batch);
		zombie.draw(batch);
		batch.end();
	}

	/**
	 * Loads all the assets into the asset manager, this includes sprites,
	 * sounds and music
	 */
	public void load() {
		/* Load player walk and idle sprites */
		assets.load("Sprites/Characters/Male/Run/WithoutArms.png",
				Texture.class);
		assets.load("Sprites/Characters/Male/Stand/WithArms.png", Texture.class);
		assets.load("Sprites/Characters/Male/Stand/WithoutArms.png",
				Texture.class);

		/* Player Roll and jup */
		assets.load("Sprites/Characters/Male/Roll.png", Texture.class);
		assets.load("Sprites/Characters/Male/Jump/WithoutArms.png",
				Texture.class);

		/* Player melee */
		assets.load("Sprites/Characters/Male/Melee/Heavy.png", Texture.class);
		assets.load("Sprites/Characters/Male/Melee/Light.png", Texture.class);

		/* Player body parts, from head down */
		assets.load("Sprites/Characters/Male/BodyParts/Head.png", Texture.class);
		assets.load("Sprites/Characters/Male/BodyParts/Body.png", Texture.class);
		assets.load("Sprites/Characters/Male/BodyParts/Leg.png", Texture.class);
		assets.load("Sprites/Characters/Male/BodyParts/Arms/Back/Bent.png",
				Texture.class);
		assets.load("Sprites/Characters/Male/BodyParts/Arms/Back/Straight.png",
				Texture.class);
		assets.load("Sprites/Characters/Male/BodyParts/Arms/Front/Bent.png",
				Texture.class);
		assets.load(
				"Sprites/Characters/Male/BodyParts/Arms/Front/Straight.png",
				Texture.class);
		assets.load(
				"Sprites/Characters/Male/BodyParts/Arms/Separated/Bottom.png",
				Texture.class);
		assets.load("Sprites/Characters/Male/BodyParts/Arms/Separated/Top.png",
				Texture.class);
		
		/* Load zombies walk and idle sprites */
		assets.load("Sprites/Zombies/Regular/Still.png", Texture.class);
		assets.load("Sprites/Zombies/Regular/Full/Walk.png", Texture.class);
		assets.load("Sprites/Zombies/Regular/Half/Walk.png", Texture.class);

		/* Load zombie attack sprites */
		assets.load("Sprites/Zombies/Regular/Full/Attack.png", Texture.class);
		assets.load("Sprites/Zombies/Regular/Half/Attack.png", Texture.class);

		/* Load zombie body parts, from head down */
		assets.load("Sprites/Zombies/Regular/Head.png", Texture.class);
		assets.load("Sprites/Zombies/Regular/Body/Top.png", Texture.class);
		assets.load("Sprites/Zombies/Regular/Body/Bottom.png", Texture.class);
		assets.load("Sprites/Zombies/Regular/Body/Full.png", Texture.class);
		assets.load("Sprites/Zombies/Regular/Arm/Top.png", Texture.class);
		assets.load("Sprites/Zombies/Regular/Arm/Bottom.png", Texture.class);
		assets.load("Sprites/Zombies/Regular/Leg.png", Texture.class);
		
		/* Load M1911 assets */
		assets.load("Guns/M1911/WithArm.png", Texture.class);
		assets.load("Guns/M1911/Icon.png", Texture.class);
		assets.load("Guns/M1911/Bullet.png", Texture.class);

		/* Load AK74U assets */
		assets.load("Guns/M1911/WithArm.png", Texture.class);
		assets.load("Guns/M1911/Icon.png", Texture.class);
		assets.load("Guns/M1911/Bullet.png", Texture.class);

		/* Load Dragunov assets */
		assets.load("Guns/M1911/WithArm.png", Texture.class);
		assets.load("Guns/M1911/Icon.png", Texture.class);
		assets.load("Guns/M1911/Bullet.png", Texture.class);
		
		/* Load background */
		assets.load("Environment/BG/Left.png", Texture.class);
		assets.load("Environment/BG/Middle.png", Texture.class);
		assets.load("Environment/BG/Right.png", Texture.class);

		/* Load ground */
		assets.load("Environment/Ground/Left.png", Texture.class);
		assets.load("Environment/Ground/Middle.png", Texture.class);
		assets.load("Environment/Ground/Right.png", Texture.class);

		/* Load defences */
		assets.load("Environment/Defense/Block/Highlighted.png", Texture.class);
		assets.load("Environment/Defense/Block/Icon.png", Texture.class);
		assets.load("Environment/Defense/Door/Highlighted.png", Texture.class);
		assets.load("Environment/Defense/Door/Icon.png", Texture.class);
		assets.load("Environment/Defense/Floor/Highlighted.png", Texture.class);
		assets.load("Environment/Defense/Floor/Icon.png", Texture.class);
		assets.load("Environment/Defense/Ladder/Highlighted.png", Texture.class);
		assets.load("Environment/Defense/Ladder/Icon.png", Texture.class);
		assets.load("Environment/Defense/Wall/Highlighted.png", Texture.class);
		assets.load("Environment/Defense/Wall/Icon.png", Texture.class);
		assets.load("Environment/Defense/overlay.png", Texture.class);

		/* Load ammo drop thing */
		assets.load("Environment/Ammo.png", Texture.class);
		
		/* Load blood effects */
		assets.load("Effect/Blood/Splatter.png", Texture.class);
		assets.load("Effect/Blood/Spray.png", Texture.class);
		assets.load("Effect/Blood/Trail.png", Texture.class);
		assets.load("Effect/Blood/Overlay.png", Texture.class);

		/* Gun fire effect */
		assets.load("Effect/Fog.png", Texture.class);
		assets.load("Effect/Gunfire.png", Texture.class);
		assets.load("Effect/Haze.png", Texture.class);
		
		/* Load gun sounds */
		assets.load("SFX/Pistol.wav", Sound.class);
		assets.load("SFX/Click2.wav", Sound.class);
		assets.load("SFX/Reload.wav", Sound.class);

		/* Load player sounds */
		assets.load("SFX/Running.wav", Sound.class);
		assets.load("SFX/Landing.wav", Sound.class);

		/* Load zombie sounds */
		assets.load("SFX/ZombieDeath.wav", Sound.class);

		/* Load other */
		assets.load("SFX/Pickup.wav", Sound.class);


	}


	/**
	 * Loads the resources needed to display this loading screen
	 */
	public void loadScreenAnimations() {

		/* Put the assets in the manager */
		assets.load("Sprites/Characters/Male/Run/WithArms.png", Texture.class);
		assets.load("Sprites/Zombies/Regular/Full/Walk.png", Texture.class);

		/* Force the assets to load */
		assets.finishLoading();

		/* Create a player animation */
		player = AnimationBuilder.create(0.06f, 2, 6, Constants.scale,
				Constants.scale, assets.get(
						"Sprites/Characters/Male/Run/WithArms.png",
						Texture.class), new int[] { 11 });
		/* Create a zombie animation */
		zombie = AnimationBuilder.create(0.08f, 1, 7, Constants.scale,
				Constants.scale,
				assets.get("Sprites/Zombies/Regular/Full/Walk.png",
						Texture.class), null);

		player.getAnimation().setPlayMode(Animation.LOOP);
		player.play();
		player.setPosition((0 - player.getWidth() / 2) + 1,
				0 - player.getHeight() / 2);

		zombie.getAnimation().setPlayMode(Animation.LOOP);
		zombie.play();
		zombie.setPosition((0 - zombie.getWidth() / 2) - 1,
				0 - zombie.getHeight() / 2);
	}

}
