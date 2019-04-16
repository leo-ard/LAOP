package org.lrima.laop.utils;


import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.network.genetics.GeneticNeuralNetwork;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.simulation.Environnement;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class NetworkUtils {

//    public static BiFunction<Environnement, AbstractCar, Double> RANGE_FITNESS = ((env, car) -> {
//        Point2D carPos = new Point2D.Double(car.getPosition().getX(), car.getPosition().getY());
//        return env.getMap().distanceFromStart(carPos);
//    });

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

    public static <T> ArrayList<CarController> toCarControllerArray(ArrayList<T> geneticLearnings) {
        return geneticLearnings.stream().map(carController -> (CarController) carController).collect(Collectors.toCollection(ArrayList::new));
    }

    public static double average(ArrayList<? extends CarController> cars) {
        double totalFitness = 0;
        for(CarController car : cars) {
            totalFitness += car.getFitness();
        }

        return totalFitness / cars.size();
    }
}
