package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gibbo.gameutil.camera.ActionOrthoCamera;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ZombieGame;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint.Pivots;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.weapons.Bullet;

public class LevelScreen extends AbstractScreen {

	/** The level currently running */
	private Level level = new Level();

	/** The Camera for Box2D */

	public static ActionOrthoCamera b2dCam = new ActionOrthoCamera(16, 9);

	public LevelScreen(ZombieGame zg) {
		super(zg);

	}
	

	@Override
	public void render(float delta) {
		super.render(delta);

		level.update(delta);

		b2dCam.follow(delta,
				new Vector2(level.player.getX() + level.player.getWidth() / 2,
						level.player.getY()), 0, 2.5f);
		if (!b2dCam.shakeEnabled())
			b2dCam.enableShake(true);

		b2dCam.update();

		batch.setProjectionMatrix(b2dCam.combined);
		batch.begin();

		level.forest.draw(batch);
		level.player.draw(batch);

		for (Bullet bullet : Level.bullets) {
			bullet.draw(batch);
		}

		batch.end();

		debugRenderer.render(Level.world, b2dCam.combined);

		sr.setProjectionMatrix(b2dCam.combined);
		sr.begin(ShapeType.Point);
		sr.setColor(MathUtils.random(0, 1), MathUtils.random(0, 1),
				MathUtils.random(0, 1), 0);
		for (PivotJoint pivot : Pivots.getPivotJoints().values()) {
			sr.point(pivot.x, pivot.y, 0);
		}

		sr.end();

		sr.begin(ShapeType.Line);
		for (PivotJoint pivot : Pivots.getPivotJoints().values()) {
			sr.circle(pivot.x, pivot.y, 0.15f, 20);
			for (int x = -1; x < 2; x += 2){
				sr.line(pivot.x, pivot.y, pivot.x + x, pivot.y);				
			}
			for (int y = -1; y < 2; y += 2){
				sr.line(pivot.x, pivot.y, pivot.x, pivot.y + y);				
				
			}
		}

		sr.end();

	}
	
	@Override
	public void resize(int width, int height) {
		Constants.set(width, height);
	}
}
