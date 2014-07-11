/**
 * Copyright 2014 Stephen Gibson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jumpbuttonstudios.zombiegame.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.gibbo.gameutil.box2d.Box2DObject;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.level.Level;

/**
 * 
 * @author Stephen Gibson
 */
public class Limb extends Box2DObject {

	/** The position of the limb relative to the parent */
	Vector2 position = new Vector2();

	/** The pivot point relative to the body of the limb */
	Vector2 pivot = new Vector2();

	/** The parent of this limb */
	Character parent;

	/** The sprite of the limb */
	private Sprite sprite;

	public Limb(Character parent, String path, float x, float y, float pivotX,
			float pivotY) {
		this.parent = parent;
		sprite = new Sprite(new Texture(Gdx.files.internal(path)));
		position.set(x, y);
		pivot.set(pivotX, pivotY);

		float width = sprite.getWidth() * Constants.scale;
		float height = sprite.getHeight() * Constants.scale;
		sprite.setSize(sprite.getWidth() * Constants.scale, sprite.getHeight()
				* Constants.scale);
		createBody(Level.world, BodyType.DynamicBody, new Vector2(parent.getX()
				+ position.x, parent.getY() + position.y), false);
		createPolyFixture(width / 2, height / 2, new Vector2(pivot.x, pivot.y),
				0, 0.01f, 0, 0, true);
		getBody().setGravityScale(0);

	}

	public void update(float angle) {
		body.setTransform(parent.getBody().getPosition().add(position), angle);

	}

	public void draw(SpriteBatch batch) {
		sprite.setOrigin((sprite.getWidth() / 2) - pivot.x, (sprite.getHeight() / 2) - pivot.y);
		sprite.setPosition(body.getPosition().x, body.getPosition().y);
		sprite.setRotation(getBody().getAngle() * MathUtils.radDeg);
		sprite.draw(batch);
	}

	public Vector2 getPosition() {
		return position;
	}

	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void dispose() {

	}

	public static class LimbBuilder {

	}

}
