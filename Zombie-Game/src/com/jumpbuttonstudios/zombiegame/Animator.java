package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Animator{
	private Animation animation;
	private Texture sheet;
	private TextureRegion[] frames;

	public Animator(float time, int rows, int cols, String path) {
		sheet = new Texture(Gdx.files.internal(path));
		TextureRegion[][] temp = TextureRegion.split(sheet, sheet.getWidth()
				/ rows, sheet.getHeight() / cols);
		
		for(int i = 0; i < temp.length; i++){
			for(int j = 0; j < temp[0].length; j++){
				
			}
		}
		

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

	public Animation getAnimation() {
		return animation;
	}
	
}
