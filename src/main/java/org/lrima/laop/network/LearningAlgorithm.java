package org.lrima.laop.network;

import org.lrima.laop.network.carcontrollers.CarController;

import java.util.ArrayList;

public interface LearningAlgorithm <T extends CarController>{
    ArrayList<T> learn(ArrayList<T> allCars);
}
