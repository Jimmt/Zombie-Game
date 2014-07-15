package com.jumpbuttonstudios.zombiegame.screens;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
import com.jumpbuttonstudios.zombiegame.defense.DefenseDimensions;
import com.jumpbuttonstudios.zombiegame.defense.DefensePlacer;
import com.jumpbuttonstudios.zombiegame.defense.Door;
import com.jumpbuttonstudios.zombiegame.level.Level;

/**
 * @author Jimmt
 */

public class DefenseTable extends Table {
	private Array<Image> defenseImages = new Array<Image>();

	private Array<Image> releaseImages = new Array<Image>();

	private Array<ImageButton> defenseButtons = new Array<ImageButton>();

	private String[] paths = { "Block", "Door", "Floor", "Ladder", "Wall" };

	public DefenseTable(final DefensePlacer defensePlacer, final Level level, Skin skin,
			final World world) {
		super(skin);

		// loading the images that are released buttons (normal)
		for (String string : paths) {
			Image i = new Image(new Texture("UI/Icons/"
					+ Gdx.files.internal(string + "/Released.png")));
			defenseImages.add(i);
		}

		// loading the images that are pressed icons
		for (String string : paths) {
			Image i = new Image(new Texture("UI/Icons/"
					+ Gdx.files.internal(string + "/Pressed.png")));
			releaseImages.add(i);
		}

		// for proper sizing/positioning
		setFillParent(true);
		setVisible(false);

		add("Defenses");
		row();

		// add the released/pressed image for each ImageButton
		for (int i = 0; i < defenseImages.size; i++) {
			ImageButtonStyle ibs = new ImageButtonStyle();
			ibs.imageUp = defenseImages.get(i).getDrawable();
			ibs.imageDown = releaseImages.get(i).getDrawable();
			ImageButton button = new ImageButton(ibs);

			// name of defense
			final String str = paths[i];

			button.addListener(new ClickListener() {
				private Class e, sub;
				private Defense tmp;
				private Method method;
				private float width, height;
				private int index;
				private Float fl;
				private Vector2 tmpv;

				@Override
				public void clicked(InputEvent event, float x, float y) {

					/**
					 * There are specific use cases/classes for the ladder and
					 * door
					 */

					if (!str.equals("Ladder") && !str.equals("Door")) {
						e = null;
						try {
							e = ClassReflection
									.forName("com.jumpbuttonstudios.zombiegame.defense.DefenseDimensions");
						} catch (ReflectionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for (int i = 0; i < DefenseDimensions.getCache().length; i++) {

							if (str.toUpperCase()
									.equals(DefenseDimensions.getCache()[i].toString())) {
								index = i;
							}
						}
						sub = e.getEnumConstants()[index].getClass();

						try {
							method = sub.getDeclaredMethod("getWidth");
							fl = (Float) method.invoke(e.getEnumConstants()[index]);
							width = fl;
							method = sub.getDeclaredMethod("getHeight");
							fl = (Float) method.invoke(e.getEnumConstants()[index]);
							height = fl;
						} catch (Exception ex) {

						}

						// load specific defense images for each defense
						tmp = new Defense(world, 0, 0, width, height, "Environment/Defense/" + str
								+ "/Icon.png");

					} else if (str.equals("Door")) {
						tmp = new Door(world, 0, 0, DefenseDimensions.DOOR.getWidth(),
								DefenseDimensions.DOOR.getHeight(),
								"Environment/Defense/Door/Icon.png");
						
					} else {

					}

					defensePlacer.setDefense(tmp);
					defensePlacer.setPlacing(true);
					level.getDefenses().add(tmp);
				}
			});
			defenseButtons.add(button);
			add(button);
		}

		debug();
	}
}
