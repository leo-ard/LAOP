package org.lrima.laop.simulation;

import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.simulation.map.AbstractMap;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Environnement {
    <T extends CarController> ArrayList<T> evaluate(ArrayList<T> cars);
    void parallelEvaluation(Consumer<ArrayList<? extends CarController>> cars);
    boolean isFinished();

    void initialise(SimulationEngine simulationEngine);

    AbstractMap getMap();
}
