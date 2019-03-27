package org.lrima.laop.utils.math;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class to get random numbers
 * @author Clement Bisaillon
 */
public class RandomUtils {

	/**
	 * @param min the minimum integer
	 * @param max the maximum integer
	 * @return a random integer between min and max inclusively
	 */
	public static int getInteger(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	/**
	 * @param bounds the bounds of the map
	 * @return a random point inside bounds
	 */
	public static Point2D.Double getPoint(Rectangle2D bounds){
		int x = getInteger((int)bounds.getMinX(), (int)bounds.getMaxX());
		int y = getInteger((int)bounds.getMinY(), (int)bounds.getMaxY());
		return new Point2D.Double(x, y);
	}
	
	/**
	 * @return a random boolean value
	 */
	public static boolean getBoolean() {
		return ThreadLocalRandom.current().nextBoolean();
	}
}
