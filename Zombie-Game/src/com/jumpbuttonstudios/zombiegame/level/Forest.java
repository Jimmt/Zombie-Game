package com.jumpbuttonstudios.zombiegame.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.gibbo.gameutil.box2d.Box2DObject;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ZombieGame;
import com.jumpbuttonstudios.zombiegame.asset.Assets;
import com.jumpbuttonstudios.zombiegame.collision.CollisionFilters;

/**
 * The forest scene, consists of a boudary fixtures and draws the ground and
 * background images
 * 
 * @author Stephen Gibson
 * @author Jimmt
 * 
 */
public class Forest extends Box2DObject {

	/** The sprites of the ground */
	private Sprite left, middle, right;
	/** The background images */
	private Sprite backgroundLeft, backgroundMiddle, backgroundRight;

	/** The width and height of the ground fixture */
	private float groundWidth, groundHeight;

	public Forest(World world) {
		/* Load sprites */
		left = new Sprite(ZombieGame.assets.get(Assets.GROUND_LEFT, Texture.class));
		middle = new Sprite(ZombieGame.assets.get(Assets.GROUND_MIDDLE, Texture.class));
		right = new Sprite(ZombieGame.assets.get(Assets.GROUND_RIGHT, Texture.class));

		backgroundLeft = new Sprite(ZombieGame.assets.get(Assets.BACKGROUND_LEFT, Texture.class));
		backgroundMiddle = new Sprite(ZombieGame.assets.get(Assets.BACKGROUND_MIDDLE, Texture.class));
		backgroundRight = new Sprite(ZombieGame.assets.get(Assets.BACKGROUND_MIDDLE, Texture.class));

		/* Scale sprites */
		left.setSize(left.getWidth() * Constants.scale, left.getHeight()
				* Constants.scale);
		middle.setSize(middle.getWidth() * Constants.scale, middle.getHeight()
				* Constants.scale);
		right.setSize(right.getWidth() * Constants.scale, right.getHeight()
				* Constants.scale);
		backgroundLeft.setSize(backgroundLeft.getWidth() * Constants.scale,
				backgroundLeft.getHeight() * Constants.scale);
		backgroundMiddle.setSize(backgroundMiddle.getWidth() * Constants.scale,
				backgroundMiddle.getHeight() * Constants.scale);
		backgroundRight.setSize(backgroundRight.getWidth() * Constants.scale,
				backgroundRight.getHeight() * Constants.scale);

		/* Position sprites */
		left.setPosition(0 - left.getWidth(), -1);
		middle.setPosition(0, -1);
		right.setPosition(0 + right.getWidth(), -1);
		backgroundLeft.setPosition(0 - backgroundLeft.getWidth(), 0);
		backgroundMiddle.setPosition(0f, 0);
		backgroundRight.setPosition(0 + backgroundLeft.getWidth(), 0);

		/* Set ground size */
		groundWidth = 48;
		groundHeight = 0.5f;

		/* Create the ground */
		createBody(world, BodyType.StaticBody, new Vector2(16, 0.5f), false);

		createPolyFixture(groundWidth, groundHeight, 0, 0.50f, 0, false);
		Filter filter = body.getFixtureList().get(0).getFilterData();
		filter.categoryBits = (short) CollisionFilters.GROUND;
		filter.maskBits = (short) (CollisionFilters.PLAYER
				| CollisionFilters.ZOMBIE | CollisionFilters.BODYPART | CollisionFilters.DROP);
		getBody().getFixtureList().get(0).setFilterData(filter);

		body.setUserData(this);

	}

	public void draw(SpriteBatch batch) {
		backgroundLeft.draw(batch);
		backgroundMiddle.draw(batch);
		backgroundRight.draw(batch);
		left.draw(batch);
		middle.draw(batch);
		right.draw(batch);
	}

	public class Boundary extends Box2DObject {

		@Override
		public void dispose() {

		}

	}

	@Override
	public void dispose() {

	}
}
