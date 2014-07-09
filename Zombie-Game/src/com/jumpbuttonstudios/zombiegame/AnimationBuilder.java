package com.jumpbuttonstudios.zombiegame;

import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;
import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Builds animations for characters and entites present in the game
 * 
 * @author Austin "Jimmt" Hsieh
 * 
 */
public class AnimationBuilder {
	private Animation animation;
	private Texture sheet;
	private TextureRegion[] frames;

	public AnimationBuilder(float time, int rows, int cols, String path) {
		sheet = new Texture(Gdx.files.internal(path));
		TextureRegion[][] temp = TextureRegion.split(sheet, sheet.getWidth()
				/ rows, sheet.getHeight() / cols);

		frames = new TextureRegion[rows * cols];
		int index = 0;
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				frames[index++] = temp[i][j];

			}
		}

		animation = new Animation(time, frames);
		animation.setPlayMode(Animation.LOOP);

	}

	/**
	 * Creates a returns a new animation
	 * 
	 * @param time
	 *            time of each frame
	 * @param rows
	 *            rows on the sprite sheet
	 * @param cols
	 *            columns on the sprite sheet
	 * 
	 * @param width
	 *            width of each frame
	 * @param height
	 *            height of each frame
	 * @param path
	 *            the path to the spritesheet
	 * @return
	 */
	public static AnimatedBox2DSprite create(float time, int rows, int cols,
			float width, float height, boolean adjustSize, String path) {
		/* The sprite sheet */
		Texture sheet;
		/* Key frames */
		Array<Sprite> frames = new Array<Sprite>();
		;

		/* Set the sheet to the one provided */
		sheet = new Texture(Gdx.files.internal(path));

		/* Split up the sprite sheet into segments */
		TextureRegion[][] temp = TextureRegion.split(sheet, sheet.getWidth()
				/ cols, sheet.getHeight() / rows);

		/* Init the keyframes arry and size it by rows by columns */
		/* Fill the frames array with sprites that we split from the sheet */
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < cols; i++) {
				frames.add(new Sprite(temp[j][i]));

			}
		}

		Animation animation = new Animation(time, frames);
		AnimatedSprite animSprite = new AnimatedSprite(animation);
		AnimatedBox2DSprite animb2dSprite = new AnimatedBox2DSprite(animSprite);

		animb2dSprite.setAdjustSize(adjustSize);
		animb2dSprite.setHeight(height);
		animb2dSprite.setWidth(width);

		/* Return an animation */
		return animb2dSprite;
	}

	public Animation getAnimation() {
		return animation;
	}

}
