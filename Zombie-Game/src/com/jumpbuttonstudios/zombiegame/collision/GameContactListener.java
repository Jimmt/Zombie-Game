package com.jumpbuttonstudios.zombiegame.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.jumpbuttonstudios.zombiegame.ai.state.zombie.AttackState;
import com.jumpbuttonstudios.zombiegame.character.player.Player;
import com.jumpbuttonstudios.zombiegame.character.zombie.Zombie;
import com.jumpbuttonstudios.zombiegame.effects.blood.BloodPuddle;
import com.jumpbuttonstudios.zombiegame.effects.death.DeathEffect;
import com.jumpbuttonstudios.zombiegame.effects.death.ZombieBodyParts;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.weapons.Bullet;
import com.jumpbuttonstudios.zombiegame.weapons.drops.Drop;

/**
 * TODO this class needs properly used and correct code written, it is very
 * messy atm
 * 
 * @author Gibbo
 * 
 */
public class GameContactListener implements ContactListener {

	/* The level instance */
	Level level;

	boolean dead = false;

	public GameContactListener(Level level) {
		this.level = level;
	}

	@Override
	public void beginContact(Contact contact) {
		Object A = contact.getFixtureA().getBody().getUserData();
		Object B = contact.getFixtureB().getBody().getUserData();

		if (A instanceof Zombie && B instanceof Player) {
			Zombie zombie = (Zombie) A;
			Player player = (Player) B;

			if (!zombie.isGrabbed())
				zombie.grab(player);

			zombie.setTargetInRange(true);
			zombie.getStateMachine().changeState(new AttackState());

		} else if (B instanceof Zombie && A instanceof Player) {
			Player player = (Player) A;
			Zombie zombie = (Zombie) B;

			if (!zombie.isGrabbed())
				zombie.grab(player);

			zombie.setTargetInRange(true);
			zombie.getStateMachine().changeState(new AttackState());

		} else if (A instanceof Zombie && B instanceof Bullet) {
			Zombie zombie = (Zombie) A;
			Bullet bullet = (Bullet) B;
			if (zombie.isGrabbed())
				zombie.release(level.getPlayer());

			if (contact.getFixtureA().getUserData() instanceof Zombie) {
				zombie.hurt(bullet, true);
			} else {
				zombie.hurt(bullet, false);
			}

			level.factory.deleteBody(bullet.getBody());
			level.bullets.removeValue(bullet, true);

			level.getBloodEffects().add(new BloodPuddle(zombie));

			if (zombie.isDead()) {
				level.getDeathEffects().add(
						new DeathEffect(zombie, new ZombieBodyParts(zombie)));
				level.getCharacters().removeValue(zombie, true);
				level.factory.deleteBody(zombie.getBody());
			}
		} else if (A instanceof Bullet && B instanceof Zombie) {
			Bullet bullet = (Bullet) A;
			Zombie zombie = (Zombie) B;

			if (zombie.isGrabbed())
				zombie.release(level.getPlayer());

			if (contact.getFixtureB().getUserData() instanceof Zombie) {
				zombie.hurt(bullet, true);
			} else {
				zombie.hurt(bullet, false);
			}

			level.factory.deleteBody(bullet.getBody());
			level.bullets.removeValue(bullet, true);

			level.getBloodEffects().add(new BloodPuddle(zombie));

			if (zombie.isDead()) {
				level.getDeathEffects().add(
						new DeathEffect(zombie, new ZombieBodyParts(zombie)));
				level.getCharacters().removeValue(zombie, true);
				level.factory.deleteBody(zombie.getBody());
			}

		} else if (A instanceof Player && B instanceof Drop) {
			Player player = (Player) A;
			Drop drop = (Drop) B;
			
			drop.pickup(player);

		} else if (A instanceof Drop && B instanceof Player) {
			Drop drop = (Drop) A;
			Player player = (Player) B;
			
			drop.pickup(player);

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
		if (contact.getFixtureA() == null || contact.getFixtureB() == null) {
			return;
		} else {
			Object A = contact.getFixtureA().getBody().getUserData();
			Object B = contact.getFixtureB().getBody().getUserData();

			if (A == null || B == null) {
				return;
			} else {

			}
		}

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
