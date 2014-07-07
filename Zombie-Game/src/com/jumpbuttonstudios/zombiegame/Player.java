package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Image{
	private World world;
	
	public Player(World world){
		super(new Texture(Gdx.files.internal("still.png")));
		this.world = world;
	}

}
