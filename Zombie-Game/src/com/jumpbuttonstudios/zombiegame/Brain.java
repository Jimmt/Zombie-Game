package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.gibbo.gameutil.box2d.Box2DObject;
import com.jumpbuttonstudios.zombiegame.collision.CollisionFilters;

public class Brain extends Box2DObject {
	private Sprite sprite;

	public Brain(World world) {
		sprite = new Sprite(new Texture(Gdx.files.internal("Sprites/brain.png")));

		createBody(world, BodyType.DynamicBody, new Vector2(-2, 4), true);

		sprite.setSize(sprite.getWidth() * Constants.scale, sprite.getHeight() * Constants.scale);

		createPolyFixture(sprite.getWidth() / 2, sprite.getHeight() / 2, 0.1f, 1.0f, 0.00f, false);

		body.setUserData(this);
		Filter filter = body.getFixtureList().get(0).getFilterData();
		filter.categoryBits = (short) CollisionFilters.BRAIN;
		filter.maskBits = (short) (CollisionFilters.GROUND | CollisionFilters.ZOMBIE);
		body.getFixtureList().get(0).setFilterData(filter);
	}

	public void draw(SpriteBatch batch) {
		sprite.draw(batch);

	}

	public void update(float delta) {
		sprite.setPosition(getBody().getPosition().x - sprite.getWidth() / 2, getBody().getPosition().y - sprite.getHeight() / 2);

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
