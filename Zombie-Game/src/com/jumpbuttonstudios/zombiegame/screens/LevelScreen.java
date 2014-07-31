package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
import com.jumpbuttonstudios.zombiegame.defense.Door;
import com.jumpbuttonstudios.zombiegame.effects.Effect;
import com.jumpbuttonstudios.zombiegame.effects.blood.Blood;
import com.jumpbuttonstudios.zombiegame.effects.death.DeathEffect;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.ui.ConfirmDefensePopup;
import com.jumpbuttonstudios.zombiegame.ui.DefenseTable;
import com.jumpbuttonstudios.zombiegame.ui.HudTable;
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
	private DefenseTable defenseTable = new DefenseTable(defensePlacer, level, getSkin(),
			level.getWorld());

	private Image grid;

	/** Player's arm, needed for weapon UI */
	private Arm arm;

	/** Popup for confirming defense building */
	private ConfirmDefensePopup defensePopup = new ConfirmDefensePopup("", getSkin(), level);

	/** Heads up display table, contains health UI and weapon UI */
	private HudTable hudTable = new HudTable(getSkin(), defensePopup, defenseTable);

	/** Parent table, in case anything else needs to be added */
	private Table parentTable = new Table(getSkin());

	/** For comparing defenses x/y for rendering order in array */
	private DefenseComparator defenseComp;

	/** Vector3 for projecting doors */
	private Vector3 temp;
	
	/** temporary vector */
	private Vector2 temp2;

	/** Door mouse is overlapping */
	private Door overlapDoor;

	

	public LevelScreen(ZombieGame zg) {
		super(zg);

		defenseComp = new DefenseComparator();

		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(uiStage);
		multiplexer.addProcessor(this);
		multiplexer.addProcessor(level.getPlayer());
		multiplexer.addProcessor(stage);


		uiStage.addActor(defenseTable);
		defensePopup.show(uiStage);
		defensePopup.hide();

		parentTable.setFillParent(true);
		parentTable.add(hudTable).expand().fill();

		stage.addActor(parentTable);

		temp = new Vector3();

		level.getPlayer().setHealth(3f);
		
		temp2 = new Vector2();
		

	

	}

	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(multiplexer);

		cursor = new Pixmap(Gdx.files.internal("UI/Cursors/potGreen.png"));
		redCursor = new Pixmap(Gdx.files.internal("UI/Cursors/potRed.png"));
		Gdx.input.setCursorImage(cursor, 32, 32);
		
		/* Enable camera shake if not already */
		if (!b2dCam.shakeEnabled())
			b2dCam.enableShake(true);

	}

	@Override
	public void render(float delta) {
		super.render(delta);		


		level.update(delta);

		defensePlacer.update(delta);
		
		hudTable.setScore(level.getScore());
		hudTable.setCash(level.getCash());
		
		
		

		arm = level.getPlayer().getArm();
		hudTable.setWeapon(arm);

		if (level.getPlayer().getHealth() == 0) {
			zg.setScreen(new GameOverScreen(zg));
		}

		if (defensePlacer.isPlacing()) {
			defenseTable.setVisible(false);
		}

		/*
		 * Put player in menu so player can't fire gun in defense placement
		 * time, also show the "done" button
		 */
		if (level.getDefensePlacing()) {
			hudTable.setDoneButtonVisibility(true);
			level.getPlayer().setInMenu(true);
			Gdx.input.setInputProcessor(multiplexer);

		} else {
			level.getPlayer().setInMenu(false);
		}

		for (int i = 0; i < hudTable.getHearts().length - level.getPlayer().getHealth(); i++) {
			hudTable.getHearts()[hudTable.getHearts().length - 1 - i].setEmpty(true);
		}

		if (defenseTable.isVisible()) {
			level.getPlayer().setInMenu(true);
			
			for (int i = 0; i < defenseTable.getDefenseButtons().size; i++) {

				if(level.getCash() < defenseTable.getCosts()[i]){
					defenseTable.getDefenseButtons().get(i).setDisabled(false);
				} else {	
					defenseTable.getDefenseButtons().get(i).setDisabled(false);
				}
			}
		} else if (!defensePlacer.isPlacing() && !Gdx.input.isButtonPressed(Buttons.LEFT)) {

		}

		b2dCam.update();
		
		/* Keep camera inside the level bounds */
		if (level.getPlayer().getX() > -5 && level.getPlayer().getX() < 17) {
			b2dCam.follow(level.getPlayer().getX()
					+ level.getPlayer().getWidth() / 2, 2, 0, 2.5f);
		}



		batch.setProjectionMatrix(b2dCam.combined);
		batch.begin();

		level.forest.draw(batch);
		level.getPlayer().draw(batch);

		level.getDefenses().sort(defenseComp);

		level.getBrain().draw(batch);
		
		for (Defense defense : level.getDefenses()) {
			defense.draw(batch);
		}

		for (Blood blood : level.getBloodEffects()) {
			blood.draw(batch);
		}

		for (Drop<?> drop : level.getDrops()) {
			drop.draw(batch);
		}

		for (Bullet bullet : level.bullets) {
			bullet.draw(batch);
		}

		for (Character character : level.getCharacters()) {
			character.draw(batch);
		}

		for (Effect effect : level.getEffects()) {
			effect.draw(batch);
			if (effect.finished()) {
				level.getEffects().removeValue(effect, true);
			}
		}

		for (DeathEffect deathEffect : level.getDeathEffects()) {
			if (deathEffect.isCreated())
				deathEffect.draw(batch);
		}

		batch.end();

		if (ZombieGame.DEBUG) {
			debugRenderer.render(level.getWorld(), b2dCam.combined);
			sr.setProjectionMatrix(b2dCam.combined);
			sr.begin(ShapeType.Point);
			sr.setColor(MathUtils.random(0, 1), MathUtils.random(0, 1), MathUtils.random(0, 1), 0);
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

			hudTable.debug();
			defenseTable.debug();
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

		/* If E is pressed and if it is defense placing time */
		if (keycode == Keys.E && level.getDefensePlacing()) {
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
		if (button == Buttons.RIGHT) {

			for (Defense defense : level.getDefenses()) {

				/** Check if mouse overlaps with any doors */
				if (defense instanceof Door) {
					temp.set(screenX, screenY, 0);
					b2dCam.unproject(temp);

					if (temp.x > defense.getSprite().getX()
							&& temp.x < defense.getSprite().getX() + defense.getSprite().getWidth()) {
						if (temp.y > defense.getSprite().getY()
								&& temp.y < defense.getSprite().getY()
										+ defense.getSprite().getHeight()) {
							/** Operate the door; open if closed and vice versa */
							((Door) defense).operate();

						}
					} else {

					}
				}
			}
		}
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
