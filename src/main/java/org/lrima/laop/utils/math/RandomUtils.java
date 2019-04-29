package org.lrima.laop.utils.math;

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
	 * @return a random boolean value
	 */
	public static boolean getBoolean() {
		return ThreadLocalRandom.current().nextBoolean();
	}

	/**
	 * Get a random double between a range
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return a random double between min and max inclusively
	 */
	public static double getDouble(int min, int max) {
		return ThreadLocalRandom.current().nextDouble(min, max);
	}
}
