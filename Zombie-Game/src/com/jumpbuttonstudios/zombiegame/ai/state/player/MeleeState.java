package com.jumpbuttonstudios.zombiegame.ai.state.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.gibbo.gameutil.ai.state.State;
import com.jumpbuttonstudios.zombiegame.character.player.Player;

public class MeleeState implements State<Player> {
	
	private static MeleeState instance = new MeleeState();

	@Override
	public void enter(Player player) {
		
		player.setCurrentAnimation("melee heavy");
		
		player.getCurrentAnimation().getAnimation().setPlayMode(Animation.LOOP);
		
		player.getCurrentAnimation().play();
		
	}

	@Override
	public void execute(Player player) {
		player.getArm().getWeapon().getSprite().setAlpha(0.0f);
		
		if (player.getAnimation("melee heavy").getAnimatedSprite().isAnimationFinished()) {
		player.getStateMachine().changeState(IdlePlayerState.instance());
		
		}
		
	}

	@Override
	public void exit(Player player) {
		player.getCurrentAnimation().stop();
		player.getArm().getWeapon().getSprite().setAlpha(1.0f);
		
	}
	
	public static MeleeState instance() {
		return instance;
	}

}
