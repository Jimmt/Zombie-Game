package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jumpbuttonstudios.zombiegame.character.Arm;

public class HudTable extends Table {
	private Heart[] hearts = new Heart[3];
	private Image weapon, tmp, bullet;
	private Arm arm;
	private Label bulletCount;
	private int max;

	public HudTable(Skin skin) {
		super(skin);
		
		tmp = new Image();
		bullet = new Image(new Texture("UI/Icons/Bullet.png"));
		weapon = new Image();
		
		bulletCount = new Label("x", skin);
		
		setFillParent(true);

		for (int i = 0; i < hearts.length; i++) {
			Heart h = new Heart();
			hearts[i] = h;

			add(h);
		}
		
		add(weapon).expandX().right();
		add(bullet);
		add(bulletCount);
		row();
		add("").colspan(4).expand().fill();

	}
	
	@Override
	public void act(float delta){
		
	}
	
	public void setWeapon(Arm arm){
		this.arm = arm;
		tmp = new Image(arm.getIcon().getTexture());
		this.weapon.setDrawable(tmp.getDrawable());
		
		max = arm.getWeapon().getMagazine().getCapacity();
		bulletCount.setText(" x" + max);
		
	}
	
	public Heart[] getHearts() {
		return hearts;
	}
}