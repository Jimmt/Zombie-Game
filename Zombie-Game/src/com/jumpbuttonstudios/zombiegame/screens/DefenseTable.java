package com.jumpbuttonstudios.zombiegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.jumpbuttonstudios.zombiegame.defense.Defense;
import com.jumpbuttonstudios.zombiegame.defense.DefensePlacer;
import com.jumpbuttonstudios.zombiegame.level.Level;

public class DefenseTable extends Table {
	private Array<Image> defenseImages = new Array<Image>();

	private Array<Image> releaseImages = new Array<Image>();

	private Array<ImageButton> defenseButtons = new Array<ImageButton>();

	private String[] paths = { "Block", "Door", "Floor",
			"Ladder", "Wall" };
	
	

	public DefenseTable(final DefensePlacer defensePlacer, final Level level, Skin skin, final World world) {
		super(skin);

		
		for (String string : paths) {
			Image i = new Image(new Texture("UI/Icons/" + Gdx.files.internal(string + "/Released.png")));
			defenseImages.add(i);
		}
		for (String string : paths) {
			Image i = new Image(new Texture("UI/Icons/" + Gdx.files.internal(string + "/Pressed.png")));
			releaseImages.add(i);
		}

		setFillParent(true);
		setVisible(false);

		add("Defenses");
		row();

		for (int i = 0; i < defenseImages.size; i++) {
			ImageButtonStyle ibs = new ImageButtonStyle();
			ibs.imageUp = defenseImages.get(i).getDrawable();
			ibs.imageDown = releaseImages.get(i).getDrawable();
			ImageButton button = new ImageButton(ibs);

			button.addListener(new ClickListener() {
				
				private Defense tmp;
				
				@Override
				public void clicked(InputEvent event, float x, float y) {
					tmp = new Defense(world, new Vector2(0, 2), 52f, 160f, "Environment/Defense/Wall/Icon.png");
					defensePlacer.setDefense(tmp);
					defensePlacer.setPlacing(true);
					level.getDefenses().add(tmp);
//					Class c = null;
//					try {
//						c = ClassReflection.forName("com.jumpbuttonstudios.zombiegame.defense" + str);
//					} catch (ReflectionException e) {
//						e.printStackTrace();
//					}
//					Constructor cst = ClassReflection.getConstructors(c)[0];
				}
			});
			defenseButtons.add(button);
			add(button);
		}

		debug();
	}
}
