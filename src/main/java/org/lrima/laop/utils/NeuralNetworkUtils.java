package org.lrima.laop.utils;


import java.util.ArrayList;

/**
 *
 * @author Léonard
 */
public class NeuralNetworkUtils {

    /**
     * Flatten an array to a one dimentionnal one
     *
     * @param array the array
     * @return a flattened array
     */
    public static double[] flatArray(double[][][] array) {
        ArrayList<Double> all = new ArrayList<>();
        for (double[][] doubles : array) {
            for (double[] aDouble : doubles) {
                for (double v : aDouble) {
                    all.add(v);
                }
            }
        }

        return all.stream().mapToDouble(Double.class::cast).toArray();
    }

    /**
     * Remap a flattened array by giving the topology of the array
     *
     * @param topology the topology of the array
     * @param weights the weights
     * @return the remapped array
     */
    public static double[][][] remap(int[] topology, double[] weights) {
        double[][][] remaped = new double[topology.length-1][][];
        int iterator = 0;

        for (int i = 0; i < remaped.length; i++) {
            int nbNeurons = topology[i];
            int nbNeuronsNext = topology[i+1];

            double[][] submap = new double[nbNeuronsNext][nbNeurons + 1];

            for (int j = 0; j < submap.length; j++) {
                for (int k = 0; k < submap[j].length; k++) {
                    submap[j][k] = weights[iterator++];
                }
            }

            remaped[i] = submap;
        }

        return remaped;
    }
}
