package com.jumpbuttonstudios.zombiegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jumpbuttonstudios.zombiegame.Player.LimbJoint;

public class LevelScreen extends AbstractScreen {
	private Player player;
	private Image backgroundLeft, backgroundMiddle, backgroundRight;
	private Ground ground;

	public LevelScreen(ZombieGame zg) {
		super(zg);

		stage.setViewport(Constants.UNIT_WIDTH, Constants.UNIT_HEIGHT, false);

		backgroundLeft = new Image(new Texture(Gdx.files.internal("Environment/BG/Left.png")));
		backgroundMiddle = new Image(new Texture(Gdx.files.internal("Environment/BG/Middle.png")));
		backgroundRight = new Image(new Texture(Gdx.files.internal("Environment/BG/Right.png")));
		backgroundLeft.setScale(Constants.scale);
		backgroundMiddle.setScale(Constants.scale);
		backgroundRight.setScale(Constants.scale);
		backgroundLeft.setX(-backgroundLeft.getDrawable().getMinWidth() * Constants.scale);
		backgroundMiddle.setX(0f);
		backgroundRight.setX(backgroundLeft.getDrawable().getMinWidth() * Constants.scale);
		stage.addActor(backgroundLeft);
		stage.addActor(backgroundMiddle);
		stage.addActor(backgroundRight);
		ground = new Ground(getWorld());
		stage.addActor(ground);

		player = new Player(getWorld());
		stage.addActor(player);

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		super.render(delta);

		cam.position.set(player.getX() + player.getWidth() / 2, player.getY() + 2.5f, 0);

	}
}
