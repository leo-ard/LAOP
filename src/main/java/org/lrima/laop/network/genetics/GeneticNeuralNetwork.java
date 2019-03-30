package org.lrima.laop.network.genetics;

import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.settings.LockedSetting;

public interface GeneticNeuralNetwork extends CarController {
    GeneticNeuralNetwork crossOver(GeneticNeuralNetwork otherGeneticNeuralNetwork);
    void init(LockedSetting settings);
}
