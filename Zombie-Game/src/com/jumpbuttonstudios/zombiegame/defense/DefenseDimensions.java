package com.jumpbuttonstudios.zombiegame.defense;

/**
 * Enum for the different width/heights of defenses. This can't be generated programmatically because 
 * the sprites are pseudo 3d, but we only need the 2d boundaries.
 * 

 * @author Jimmt
 */

public enum DefenseDimensions {
	BLOCK(86f, 116f, 10), DOOR(19f, 162f, 10), FLOOR(114f, 11f, 10), LADDER(0f, 0f, 10), WALL(52f, 160f, 10);
		
	private float width, height;
	private int cost;
	private static DefenseDimensions[] cache = values();

	DefenseDimensions(float width, float height, int cost) {
		this.width = width;
		this.height = height;
		this.cost = cost;
		
	}
	
	public int getCost(){
		return cost;
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
