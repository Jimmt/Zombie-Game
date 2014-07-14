package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gibbo.gameutil.camera.ActionOrthoCamera;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ZombieGame;
import com.jumpbuttonstudios.zombiegame.character.Arm;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint.Pivots;
import com.jumpbuttonstudios.zombiegame.defense.Defense;
import com.jumpbuttonstudios.zombiegame.defense.DefenseComparator;
import com.jumpbuttonstudios.zombiegame.defense.DefensePlacer;
import com.jumpbuttonstudios.zombiegame.effects.Effect;
import com.jumpbuttonstudios.zombiegame.effects.blood.Blood;
import com.jumpbuttonstudios.zombiegame.effects.zombiedeath.DeathEffect;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.weapons.Bullet;
import com.jumpbuttonstudios.zombiegame.weapons.drops.Drop;

/**
 * TODO Clean up this god awful class
 * 
 * @author Gibbo
 * @author Jimmt
 */
public class LevelScreen extends AbstractScreen implements InputProcessor {

	/** The level currently running */
	private Level level = new Level();

	/** The Camera for Box2D */
	public static ActionOrthoCamera b2dCam = new ActionOrthoCamera(16, 9);
	
	private DefensePlacer defensePlacer = new DefensePlacer(level);

	/** Table for creating defenses */
	private DefenseTable defenseTable = new DefenseTable(defensePlacer, level, getSkin(), level.getWorld());

	private Image grid;
	
	private Arm arm;

	private HudTable hudTable = new HudTable(getSkin());

	private Table parentTable = new Table(getSkin());
	
	private DefenseComparator defenseComp;
	
	
	
	

	public LevelScreen(ZombieGame zg) {
		super(zg);
		
		defenseComp = new DefenseComparator();

		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(uiStage);
		multiplexer.addProcessor(this);

		
		hudTable.debug();
		
	

		uiStage.addActor(defenseTable);

		parentTable.setFillParent(true);
		parentTable.add(hudTable).expand().fill();

		stage.addActor(parentTable);
		
	}

	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		level.update(delta);
		
		defensePlacer.update(delta);
		
		arm = level.getPlayer().getArm();
		hudTable.setWeapon(arm);
		
		if(defensePlacer.isPlacing()){
			defenseTable.setVisible(false);
		}

		
		if (defenseTable.isVisible()) {
			level.getPlayer().setInMenu(true);
		} else if(!defensePlacer.isPlacing() && !Gdx.input.isButtonPressed(Buttons.LEFT)){
			level.getPlayer().setInMenu(false);
		}
		
		

		/* Keep camera inside the level bounds */
		if (level.getPlayer().getX() > -5 && level.getPlayer().getX() < 17) {
			b2dCam.follow(delta, new Vector2(level.getPlayer().getX()
					+ level.getPlayer().getWidth() / 2, 2), 0, 2.5f);
		}

		if (!b2dCam.shakeEnabled())
			b2dCam.enableShake(true);

		b2dCam.update();

		batch.setProjectionMatrix(b2dCam.combined);
		batch.begin();

		level.forest.draw(batch);
		level.getPlayer().draw(batch);
		
		level.getDefenses().sort(defenseComp);
		
		for(Defense defense : level.getDefenses()){
			defense.draw(batch);
		}
		
		
		for(Blood blood : level.getBloodEffects()){
			blood.draw(batch);
		}
		
		for(Drop drop : level.getDrops()){
			drop.draw(batch);
		}

		for (Bullet bullet : level.bullets) {
			bullet.draw(batch);
		}

		for (Character player : level.getCharacters()) {
			player.draw(batch);
		}

		for (Effect effect : level.getEffects()) {
			effect.draw(batch);
			if(effect.finished()){
				level.getEffects().removeValue(effect, true);
			}
		}
		
		for(DeathEffect deathEffect : level.getDeathEffects()){
			if(deathEffect.isCreated())
				deathEffect.draw(batch);
		}

		batch.end();

		if (ZombieGame.DEBUG) {
			debugRenderer.render(level.getWorld(), b2dCam.combined);
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
				for (int x = -1; x < 2; x += 2) {
					sr.line(pivot.x, pivot.y, pivot.x + x, pivot.y);
				}
				for (int y = -1; y < 2; y += 2) {
					sr.line(pivot.x, pivot.y, pivot.x, pivot.y + y);

				}
			}

			sr.end();

			Table.drawDebug(stage);
			Table.drawDebug(uiStage);
		}

		stage.draw();
		uiStage.draw();

	}

	@Override
	public void resize(int width, int height) {
		Constants.set(width, height);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.E) {
			defenseTable.setVisible(!defenseTable.isVisible());
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
