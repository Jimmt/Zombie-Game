package com.jumpbuttonstudios;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jumpbuttonstudios.zombiegame.ZombieGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Zombie Game";
		cfg.useGL20 = true;
		cfg.width = 768;
		cfg.height = 432;
		
		new LwjglApplication(new ZombieGame(), cfg);
	}
}
