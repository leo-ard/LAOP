package org.lrima.laop.utils.math;

import java.awt.geom.Point2D;
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
	 * @param xMin the minimum value of x
	 * @param xMax the maximum value of x
	 * @param yMin the minimum value of y
	 * @param yMax the maximum value of y
	 * @return a random point
	 */
	public static Point2D.Double getPoint(int xMin, int xMax, int yMin, int yMax){
		int x = getInteger(xMin, xMax);
		int y = getInteger(yMin, yMax);
		return new Point2D.Double(x, y);
	}
}
