package com.jumpbuttonstudios.zombiegame.character.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jumpbuttonstudios.zombiegame.Constants;

public class Weapon extends Sprite {
	private int clipSize, rof;
	
	public Weapon(String path, int clipSize, int rof){
		super(new Texture(Gdx.files.internal(path)));
		
		this.clipSize = clipSize;
		this.rof = rof;

		setSize(getWidth() * Constants.scale, getHeight() * Constants.scale);

	}
	
	public int getClipSize(){
		return clipSize;
	}
	
	//TODO weapon firing
	
}
