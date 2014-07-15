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

/**
 * @author Jimmt
 */


public class Defense extends Box2DObject {
	private Sprite sprite;
	private Texture tmp;
	private Vector2 temp;
	private float scaleWidth, scaleHeight;

	public Defense(World world, float x, float y, float width, float height, String path) {

		sprite = new Sprite(tmp = new Texture(Gdx.files.internal(path)));
		sprite.setSize(sprite.getWidth() * Constants.scale, sprite.getHeight() * Constants.scale);
		sprite.setPosition(x, y);

		scaleWidth = width * Constants.scale;
		scaleHeight = height * Constants.scale;

		createBody(world, BodyType.KinematicBody, new Vector2(x + scaleWidth / 2, y + scaleHeight
				/ 2), true);
		createPolyFixture(scaleWidth / 2, scaleHeight / 2, 0.1f, 0.1f, 0.1f, false);

		temp = new Vector2();
	}

	public float getWidth() {
		return scaleWidth;
	}

	public float getHeight() {
		return scaleHeight;
	}

	public void setPosition(float x, float y) {
		sprite.setPosition(x, y);
	}

	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void draw(SpriteBatch batch) {
		sprite.draw(batch);

		/** set Body to Sprite, because we move around the Sprite in DefensePlacer*/

		getBody().setTransform(
				temp.set(sprite.getX() + scaleWidth / 2, sprite.getY() + scaleHeight / 2), 0);

	}

}
