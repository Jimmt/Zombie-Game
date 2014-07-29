package com.jumpbuttonstudios.zombiegame.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudios.zombiegame.character.Arm;

/**
 * @author Jimmt
 */

public class HudTable extends Table {
	private Heart[] hearts = new Heart[3];
	private Image weapon, tmp, bullet;
	private Arm arm;
	private Label bulletCount;
	private int max, score, cash;
	private TextButton done;
	private Label scoreLabel;
	private ConfirmDefensePopup defensePopup;

	public HudTable(Skin skin, final ConfirmDefensePopup dp, final DefenseTable defenseTable) {
		super(skin);

		this.defensePopup = dp;

		tmp = new Image();
		bullet = new Image(new Texture("UI/Icons/Bullet.png"));
		weapon = new Image();

		bulletCount = new Label("x", skin);

		setFillParent(true);

		/** add the hearts ui */
		for (int i = 0; i < hearts.length; i++) {
			Heart h = new Heart();
			hearts[i] = h;

			add(h);
		}

		/**
		 * add the weapon sprite, make the cell fill all the horizontal space
		 * that's not occupied by hearts, then align weapon sprite to
		 * the right of the cell
		 */
		scoreLabel = new Label(String.valueOf(score), skin);
		add(scoreLabel).expandX();

		add(weapon).expandX().right();
		add(bullet);
		bullet.setScale(2.0f);
		add(bulletCount).width(bulletCount.getWidth() * 3);
		row();

		/** Spacing hack, spans 4 columns */
		add("").colspan(4).expand().fill();
		
		row();
		done = new TextButton("Done", skin);
		add(done).colspan(8);
		done.setVisible(false);
		done.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				done.setVisible(false);
				defenseTable.setVisible(false);
				dp.show(getStage());
			}
		});
	}

	public void setDoneButtonVisibility(boolean visible) {
		done.setVisible(visible);
	}

	@Override
	public void act(float delta) {
		scoreLabel.setText(String.valueOf(score) + "\n$" + cash);
		
		if (defensePopup.getHideButton()) {
			done.setVisible(false);
		} else {
			done.setVisible(true);
		}
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public void setCash(int cash){
		this.cash = cash;
	}
	

	public void setWeapon(Arm arm) {
		if (arm.getWeapon() == null)
			return;
		this.arm = arm;
		tmp = new Image(arm.getWeapon().getIcon().getTexture());
		this.weapon.setDrawable(tmp.getDrawable());

		max = arm.getWeapon().getMagazine().getCapacity();
		bulletCount.setText(" x" + max);

	}

	public Heart[] getHearts() {
		return hearts;
	}
}
