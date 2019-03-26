package org.lrima.laop.network.genetics;

import org.lrima.laop.network.carcontrollers.CarController;

public interface GeneticNeuralNetwork extends CarController {
    GeneticNeuralNetwork crossOver(GeneticNeuralNetwork otherGeneticNeuralNetwork);
}
