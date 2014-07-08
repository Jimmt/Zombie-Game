package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Ground extends Actor{
	private Image left, middle, right;
	private Box2DObject b2d;
	private float width, height;
	
	
	public Ground(World world){
		left = new Image(new Texture(Gdx.files.internal("Environment/Ground/Left.png")));
		middle = new Image(new Texture(Gdx.files.internal("Environment/Ground/Middle.png")));
		right = new Image(new Texture(Gdx.files.internal("Environment/Ground/Right.png")));
		left.setScale(Constants.scale);
		middle.setScale(Constants.scale);
		right.setScale(Constants.scale);
		left.setX(0 - left.getDrawable().getMinWidth() * Constants.scale);
		middle.setX(0);
		right.setX(0 + left.getDrawable().getMinWidth() * Constants.scale);
		
		width = left.getDrawable().getMinWidth() * Constants.scale;
		height = left.getDrawable().getMinHeight() * Constants.scale;
		
		b2d = new Box2DObject();
		b2d.createBody(world, BodyType.StaticBody, new Vector2(0, 0), false);
		b2d.createPolyFixture(width / 2 * 3, height / 2, 0, 0, 0, false);
		
	}
	@Override
	public void act(float delta){
		super.act(delta);
		
	}
	@Override
	public void draw(Batch batch, float parentAlpha){
		left.draw(batch, parentAlpha);
		middle.draw(batch, parentAlpha);
		right.draw(batch, parentAlpha);
	}
}
