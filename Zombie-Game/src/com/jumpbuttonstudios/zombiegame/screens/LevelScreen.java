package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.math.Vector2;
import com.gibbo.gameutil.camera.ActionOrthoCamera;
import com.jumpbuttonstudios.zombiegame.Level;
import com.jumpbuttonstudios.zombiegame.ZombieGame;

public class LevelScreen extends AbstractScreen {

	/** The level currently running */
	private Level level = new Level();

	/** The Camera for Box2D */
	ActionOrthoCamera b2dCam = new ActionOrthoCamera(16, 9);

	public LevelScreen(ZombieGame zg) {
		super(zg);

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		super.render(delta);

		level.update(delta);

		b2dCam.follow(delta,
				new Vector2(level.player.getX() + level.player.getWidth() / 2,
						level.player.getY()), 0, 2.5f);

		b2dCam.update();

		batch.setProjectionMatrix(b2dCam.combined);
		batch.begin();

		level.forest.draw(batch);		
		level.player.draw(batch);

		batch.end();

		debugRenderer.render(Level.world, b2dCam.combined);

	}
}
