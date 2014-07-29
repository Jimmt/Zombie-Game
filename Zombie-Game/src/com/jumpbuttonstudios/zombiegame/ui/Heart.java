package com.jumpbuttonstudios.zombiegame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * @author Jimmt
 */

public class Heart extends Image {
	private Image full, empty;
	private Texture tmp;
	private boolean isEmpty;
	
	public Heart(){
		super(new Texture(Gdx.files.internal("UI/Icons/Heart/heart.png")));
		full = new Image(tmp = new Texture(Gdx.files.internal("UI/Icons/Heart/heart.png")));
		empty = new Image(tmp = new Texture(Gdx.files.internal("UI/Icons/Heart/heartempty.png")));
	}
	
	public void setEmpty(boolean isEmpty){
		this.isEmpty = isEmpty;
		if(isEmpty){
			setDrawable(empty.getDrawable());
		} else {
			setDrawable(full.getDrawable());
		}
	}
}
