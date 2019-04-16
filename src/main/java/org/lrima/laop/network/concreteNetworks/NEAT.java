package org.lrima.laop.network.concreteNetworks;

import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.network.genetics.GeneticNeuralNetwork;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.settings.LockedSetting;

/**
 * An implementation of the NEAT algorithm. See paper here : http://nn.cs.utexas.edu/downloads/papers/stanley.ec02.pdf
 *
 * @author Léonard
 */
public class NEAT implements GeneticNeuralNetwork {
    double direction = 0.5;

    @Override
    public GeneticNeuralNetwork crossOver(GeneticNeuralNetwork otherGeneticNeuralNetwork) {
        direction = -direction;

        return this;
    }


    @Override
    public void setFitness(double fitness) {

    }

    @Override
    public double getFitness() {
        return 0;
    }

    @Override
    public CarControls control(double... sensorValues) {
        return new CarControls(1, 0, 0);
    }

    @Override
    public void init(LockedSetting lockedSetting) {

    }

    @Override
    public <T extends CarController> T copy() {
        return null;
    }
}
