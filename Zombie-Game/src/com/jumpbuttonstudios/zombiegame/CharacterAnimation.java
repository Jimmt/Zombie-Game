package com.jumpbuttonstudios.zombiegame;
import static net.dermetfan.utils.libgdx.box2d.Box2DUtils.height;
import static net.dermetfan.utils.libgdx.box2d.Box2DUtils.minX;
import static net.dermetfan.utils.libgdx.box2d.Box2DUtils.width;
import net.dermetfan.utils.libgdx.box2d.Box2DUtils;
import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;
import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

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

/**
 * 
 * @author Stephen Gibson
 */
public class CharacterAnimation extends AnimatedBox2DSprite {
	
	/** for internal, temporary usage */
	private static final Vector2 vec2 = new Vector2();

	public CharacterAnimation(AnimatedSprite animatedSprite) {
		super(animatedSprite);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw(Batch batch, Body body) {
		batch.setColor(getColor());
		float width = width(body), height = height(body);
		vec2.set(Box2DUtils.minX(body) + width / 2, Box2DUtils.minY(body) + height / 2);
		vec2.set(body.getWorldPoint(vec2));
		draw(batch, vec2.x, vec2.y, width, height, body.getAngle());
		System.out.println("here");
	}
	
	@Override
	public void draw(Batch batch, float x, float y, float width, float height,
			float rotation) {
		super.draw(batch, x, y, width, height, rotation);
	}


}


