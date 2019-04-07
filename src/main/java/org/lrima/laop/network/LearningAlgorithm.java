package org.lrima.laop.network;

import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.physic.abstractObjects.AbstractCar;
import org.lrima.laop.simulation.GenerationBasedSimulation;

import java.lang.annotation.Inherited;
import java.util.ArrayList;

/**
 * Interface to make a learning algorithm
 *
 * @param <T> the type of network accepted by this learning algorithm. See {@link org.lrima.laop.network.concreteLearning.GeneticLearning} for an exemple.
 */
public interface LearningAlgorithm <T extends CarController>{
    /**
     * Makes the cars learn. Takes an array of cars and make it learn. All ways are accepted. The better version is then returned to get back to the simulation.
     *
     * @param allCars the freshly tested cars
     * @return a new set of car to test
     */
    ArrayList<T> learn(ArrayList<T> allCars);
    void init(ArrayList<AbstractCar> cars);

}
