package org.lrima.laop.network.concreteNetworks;

import org.lrima.laop.network.genetics.GeneticNeuralNetwork;

public class NEAT implements GeneticNeuralNetwork {
    double direction = 0.5;

    @Override
    public GeneticNeuralNetwork crossOver(GeneticNeuralNetwork otherGeneticNeuralNetwork) {
        direction = -direction;

        return this;
    }

    @Override
    public double[] control(double... captorValues) {
        return new double[]{1, 0, direction};
    }
}
