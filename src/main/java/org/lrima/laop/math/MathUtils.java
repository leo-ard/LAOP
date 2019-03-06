package org.lrima.laop.math;


/**
 * Class for math utilities that are not present in the java Math class
 * @author Clement Bisaillon
 */
public class MathUtils {
    private static final double floatDelta = 0.0001;


    /**
     * Used to know if a decimal number is near zero. The precision is 0.0001.
     * @param number the number to test
     * @return true if the number is near 0, false otherwise.
     */
    public static boolean nearZero(double number){
        if(Math.abs(number) < MathUtils.floatDelta){
            return true;
        }
        else{
            return false;
        }
    }

}
