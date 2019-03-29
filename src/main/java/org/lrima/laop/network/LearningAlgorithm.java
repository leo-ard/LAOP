package org.lrima.laop.network;

import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.simulation.GenerationBasedSimulation;

import java.lang.annotation.Inherited;
import java.util.ArrayList;


public interface LearningAlgorithm <T extends CarController>{
    ArrayList<T> learn(ArrayList<T> allCars);
}
