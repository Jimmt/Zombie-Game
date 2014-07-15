package com.jumpbuttonstudios.zombiegame.defense;

public enum DefenseDimensions {
	BLOCK(85f, 45f), DOOR(18f, 61f), FLOOR(113f, 63f), LADDER(0f, 0f), WALL(52f, 160f);
		
	private float width, height;

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
}
