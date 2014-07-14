package com.jumpbuttonstudios.zombiegame.defense;

import java.util.Comparator;

public class DefenseComparator implements Comparator<Defense> {

	@Override
	public int compare(Defense a, Defense b) {
		if (a.getSprite().getX() < b.getSprite().getX()) {
			return -1;
		} else if (a.getSprite().getX() > b.getSprite().getX()) {
			return 1;
		} else if (a.getSprite().getX() == b.getSprite().getX()
				&& a.getSprite().getY() < b.getSprite().getY()) {

			return -1;

		} else {
			return 1;
		}

	}

}
