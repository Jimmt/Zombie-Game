package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.jumpbuttonstudios.zombiegame.ai.state.zombie.AttackState;
import com.jumpbuttonstudios.zombiegame.character.player.Player;
import com.jumpbuttonstudios.zombiegame.character.zombie.Zombie;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.weapons.Bullet;

public class GameContactListener implements ContactListener {

	/* The level instance */
	Level level;

	public GameContactListener(Level level) {
		this.level = level;
	}

	@Override
	public void beginContact(Contact contact) {
		Object A = contact.getFixtureA().getBody().getUserData();
		Object B = contact.getFixtureB().getBody().getUserData();

		if (A instanceof Zombie && B instanceof Bullet) {
			Bullet bullet = (Bullet) B;
			Level.bullets.removeValue(bullet, true);
			Level.factory.deleteBody(bullet.getBody());
		} else if (B instanceof Bullet && A instanceof Zombie) {
			Bullet bullet = (Bullet) A;
			Level.bullets.removeValue(bullet, true);
			Level.factory.deleteBody(bullet.getBody());

		}

		if (A instanceof Zombie && B instanceof Player) {
			Zombie zombie = (Zombie) A;
			Player player = (Player) B;
			zombie.setTargetInRange(true);
			zombie.getStateMachine().changeState(new AttackState());

		} else if (B instanceof Zombie && A instanceof Player) {
			Player player = (Player) A;
			Zombie zombie = (Zombie) B;
			zombie.setTargetInRange(true);
			zombie.getStateMachine().changeState(new AttackState());

		}

	}

	@Override
	public void endContact(Contact contact) {
		if (contact.getFixtureA() == null || contact.getFixtureB() == null) {
			return;
		} else {
			Object A = contact.getFixtureA().getBody().getUserData();
			Object B = contact.getFixtureB().getBody().getUserData();

			if (A == null || B == null) {
				return;
			} else {
				if (A instanceof Zombie && B instanceof Player) {
					Zombie zombie = (Zombie) A;
					Player player = (Player) B;
					zombie.setTargetInRange(false);

				} else if (B instanceof Zombie && A instanceof Player) {
					Player player = (Player) A;
					Zombie zombie = (Zombie) B;
					zombie.setTargetInRange(false);

				}
			}
		}

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
