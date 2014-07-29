package com.jumpbuttonstudios.zombiegame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudios.zombiegame.level.Level;

public class ConfirmDefensePopup extends Dialog {
	private Image background, tmp;
	private Texture tex;
	private ImageButton yes, no;
	private boolean hideButton = true;

	public ConfirmDefensePopup(String title, Skin skin, final Level level) {
		super(title, skin);

		debug();
		
		this.
		
		background = new Image(new Texture(Gdx.files.internal("UI/Windows/Confirm_modified.png")));
		this.setBackground(background.getDrawable());

		ImageButtonStyle style = new ImageButtonStyle();
		tmp = new Image(tex = new Texture(Gdx.files.internal("UI/Buttons/Yes/Released.png")));
		style.imageUp = tmp.getDrawable();
		tmp = new Image(tex = new Texture(Gdx.files.internal("UI/Buttons/Yes/Pressed.png")));
		style.imageDown = tmp.getDrawable();
		tmp = new Image(tex = new Texture(Gdx.files.internal("UI/Buttons/Yes/Highlighted.png")));
		style.over = tmp.getDrawable();
		yes = new ImageButton(style);
		yes.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				level.exitDefensePlacing();	
				
				/* Hide the "done" button, the check occurs in HudTable */
				hideButton = true;
			}
		});
		this.button(yes);

		ImageButtonStyle style1 = new ImageButtonStyle();
		tmp = new Image(tex = new Texture(Gdx.files.internal("UI/Buttons/No/Released.png")));
		style1.imageUp = tmp.getDrawable();
		tmp = new Image(tex = new Texture(Gdx.files.internal("UI/Buttons/No/Pressed.png")));
		style1.imageDown = tmp.getDrawable();
		tmp = new Image(tex = new Texture(Gdx.files.internal("UI/Buttons/No/Highlighted.png")));
		style1.over = tmp.getDrawable();
		no = new ImageButton(style1);
		no.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				/* Show the "done" button (check occurs in HudTable) */
				
				hideButton = false;
			}
		});

		this.button(no);

	}
	
	/* Whether to hide HudTable's Done button or not */
	public boolean getHideButton(){
		return hideButton;
	}

}
