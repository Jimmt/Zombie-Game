package com.jumpbuttonstudios.zombiegame.character.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpbuttonstudios.zombiegame.AnimationBuilder;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ai.state.Idle;
import com.jumpbuttonstudios.zombiegame.ai.state.Jumping;
import com.jumpbuttonstudios.zombiegame.character.Arms;
import com.jumpbuttonstudios.zombiegame.character.Arms.ArmsBuilder;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.weapons.Pistol;
import com.jumpbuttonstudios.zombiegame.weapons.Weapon;

public class Player extends Character {
	
	Arms ak;
	Arms pistol;
	
	Vector2 mouse;

	public Player(World world) {
		this.world = world;
		
		mouse = new Vector2();

		/* Create animations */
		/* Idle animation */
		Vector2 tmp = addAnimation(AnimationBuilder.createb2d(1, 1, 1,
				Constants.scale, Constants.scale, "still.png", null), "idle");
		/* Running animation */
		addAnimation(AnimationBuilder.createb2d(0.06f, 2, 6, Constants.scale,
				Constants.scale, "Sprites/Characters/Male/Run/WithoutArms.png",
				new int[] { 11 }), "running");
		/* Jump animation */
		addAnimation(
				AnimationBuilder.createb2d(1, 1, 2, Constants.scale, Constants.scale,
						"Sprites/Characters/Male/Jump/WithoutArms.png", null),
				"jumping");

		/* Setup the width and height from our animations sprites */
		width = tmp.x * Constants.scale;
		height = tmp.y * Constants.scale;

		/* Setup Box2D stuff */
		createBody(world, BodyType.DynamicBody, new Vector2(0, 2), true);
		createPolyFixture(width / 2, height / 2, 0.25f, 0.70f, 0.05f, false);

		/* Character starts facing right */
		setFacing(Facing.RIGHT);
		/* Set the current animation */
		setCurrentAnimation("idle");

		/** Create arms */
		pistol = ArmsBuilder
				.create(this,
						-28 * Constants.scale,
						4 * Constants.scale,
						new Pistol("Guns/M1911/WithArm.png", this),
						-28 * Constants.scale,
						-4 * Constants.scale,
						new Sprite(
								new Texture(
										Gdx.files
												.internal("Sprites/Characters/Male/BodyParts/Arms/Back/Bent.png"))));
		
		ak = ArmsBuilder
				.create(this,
						-28 * Constants.scale,
						-16 * Constants.scale,
						new Weapon("Guns/AK74u/WithArm.png", this),
						-20 * Constants.scale,
						-4 * Constants.scale,
						new Sprite(
								new Texture(
										Gdx.files
												.internal("Sprites/Characters/Male/BodyParts/Arms/Back/Bent.png"))));

		arms = pistol;
		
		/* Setup state machine */
		stateMachine.setDefaultState(Idle.instance());
		stateMachine.changeState(Idle.instance());

		/* Setup character properties */
		maxSpeed = 8;
		acceleration = 10;
		jumpPower = 200;

	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (Gdx.input.isKeyPressed(Keys.P)) {
			arms = ak;
		} else if (Gdx.input.isKeyPressed(Keys.O)) {
			arms = pistol;
		}
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT)){
			if(arms.getWeapon() != null){
				arms.getWeapon().fire(arms.getDirection());	
			}
				
		}
		
		arms.update(delta);

	}

	@Override
	public void draw(SpriteBatch batch) {

		if (arms != null) {
			arms.drawBack(batch);
		}

		if (Gdx.input.isKeyPressed(Keys.SPACE) && isGrounded())
			getStateMachine().changeState(Jumping.instance());

		if (currentAnimation.equals(getAnimation("running"))) {
			if (getBody().getLinearVelocity().x < 0) {
				currentAnimation.draw(batch, getBody().getPosition().x - 0.49f,
						getBody().getPosition().y, width, height,
						body.getAngle() * MathUtils.radDeg);
			} else {
				currentAnimation.draw(batch, getBody().getPosition().x - 0.39f,
						getBody().getPosition().y, width, height,
						body.getAngle() * MathUtils.radDeg);

			}

		} else {
			super.draw(batch);

		}
		
		arms.getWeapon().draw(batch);

		if (arms != null)
			arms.drawFront(batch);
		
		mouse.set(Gdx.input.getX(), Constants.HEIGHT - Gdx.input.getY());
		mouse.scl(Constants.scale);
		arms.rotateTowards(mouse);

	}

}
