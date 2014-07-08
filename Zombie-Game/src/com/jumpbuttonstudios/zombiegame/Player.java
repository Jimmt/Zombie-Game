package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudios.zombiegame.ai.StateMachine;
import com.jumpbuttonstudios.zombiegame.ai.state.Idle;

public class Player extends Image {
	private World world;
	private Body body;
	private Array<TextureRegionDrawable> drawables;
	private float width, height;

	/** The arm closest to the user */
	private LimbJoint frontArm;
	/** The arm furthers from the user */
	private LimbJoint backArm;

	/** Box2DObject */
	private Box2DObject b2d;
	
	/* State machine code */
	StateMachine stateMachine = new StateMachine(this);
	
	/** Speed the player moves */
	private float speed = 5;

	public Player(World world) {
		super(new Texture(Gdx.files.internal("still.png")));
		this.world = world;
		
		drawables = new Array<TextureRegionDrawable>();

		width = getDrawable().getMinWidth() * Constants.scale;
		height = getDrawable().getMinHeight() * Constants.scale;

		b2d = new Box2DObject();

		b2d.createBody(world, BodyType.DynamicBody, new Vector2(0, 2), false);
		b2d.createPolyFixture(width / 2, height / 2, 0, 0, 0, false);

		setSize(width, height);
		
		setOrigin(width / 2, height / 2);
		
		/** Create limbs */
		frontArm = new LimbJoint(this, 1 * Constants.scale, 50 * Constants.scale,  "Sprites/Characters/Male/BodyParts/Arms/Front/straight.png");
		backArm = new LimbJoint(this, 45 * Constants.scale, 85 * Constants.scale, "Guns/M1911/WithArm.png");
 		
		stateMachine.setDefaultState(Idle.instance());
		
	
	}
	
	public Array<TextureRegionDrawable> getDrawables(){
		drawables.clear();
		drawables.add((TextureRegionDrawable) getDrawable());
		drawables.add((TextureRegionDrawable) frontArm.getDrawable());
		drawables.add((TextureRegionDrawable) backArm.getDrawable());
		return drawables;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		backArm.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
		frontArm.draw(batch, parentAlpha);
		
	}
	

	@Override
	public void act(float delta) {
		super.act(delta);
		

		setPosition(b2d.getBody().getPosition().x - width / 2, b2d.getBody()
				.getPosition().y - height / 2);
		
		frontArm.act(delta);
		backArm.act(delta);
		
		stateMachine.update(this);
		

	}
	
	public LimbJoint getFrontArm() {
		return frontArm;
	}
	
	public LimbJoint getBackArm() {
		return backArm;
	}
	
	public void setFrontArm(LimbJoint frontArm) {
		this.frontArm = frontArm;
	}
	
	public void setBackArm(LimbJoint backArm) {
		this.backArm = backArm;
	}
	
	public StateMachine getStateMachine() {
		return stateMachine;
	}
	
	public Box2DObject getB2d() {
		return b2d;
	}

	public float getSpeed() {
		return speed;
	}
	
	/**
	 * Simple limb anchor class
	 * 
	 * @author JBS
	 * 
	 */
	public class LimbJoint extends Image {
		
		/** The parent of this limb */
		private Image parent;

		/** The position at which the limb is anchored */
		private Vector2 position = new Vector2();
		
		private float width, height;
		

		/**
		 * Creates a new joint or "anchor" point for a limb to be placed
		 * 
		 * @param x
		 *            the x coordinate on the sprite this limb is anchored to
		 * @param y
		 *            the y coordinate on the sprite this limb is anchored to
		 * @param path
		 *            internal path to assets folder
		 */
		public LimbJoint(Image parent, float x, float y, String path) {
			super(new Texture(Gdx.files.internal(path)));
			position.set(x, y);
			this.parent = parent;
			
			width = getDrawable().getMinWidth() * Constants.scale;
			height = getDrawable().getMinHeight() * Constants.scale;
			setSize(width, height);

		}
		
		@Override
		public void act(float delta) {
			super.act(delta);
			
			setPosition(parent.getX() + position.x, parent.getY() + position.y);
			
			
		}
		
		public Vector2 getPosition() {
			return position;
		}
	}

}
