package com.jumpbuttonstudios.zombiegame.defense;

import java.util.Comparator;

/**
 * @author Jimmt
 */

public class DefenseComparator implements Comparator<Defense> {

	/** If -1 is returned, a < b, if 0, a = b, if 1, a > b.
	 * Used to sort libgdx Array of defenses - lesser x and lesser y are rendered first (earliest in array)*/
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
