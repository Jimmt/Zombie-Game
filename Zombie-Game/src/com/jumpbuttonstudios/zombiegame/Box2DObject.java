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

package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

/**
 * A base Box2D Object class, any object that uses Box2D can extends from this,
 * making it easier to create bodies and fixtures. As well as deleting the body
 * safely. When/if you serialise this class, none of the fields in here will be
 * saved, this is to prevent stack overflow to Box2D world serialization
 * problems
 * 
 * @author Stephen Gibson
 * 
 */
public class Box2DObject implements Disposable {

	/** The body for the Box2D Object */
	protected transient Body body;
	/** The body definition for the Box2D Object */
	protected transient BodyDef bd = new BodyDef();
	/**
	 * A fixture for the Box2D Object, you can use this fixture to create many
	 * fixtures
	 */
	protected transient Fixture fixture;
	/**
	 * The fixture definition that can be used for any fixture, it is
	 * recommended to reuse this one definition to avoud garbage collection and
	 * general memory problems
	 */
	protected transient FixtureDef fd = new FixtureDef();

	/**
	 * Create a new body for this Box2D Object, this should always be done
	 * before creating any fixtures or a nullpointer will be thrown
	 * 
	 * @param type
	 *            the body type
	 * @param position
	 *            the position of the body
	 * @param fixedRotation
	 *            if this body can rotate or not
	 * @return this method will return the object itself for method chaning
	 */
	public Box2DObject createBody(World world, BodyType type, Vector2 position,
			boolean fixedRotation) {
		if (world == null)
			throw new NullPointerException(
					"Trying to create a body on a null world!");
		bd.type = type;
		bd.position.set(position);
		bd.fixedRotation = fixedRotation;
		body = world.createBody(bd);
		return this;
	}

	/**
	 * Create a new polygon fixture given its dimensions and properties, this is
	 * the simplest method to creating a poly shaped fixture
	 * 
	 * @param hx
	 *            the <b>half</b> width of the fixture
	 * @param hy
	 *            the <b>half</b> height of the fixture
	 * @param density
	 *            the density of the fixture, usually in kg/m^2
	 * @param friction
	 *            the friction of the fixture
	 * @param restitution
	 *            the restitution of the fixture, determines how bouncy it is
	 * @param isSensor
	 *            if this fixture should be a sensor or not
	 */
	public void createPolyFixture(float hx, float hy, float density,
			float friction, float restitution, boolean isSensor) {
		if (body == null)
			throw new NullPointerException(
					"Trying to create a polygon fixture on a null body!");
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(hx, hy);
		fd.shape = shape;
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;
		fd.isSensor = isSensor;
		fixture = body.createFixture(fd);
	}

	/**
	 * Create a new polygon fixture given its dimensions and properties, this
	 * method has the ability to offset the center anchor point of the fixture
	 * relative to the body center
	 * 
	 * @param hx
	 *            the <b>half</b> width of the fixture
	 * @param hy
	 *            the <b>half</b> height of the fixture
	 * @param center
	 *            the center point on the body where the fixture will be created
	 * @param angle
	 *            the angle of the fixture relative to the body angle
	 * @param density
	 *            the density of the fixture, usually in kg/m^2
	 * @param friction
	 *            the friction of the fixture
	 * @param restitution
	 *            the restitution of the fixture, determines how bouncy it is
	 * @param isSensor
	 *            if this fixture should be a sensor or not
	 */
	public void createPolyFixture(float hx, float hy, Vector2 center,
			float angle, float density, float friction, float restitution,
			boolean isSensor) {
		if (body == null)
			throw new NullPointerException(
					"Trying to create a polygon fixture on a null body!");
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(hx, hy, center, angle);
		fd.shape = shape;
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;
		fd.isSensor = isSensor;
		fixture = body.createFixture(fd);
	}

	/**
	 * Create a new polygon fixture given its dimensions and properties, a more
	 * advanced method of creating a fixture that allows you to specify the
	 * exact coordinates of each vertice, be warned this only supports concave
	 * polygons and box2d does have a built in vertice limit per shape
	 * 
	 * @param vertices
	 *            the array of vertices to be passed to the shape
	 * @param density
	 *            the density of the fixture, usually in kg/m^2
	 * @param friction
	 *            the friction of the fixture
	 * @param restitution
	 *            the restitution of the fixture, determines how bouncy it is
	 * @param isSensor
	 *            if this fixture should be a sensor or not
	 */
	public void createPolyFixture(float[] vertices, float density,
			float friction, float restitution, boolean isSensor) {
		if (body == null)
			throw new NullPointerException(
					"Trying to create a polygon fixture on a null body!");
		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
		fd.shape = shape;
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;
		fd.isSensor = isSensor;
		fixture = body.createFixture(fd);
	}

	/**
	 * Create a new circle shape fixture, this is the simplest way of creating a
	 * circle fixture
	 * 
	 * @param position
	 *            the position of the fixture relative to the bodies origin
	 * @param radius
	 *            the radius of the fixture
	 * @param angle
	 *            the angle of the fixture relative to the body
	 * @param density
	 *            the density of the fixture, usually in kg/m^2
	 * @param friction
	 *            the friction of the fixture
	 * @param restitution
	 *            the restitution of the fixture, determines how bouncy it is
	 * @param isSensor
	 *            if this fixture should be a sensor or not
	 */
	public void createCircleFixture(Vector2 position, float radius,
			float angle, float density, float friction, float restitution,
			boolean isSensor) {
		if (body == null)
			throw new NullPointerException(
					"Trying to create a circle fixture on a null body!");
		CircleShape shape = new CircleShape();
		shape.setPosition(position);
		shape.setRadius(radius);
		fd.shape = shape;
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;
		fd.isSensor = isSensor;
		fixture = body.createFixture(fd);

	}

	/**
	 * Called when you want to destroy the body, this must be called in a loop
	 * as there is no gurantee that the world will be unlocked at that given
	 * time.<br>
	 * Therefore this works best when you call it while iterating through an
	 * array of entities that are queued and ready to be deleted
	 * <p>
	 * <b>INFO</b> If you are using the Box2DFactory, do not use this method or
	 * you will cause lag/null pointers
	 * 
	 * @param world
	 */
	public void destroy(World world) {
		if (world == null)
			throw new NullPointerException(
					"Trying to delete a body from a world that is null, how is that possible?!");
		if (!world.isLocked())
			world.destroyBody(body);
	}

	/** Get the body of this Box2D object */
	public Body getBody() {
		return body;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
