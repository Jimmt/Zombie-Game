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

import net.dermetfan.utils.libgdx.AnnotationAssetManager;
import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Logger;
import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ZombieGame;
import com.jumpbuttonstudios.zombiegame.asset.Assets;

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
	AnnotationAssetManager assets = ZombieGame.assets;

	public LoadingScreen(ZombieGame zg) {
		super(zg);

		assets.getLogger().setLevel(Logger.INFO);
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
		
		stage.draw();
	}

	/**
	 * Loads all the assets into the asset manager, this includes sprites,
	 * sounds and music
	 */
	public void load() {
		assets.load(Assets.class);
	}

	/**
	 * Loads the resources needed to display this loading screen
	 */
	public void loadScreenAnimations() {

		/* Put the assets in the manager */
		assets.load(Assets.ZOMBIE_FULL_WALK);
		assets.load(Assets.PLAYER_RUN_ARMS);
		/* Force the assets to load */
		assets.finishLoading();

		/* Create a player animation */
		player = AnimationBuilder.create(0.06f, 2, 6, Constants.scale, Constants.scale, Assets.PLAYER_RUN_ARMS.fileName, new int[]{11});
		/* Create a zombie animation */
		zombie = AnimationBuilder.create(0.06f, 1, 7, Constants.scale, Constants.scale, Assets.ZOMBIE_FULL_WALK.fileName, null);
		/* Create a zombie animation */

		player.getAnimation().setPlayMode(Animation.LOOP);
		player.play();
		player.setPosition((0 - player.getWidth() / 2) + 1,
				0 - player.getHeight() / 2);

		zombie.getAnimation().setPlayMode(Animation.LOOP);
		zombie.play();
		zombie.setPosition((0 - zombie.getWidth() / 2) - 1,
				0 - zombie.getHeight() / 2);
		
		Label label = new Label("LOADING", getSkin());
//		label.setX(Constants.WIDTH - label.getWidth());
		label.setX(Constants.WIDTH / 2 - label.getWidth() / 2);
		label.setY(Constants.HEIGHT / 2 - Constants.HEIGHT / 5);
		stage.addActor(label);
	}
	
	

}
