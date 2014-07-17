package com.jumpbuttonstudios.zombiegame;

import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;
import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

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


	
	/**
	 * Creates a returns a new Box2D animated sprite, this is to be used with
	 * Box2D objects only
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
	 * @param scaleX
	 *            a scale factor for the X axis
	 * @param scaleY
	 *            a scale factor for the Y axis
	 * @param texture
	 *            the texture to be used for the animation
	 * @param blankFrames
	 *            If you have blank frames in your sprite sheet pass the frame
	 *            numbers in the form of an array, this will remove them and
	 *            resize the array accordingly. If you have no blank frames you
	 *            may pass null
	 * @return new AnimatedBox2DSprite
	 */
	public static AnimatedBox2DSprite createb2d(float time, int rows, int cols,
			float scaleX, float scaleY, String texture, int[] blankFrames) {
		/* The sprite sheet */
		Texture sheet = ZombieGame.assets.get(texture, Texture.class);
		/* Key frames */
		Array<Sprite> frames = new Array<Sprite>();
		
		/* Set the sheet to the one provided */
		
		/* Split up the sprite sheet into segments */
		TextureRegion[][] temp = TextureRegion.split(sheet, sheet.getWidth()
				/ cols, sheet.getHeight() / rows);
		
		/* Fill the frames array with sprites that we split from the sheet */
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < cols; i++) {
				frames.add(new Sprite(temp[j][i]));
				
			}
		}
		
		/** Remove blank frames */
		if (blankFrames != null)
			for (int blank = 0; blank < blankFrames.length; blank++) {
				frames.removeIndex(blankFrames[blank]);
			}
		
		Animation animation = new Animation(time, frames);
		AnimatedSprite animSprite = new AnimatedSprite(animation);
		AnimatedBox2DSprite animb2dSprite = new AnimatedBox2DSprite(animSprite);
		
		animb2dSprite.setAdjustSize(false);
		animb2dSprite.setHeight(frames.first().getHeight() * scaleX);
		animb2dSprite.setWidth(frames.first().getWidth() * scaleY);
		
		/* Return an animation */
		return animb2dSprite;
	}
	
	/**
	 * Creates a returns a AnimatedSprite
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
	 * @param scaleX
	 *            a scale factor for the X axis
	 * @param scaleY
	 *            a scale factor for the Y axis
	 * @param texture
	 *            the texture to be used for the animation
	 * @param blankFrames
	 *            If you have blank frames in your sprite sheet pass the frame
	 *            numbers in the form of an array, this will remove them and
	 *            resize the array accordingly. If you have no blank frames you
	 *            may pass null
	 * @return new AnimatedBox2DSprite
	 */
	public static AnimatedSprite create(float time, int rows, int cols,
			float scaleX, float scaleY, String texture, int[] blankFrames) {
		/* The sprite sheet */
		Texture sheet;
		/* Key frames */
		Array<Sprite> frames = new Array<Sprite>();
		;

		/* Set the sheet to the one provided */
		sheet = ZombieGame.assets.get(texture, Texture.class);

		/* Split up the sprite sheet into segments */
		TextureRegion[][] temp = TextureRegion.split(sheet, sheet.getWidth()
				/ cols, sheet.getHeight() / rows);

		/* Fill the frames array with sprites that we split from the sheet */
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < cols; i++) {
				frames.add(new Sprite(temp[j][i]));

			}
		}

		/** Remove blank frames */
		if (blankFrames != null)
			for (int blank = 0; blank < blankFrames.length; blank++) {
				frames.removeIndex(blankFrames[blank]);
			}

		Animation animation = new Animation(time, frames);
		AnimatedSprite animSprite = new AnimatedSprite(animation);
		
		animSprite.setSize(frames.first().getWidth() * scaleX, frames.first().getHeight() * scaleY);
		animSprite.setKeepSize(true);
		
		/* Return an animation */
		return animSprite;
	}
	
	/**
	 * @deprecated
	 * @param time
	 * @param rows
	 * @param cols
	 * @param scaleX
	 * @param scaleY
	 * @param path
	 * @param blankFrames
	 * @return
	 */
//	public static AnimatedSprite create(float time, int rows, int cols,
//			float scaleX, float scaleY, String path, int[] blankFrames) {
//		/* The sprite sheet */
//		Texture sheet;
//		/* Key frames */
//		Array<Sprite> frames = new Array<Sprite>();
//		;
//
//		/* Set the sheet to the one provided */
//		sheet = new Texture(Gdx.files.internal(path));
//
//		/* Split up the sprite sheet into segments */
//		TextureRegion[][] temp = TextureRegion.split(sheet, sheet.getWidth()
//				/ cols, sheet.getHeight() / rows);
//
//		/* Fill the frames array with sprites that we split from the sheet */
//		for (int j = 0; j < rows; j++) {
//			for (int i = 0; i < cols; i++) {
//				frames.add(new Sprite(temp[j][i]));
//
//			}
//		}
//
//		/** Remove blank frames */
//		if (blankFrames != null)
//			for (int blank = 0; blank < blankFrames.length; blank++) {
//				frames.removeIndex(blankFrames[blank]);
//			}
//
//		Animation animation = new Animation(time, frames);
//		AnimatedSprite animSprite = new AnimatedSprite(animation);
//		
//		animSprite.setSize(frames.first().getWidth() * scaleX, frames.first().getHeight() * scaleY);
//
//		/* Return an animation */
//		return animSprite;
//	}
	
	/**
	 * @deprecated
	 * @param time
	 * @param rows
	 * @param cols
	 * @param scaleX
	 * @param scaleY
	 * @param path
	 * @param blankFrames
	 * @return
	 */
//	public static AnimatedBox2DSprite createb2d(float time, int rows, int cols,
//			float scaleX, float scaleY, String path, int[] blankFrames) {
//		/* The sprite sheet */
//		Texture sheet;
//		/* Key frames */
//		Array<Sprite> frames = new Array<Sprite>();
//		;
//
//		/* Set the sheet to the one provided */
//		sheet = new Texture(Gdx.files.internal(path));
//
//		/* Split up the sprite sheet into segments */
//		TextureRegion[][] temp = TextureRegion.split(sheet, sheet.getWidth()
//				/ cols, sheet.getHeight() / rows);
//
//		/* Fill the frames array with sprites that we split from the sheet */
//		for (int j = 0; j < rows; j++) {
//			for (int i = 0; i < cols; i++) {
//				frames.add(new Sprite(temp[j][i]));
//
//			}
//		}
//
//		/** Remove blank frames */
//		if (blankFrames != null)
//			for (int blank = 0; blank < blankFrames.length; blank++) {
//				frames.removeIndex(blankFrames[blank]);
//			}
//
//		Animation animation = new Animation(time, frames);
//		AnimatedSprite animSprite = new AnimatedSprite(animation);
//		AnimatedBox2DSprite animb2dSprite = new AnimatedBox2DSprite(animSprite);
//
//		animb2dSprite.setAdjustSize(false);
//		animb2dSprite.setHeight(frames.first().getHeight() * scaleX);
//		animb2dSprite.setWidth(frames.first().getWidth() * scaleY);
//
//		/* Return an animation */
//		return animb2dSprite;
//	}
	
	

}
