package org.lrima.laop.network.concreteNetworks;

import org.lrima.laop.network.genetics.GeneticNeuralNetwork;
import org.lrima.laop.physic.CarControls;
import org.lrima.laop.settings.LockedSetting;

public class NEAT implements GeneticNeuralNetwork {
    double direction = 0.5;

    @Override
    public GeneticNeuralNetwork crossOver(GeneticNeuralNetwork otherGeneticNeuralNetwork) {
        direction = -direction;

        return this;
    }

    @Override
    public void init(LockedSetting settings) {

    }

    @Override
    public CarControls control(double... sensorValues) {
    	CarControls controls = new CarControls();
    	controls.setAcceleration(sensorValues[2]);
    	controls.setBreak(1-sensorValues[2]);
    	controls.setRotation(sensorValues[4] - sensorValues[0]);

        return controls;
    }
}
