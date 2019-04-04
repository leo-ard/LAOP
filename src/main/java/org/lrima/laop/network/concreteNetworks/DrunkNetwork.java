package org.lrima.laop.network.concreteNetworks;

import org.lrima.laop.network.genetics.GeneticNeuralNetwork;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.settings.LockedSetting;

public class DrunkNetwork implements GeneticNeuralNetwork {
    @Override
    public GeneticNeuralNetwork crossOver(GeneticNeuralNetwork otherGeneticNeuralNetwork) {
        return null;
    }

    @Override
    public void init(LockedSetting settings) {

    }

    @Override
    public CarControls control(double... sensorValues) {
        CarControls controls = new CarControls();
        controls.setAcceleration(sensorValues[2]);
        controls.setBreak(1-sensorValues[2]);
        controls.setRotation(sensorValues[0]-sensorValues[4]);
        return controls;
    }
}
