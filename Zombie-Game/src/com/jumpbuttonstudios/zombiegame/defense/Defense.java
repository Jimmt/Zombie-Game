package com.jumpbuttonstudios.zombiegame.defense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.gibbo.gameutil.box2d.Box2DObject;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.level.Level;

public class Defense extends Box2DObject {
	private Sprite sprite;
	private Texture tmp;
	
	public Defense(World world, Vector2 position, float width, float height, String path){
		
		sprite = new Sprite(tmp = new Texture(Gdx.files.internal(path)));
		sprite.setSize(sprite.getWidth() * Constants.scale, sprite.getHeight() * Constants.scale);
		sprite.setPosition(position.x, position.y);
		
		float scaleX = width * Constants.scale;
		float scaleY = height * Constants.scale;
		
		createBody(world, BodyType.KinematicBody, new Vector2(position.x + scaleX / 2, position.y + scaleY / 2), true);
		createPolyFixture(scaleX / 2, scaleY / 2, 0.1f, 0.1f, 0.1f, false);
	}
	
	public void setPosition(float x, float y){
		sprite.setPosition(x, y);
	}
	
	public Sprite getSprite(){
		return sprite;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
		
	}

}
