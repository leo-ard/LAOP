package org.lrima.laop.utils;


import org.lrima.laop.utils.math.Vector2d;

import java.util.function.Function;

/**
 * Class for java.math utilities that are not present in the java Math class
 * @author Clement Bisaillon
 */
public class MathUtils {
    public static final Function<Double, Double> LOGISTIC = (x) -> 1.0/(1.0 + Math.exp(-x));
    private static final double FLOAT_DELTA = 0.0001;


    public static double normalDistribution(double x, double max, double translate){
        double firstTerm = 1/(max*Math.sqrt(2*Math.PI));
        double secondTerm = Math.exp(-Math.pow((x-translate),2)/(2*Math.pow(max,2)));

        return firstTerm * secondTerm;
    }

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
    public static boolean nearZero(double number, double error){
        if(number < error && number > -error){
            return true;
        }
        else{
            return false;
        }
    }

}
