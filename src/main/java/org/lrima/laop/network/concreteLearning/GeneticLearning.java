package org.lrima.laop.network.concreteLearning;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.genetics.GeneticNeuralNetwork;

import java.util.ArrayList;

public class GeneticLearning implements LearningAlgorithm<GeneticNeuralNetwork> {
    @Override
    public ArrayList<GeneticNeuralNetwork> learn(ArrayList<GeneticNeuralNetwork> allCars) {

        allCars.forEach(car -> car.crossOver(car));

        //Kill 50
        //Other suff like crossOver and stuff

        return allCars;
    }
}
