package org.lrima.laop.network.concreteLearning;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.LearningAnotation;
import org.lrima.laop.network.concreteNetworks.DL4JNN;
import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.simulation.RealTimeSimulation;

import java.util.ArrayList;

@LearningAnotation(simulation= RealTimeSimulation.class)
public class DL4JLearning implements LearningAlgorithm<DL4JNN> {

    @Override
    public ArrayList<DL4JNN> learn(ArrayList<DL4JNN> allCars) {
        return allCars;
    }

    public void init(ArrayList<AbstractCar> cars) {

    }
}
