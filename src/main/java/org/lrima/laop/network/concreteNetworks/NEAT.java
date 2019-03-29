package org.lrima.laop.network.concreteNetworks;

import org.lrima.laop.network.genetics.GeneticNeuralNetwork;
import org.lrima.laop.physic.CarControls;

public class NEAT implements GeneticNeuralNetwork {
    double direction = 0.5;

    @Override
    public GeneticNeuralNetwork crossOver(GeneticNeuralNetwork otherGeneticNeuralNetwork) {
        direction = -direction;

        return this;
    }

    @Override
    public CarControls control(double... sensorValues) {
    	CarControls controls = new CarControls();
    	controls.setAcceleration(1-sensorValues[2]);
    	controls.setBreak(sensorValues[2]);
    	controls.setRotation(sensorValues[4] - sensorValues[0]);
    	
        return controls;
    }
}
