package org.lrima.laop.utils;


import java.util.ArrayList;

public class NetworkUtils {

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

    public static double[][][] remap(int[] topology, double[] weights) {
        double[][][] remaped = new double[topology.length-1][][];
        int iterator = 0;

        for (int i = 0; i < remaped.length; i++) {
            int nbNeurons = topology[i];
            int nbNeuronsNext = topology[i+1];

            double[][] submap = new double[nbNeuronsNext][nbNeurons];

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
