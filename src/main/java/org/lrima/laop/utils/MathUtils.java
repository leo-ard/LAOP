package org.lrima.laop.utils;


import java.util.Arrays;
import java.util.function.Function;

/**
 * Class for java.math utilities that are not present in the java Math class
 * @author Clement Bisaillon
 */
public class MathUtils {
    public static final Function<Double, Double> LOGISTIC = (x) -> 1.0/(1.0 + Math.exp(-x));
    public static final Function<Double, Double> TANH = Math::tanh;
    private static final double FLOAT_DELTA = 0.0001;

    /**
     * Used to know if a decimal number is near zero. The precision is 0.0001.
     * @param number the number to test
     * @return true if the number is near 0, false otherwise.
     */
    public static boolean nearZero(double number){
        return nearZero(number, FLOAT_DELTA);
    }

    /**
     * Used to know if a decimal number is near zero with a certain error
     * @param number the number
     * @param error the error
     * @return true if the number is near zero, false otherwise
     */
    private static boolean nearZero(double number, double error){
        if(number < error && number > -error){
            return true;
        }
        return false;

    }

    /**
     * Checks for an intersection between two segment.
     *
     * Line1 (p0x, p0y, p1x, p1y)
     * Line2 (p2x, p2y, p3x, p3y)
     *
     * @author Léonard with source https://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
     * @param p0x line 1 point 1 x
     * @param p0y line 1 point 1 y
     * @param p1x line 1 point 2 x
     * @param p1y line 1 point 2 y
     * @param p2x line 2 point 1 x
     * @param p2y line 2 point 1 y
     * @param p3x line 2 point 2 x
     * @param p3y line 2 point 2 y
     * @return null if there is no intersection, the Vector2D containing the point if there is
     */
    public static float[] segmentIntersection(float p0x, float p0y, float p1x, float p1y, float p2x, float p2y, float p3x, float p3y){
        // Get A,B of first line - points : ps1 to pe1
        float s1x = p1x-p0x;
        float s1y = p1y-p0y;
        // Get A,B of second line - points : ps2 to pe2
        float s2x = p3x-p2x;
        float s2y = p3y-p2y;

        // Get determinant and check if the lines are parallel
        float determinant = -s2x*s1y + s1x*s2y;
        if(determinant == 0) return null;

        determinant = 1f/determinant;
        float s = ( -s1y*( p0x - p2x ) + s1x * (p0y - p2y) )* determinant;
        float t = (  s2x*( p0y - p2y ) - s2y * (p0x - p2x) )* determinant;

        if(s >= 0 && s <= 1 && t >= 0 && t <= 1){
            return new float[]{p0x + (t * s1x), p0y + (t * s1y)};
        }

        return null;
    }


    /**
     * Checks for an collision of a rectangle and a line
     *
     * Line1 (p0x, p0y, p1x, p1y)
     * Rect1 (r0x, r0y, r1x, r1y, r2x, r2y, r3x, r3y)
     *
     * @param p0x line's point 1 x
     * @param p0y line's point 1 y
     * @param p1x line's point 2 x
     * @param p1y line's point 2 y
     * @param r0x rect's point 1 x
     * @param r0y rect's point 1 y
     * @param r1x rect's point 2 x
     * @param r1y rect's point 2 y
     * @param r2x rect's point 3 x
     * @param r2y rect's point 3 y
     * @param r3x rect's point 4 x
     * @param r3y rect's point 4 y
     * @return true if the line intersects the rectangle, false otherwise
     */
    public static boolean rectSegmentIntersection(float p0x, float p0y, float p1x, float p1y, float r0x, float r0y, float r1x, float r1y, float r2x, float r2y, float r3x, float r3y){
        return segmentIntersection(p0x, p0y, p1x, p1y, r0x, r0y, r1x, r1y) != null
                || segmentIntersection(p0x, p0y, p1x, p1y, r1x, r1y, r2x, r2y) != null
                || segmentIntersection(p0x, p0y, p1x, p1y, r2x, r2y, r3x, r3y) != null
                || segmentIntersection(p0x, p0y, p1x, p1y, r3x, r3y, r0x, r0y) != null;
    }

    /**
     * Check if all the values of an array are a certain value
     * @param array the array to check
     * @param i the value to check
     * @return true if all the values of the array are i
     */
    public static boolean arrayEquals(double[] array, int i) {
        for (double captorValue : array) {
            if(captorValue != i)
                return false;
        }

        return true;
    }

    /**
     * Converts an integer array to a double array
     * @param vector the array to convert
     * @return the array with double values
     */
    public static double[] convertToDoubleArray(int[] vector) {
        double[] doubleVector = new double[vector.length];

        for (int i = 0; i < vector.length; i++) {
            doubleVector[i] = (double) vector[i];
        }

        return doubleVector;
    }
}
