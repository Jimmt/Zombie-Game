package com.jumpbuttonstudios.zombiegame.screens;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jumpbuttonstudios.zombiegame.Constants;
import com.jumpbuttonstudios.zombiegame.ZombieGame;

public abstract class AbstractScreen implements Screen {
	protected ZombieGame zg;
	protected Stage stage, uiStage;
	protected OrthographicCamera cam;
	private World world;
	private Skin skin;
	private BitmapFont font;
	private TextureAtlas atlas;
	protected SpriteBatch batch;
	protected Box2DDebugRenderer debugRenderer;
	private Table table;
	protected ShapeRenderer sr;
	protected RayHandler rh;
	protected Pixmap cursor;
	protected Pixmap redCursor;
	private FPSLogger logger;
	protected InputMultiplexer multiplexer;

	public AbstractScreen(ZombieGame zg) {
		this.zg = zg;
		stage = new Stage(Constants.WIDTH, Constants.HEIGHT, true);
		uiStage = new Stage(Constants.WIDTH, Constants.HEIGHT, true);
		cam = (OrthographicCamera) stage.getCamera();
		
		batch = new SpriteBatch();

		debugRenderer = new Box2DDebugRenderer();
		sr = new ShapeRenderer();

		logger = new FPSLogger();

		

	}

	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	protected Table getTable() {
		if (table == null) {
			table = new Table(getSkin());
			table.setFillParent(true);

			stage.addActor(table);
		}
		return this.table;
	}

	protected World getWorld() {
		return world;
	}

	protected Skin getSkin() {
		if (skin == null) {
			FileHandle skinFile = Gdx.files.internal("skin/uiskin.json");
			skin = new Skin(skinFile);
		}
		return skin;
	}

	public BitmapFont getFont() {
		if (font == null) {
			font = new BitmapFont(Gdx.files.internal("skin/white.fnt"), false);
		}
		return font;
	}

	public SpriteBatch getBatch() {
		if (batch == null) {
			batch = new SpriteBatch();
		}
		return batch;
	}

	public TextureAtlas getAtlas() {
		if (atlas == null) {
			atlas = new TextureAtlas(
					Gdx.files.internal("image-atlases/pack.atlas"));
		}

		return this.atlas;
	}

	public void resize(int width, int height) {

	}

	public void render(float delta) {

		stage.act(delta);
		uiStage.act(delta);

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		stage.draw();
		uiStage.draw();

		if (rh != null)
			rh.updateAndRender();


	}

	public void hide() {

		dispose();
	}

	public void pause() {

	}

	public void resume() {

	}

	public void dispose() {

	}

}