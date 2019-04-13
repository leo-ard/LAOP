package org.lrima.laop.network.concreteLearning;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.LearningAnotation;
import org.lrima.laop.network.concreteNetworks.DL4JNN;
import org.lrima.laop.physic.PhysicEngine;
import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.simulation.Environnement;
import org.lrima.laop.simulation.RealTimeSimulation;
import org.lrima.laop.simulation.sensors.CarSensor;

import java.util.ArrayList;

@LearningAnotation(simulation= RealTimeSimulation.class)
public class DL4JLearning implements LearningAlgorithm<DL4JNN> {

    @Override
    public ArrayList<DL4JNN> learn(ArrayList<DL4JNN> allCars) {
        return allCars;
    }

    @Override
    public void cycle(Environnement environnement) {
        environnement.parallelEvaluation((cars) ->{
            cars.forEach(car -> ((DL4JNN)car).disableHumanControl());
        });
    }


    public void init(ArrayList<AbstractCar> cars) {
        cars.forEach(car -> car.addSensor(CarSensor.VELOCITY_SENSOR(car)));


    }
}
