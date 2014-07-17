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

package com.jumpbuttonstudios.zombiegame.asset.loaders;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudios.zombiegame.Constants;

/**
 * 
 * @author Stephen Gibson
 */
public class AnimationLoader
		extends
		AsynchronousAssetLoader<AnimatedSprite, AnimationLoader.AnimationLoaderParameter> {

	/** The animation to load */
	AnimatedSprite sprite;

	public AnimationLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			FileHandle file, AnimationLoaderParameter parameter) {
	}

	@Override
	public AnimatedSprite loadSync(AssetManager manager, String fileName,
			FileHandle file, AnimationLoaderParameter parameter) {
		
		/** The sprite sheet */
		Texture sheet = new Texture(file);
		/** Frames in the animation */
		Array<Sprite> frames = new Array<Sprite>();
		
		/* Split up the sprite sheet into segments */
		TextureRegion[][] temp = TextureRegion.split(sheet, sheet.getWidth()
				/ parameter.cols, sheet.getHeight() / parameter.rows);

		/* Fill the frames array with sprites that we split from the sheet */
		for (int j = 0; j < parameter.rows; j++) {
			for (int i = 0; i < parameter.cols; i++) {
				frames.add(new Sprite(temp[j][i]));

			}
		}

		/** Remove blank frames */
		if (parameter.blankFrames != null)
			for (int blank = 0; blank < parameter.blankFrames.length; blank++) {
				frames.removeIndex(parameter.blankFrames[blank]);
			}
		
		/* Create the LibGDX animation */
		Animation animation = new Animation(parameter.time, frames);
		/* Pass it to create new animated sprite */
		AnimatedSprite animSprite = new AnimatedSprite(animation);

		/* Keep custom sizes */
		animSprite.setKeepSize(true);
		
		/* Set the size to the given scale */
		animSprite.setSize(frames.first().getWidth() * parameter.scaleX, frames
				.first().getHeight() * parameter.scaleY);

		return animSprite;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			FileHandle file, AnimationLoaderParameter parameter) {
		return null;
	}

	public static class AnimationLoaderParameter extends
			AssetLoaderParameters<AnimatedSprite> {
		/** The time per frame */
		public float time;
		/** How many rows are in the sprite sheet */
		public int rows;
		/** How many cols are in the sprite sheet */
		public int cols;
		/** The scale on the X axis for world coords */
		public float scaleX = Constants.scale;
		/** The scale on the Y axis for world coords */
		public float scaleY = Constants.scale;

		/** The blank frames in this animation, to be removed */
		public int[] blankFrames;
		
		/**
		 * Creates a new parameter with the frame time, rows and cols
		 * @param time state time of each frame
		 * @param rows how many sprites on Y on the sheet
		 * @param cols how many sprites on X on the sheet
		 */
		public AnimationLoaderParameter(float time, int rows, int cols) {
			this.time = time;
			this.rows = rows;
			this.cols = cols;
		}
		
		/**
		/**
		 * Creates a new parameter with the frame time, rows and cols
		 * @param time state time of each frame
		 * @param rows how many sprites on Y on the sheet
		 * @param cols how many sprites on X on the sheet
		 * @param blankFrames the index of each blank frame that should be removed from the animation
		 */
		public AnimationLoaderParameter(float time, int rows, int cols, int[] blankFrames) {
			this(time, rows, cols);
			this.blankFrames = blankFrames;
		}

	}
}
