package org.lrima.laop.network.genetics;

import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.settings.LockedSetting;

/**
 * An interface for all the networks that will be using the GeneticLearning as learner
 *
 * @author Léonard
 */
public interface GeneticNeuralNetwork extends CarController {
    /**
     * Takes as input one other neural network of this type and returns a version that is similar to this network and the other network.
     *
     * @param otherGeneticNeuralNetwork an other neural network of this type
     * @return A version that resemble the two networks.
     */
    GeneticNeuralNetwork crossOver(GeneticNeuralNetwork otherGeneticNeuralNetwork);
    void init(LockedSetting settings);
}
