package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.gibbo.gameutil.camera.ActionOrthoCamera;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ZombieGame;
import com.jumpbuttonstudios.zombiegame.character.Character;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint;
import com.jumpbuttonstudios.zombiegame.character.PivotJoint.Pivots;
import com.jumpbuttonstudios.zombiegame.level.Level;
import com.jumpbuttonstudios.zombiegame.weapons.Bullet;

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

	/** Table for creating defenses */
	private Table defenseTable = new Table(getSkin());

	/** Array of defense icons */
	private Array<Image> defenseImages = new Array<Image>();

	/** Array of released defense icons */
	private Array<Image> releaseImages = new Array<Image>();

	private Array<ImageButton> defenseButtons = new Array<ImageButton>();

	private String[] paths = { "Block/Released", "Door/Released",
			"Floor/Released", "Ladder/Released", "Wall/Released" };

	private String[] pressedPaths = { "Block/Pressed", "Door/Pressed",
			"Floor/Pressed", "Ladder/Pressed", "Wall/Pressed" };

	private Image grid;

	private HudTable hudTable = new HudTable(getSkin());

	private Table parentTable = new Table(getSkin());

	public LevelScreen(ZombieGame zg) {
		super(zg);

		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(uiStage);
		multiplexer.addProcessor(this);

		// adding each pressed/released image
		for (String string : paths) {
			Image i = new Image(new Texture("UI/Icons/"
					+ Gdx.files.internal(string + ".png")));
			defenseImages.add(i);
		}
		for (String string : pressedPaths) {
			Image i = new Image(new Texture("UI/Icons/"
					+ Gdx.files.internal(string + ".png")));
			releaseImages.add(i);
		}

		grid = new Image(new Texture(Gdx.files.internal("UI/Grid.png")));

		defenseTable.setFillParent(true);
		defenseTable.setVisible(false);

		defenseTable.add("Defenses");
		defenseTable.row();

		// Add pressed and released images to each imageButton
		for (int i = 0; i < defenseImages.size; i++) {
			ImageButtonStyle ibs = new ImageButtonStyle();
			ibs.imageUp = defenseImages.get(i).getDrawable();
			ibs.imageDown = releaseImages.get(i).getDrawable();
			ImageButton button = new ImageButton(ibs);
			final String str = paths[i];
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					
					
				}
			});
			defenseButtons.add(button);
			defenseTable.add(button);
		}

		defenseTable.debug();
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

		if (defenseTable.isVisible()) {
			level.getPlayer().setInMenu(true);
		} else {
			level.getPlayer().setInMenu(false);
		}

		/* Keep camera inside the level bounds */
		if (level.getPlayer().getX() > -5 && level.getPlayer().getX() < 17) {
			b2dCam.follow(delta, new Vector2(level.getPlayer().getX()
					+ level.getPlayer().getWidth() / 2, 2), 0, 2.5f);
		}

		/* Enable shake if not already */
		if (!b2dCam.shakeEnabled())
			b2dCam.enableShake(true);

		b2dCam.update();

		batch.setProjectionMatrix(b2dCam.combined);
		batch.begin();

		level.forest.draw(batch);
		level.getPlayer().draw(batch);

		for (Bullet bullet : level.bullets) {
			bullet.draw(batch);
		}

		for (Character player : level.getCharacters()) {
			player.draw(batch);
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

		}
		stage.draw();
		uiStage.draw();
		Table.drawDebug(stage);
		Table.drawDebug(uiStage);
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
