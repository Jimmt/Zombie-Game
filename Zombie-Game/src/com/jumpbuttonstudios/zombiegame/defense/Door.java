package com.jumpbuttonstudios.zombiegame.defense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpbuttonstudios.zombiegame.Constants;

/**
 * @author Jimmt
 */

public class Door extends Defense {
	private boolean opened;
	private Sprite open, closed;
	private float openWidth, openHeight;

	public Door(World world, float x, float y, float width, float height, String path) {
		super(world, x, y, width, height, path);
		open = new Sprite(new Texture(Gdx.files.internal("Environment/Defense/Door/Open.png")));
		closed = new Sprite(new Texture(Gdx.files.internal("Environment/Defense/Door/Icon.png")));
		open.setSize(open.getWidth() * Constants.scale, open.getHeight() * Constants.scale);
		closed.setSize(closed.getWidth() * Constants.scale, closed.getHeight() * Constants.scale);

		openWidth = open.getWidth();
		openHeight = open.getHeight();

	}

	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);

		
		if (opened) {
			getSprite().set(open);
			getSprite().setSize(openWidth, openHeight);
			getBody().setActive(false);
		} else {
			getSprite().set(closed);
			getBody().setActive(true);
		}
	}
	
	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		open.setPosition(x, y);
		closed.setPosition(x, y);
	}

	public void operate() {
		opened = !opened;
	}

	public boolean isOpened() {
		return opened;
	}

	public float getOpenWidth() {
		return openWidth;
	}

	public float getOpenHeight() {
		return openHeight;
	}

}
