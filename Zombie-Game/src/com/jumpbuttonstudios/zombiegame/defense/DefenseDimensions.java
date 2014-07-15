package com.jumpbuttonstudios.zombiegame.defense;

/**
 * Enum for the different width/heights of defenses. This can't be generated programmatically because 
 * the sprites are pseudo 3d, but we only need the 2d boundaries.
 * 

 * @author Jimmt
 */

public enum DefenseDimensions {
	BLOCK(86f, 116f), DOOR(19f, 162f), FLOOR(114f, 11f), LADDER(0f, 0f), WALL(52f, 160f);
		
	private float width, height;
	private static DefenseDimensions[] cache = values();

	DefenseDimensions(float width, float height) {
		this.width = width;
		this.height = height;
		
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public static DefenseDimensions[] getCache(){
		return cache;
	}
}
