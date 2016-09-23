package game_engine.controllers;

import java.util.Comparator;
import game_engine.game_elements.Unit;

/**
 * This class is a comparator for a Bounds Priority Queue for sorting Unit bounds by maximum bounding distance.
 * Maximum bounding distance is a utility for tower placement validation to ensure no collisions would happen on the largest enemy's "maximum bounding distance."
 * 
 * @author adamtache
 *
 */

public class BoundsComparator implements Comparator<Unit>{

	@Override
	public int compare(Unit o1, Unit o2) {
		return (int) (o1.getProperties().getBounds().getMaxBoundingDistance() - o2.getProperties().getBounds().getMaxBoundingDistance());
	}

}