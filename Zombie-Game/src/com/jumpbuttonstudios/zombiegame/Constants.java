package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.Gdx;

public class Constants {
	public static int WIDTH = Gdx.graphics.getWidth();
	public static int HEIGHT = Gdx.graphics.getHeight();
	public static float scale = 1/80f;
	public static float TILE_SIZE = 64f;
	public static float UNIT_WIDTH = WIDTH * scale;
	public static float UNIT_HEIGHT = HEIGHT * scale;
	
	public static float WIDTH_SCALE = 32;
	public static float HEIGHT_SCALE = 18;
	
	public static void set(int width, int height){
		Constants.WIDTH = width;
		Constants.HEIGHT = height;
		UNIT_WIDTH = WIDTH * scale;
		UNIT_HEIGHT = HEIGHT * scale;
		
	}
}
