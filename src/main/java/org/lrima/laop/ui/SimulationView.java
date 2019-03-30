package org.lrima.laop.ui;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.simulation.Simulation;

public abstract class SimulationView<T extends Simulation> {
    T simulation;

    public SimulationView(T simulation){
        this.simulation = simulation;
    }

    public abstract void setup(MainSimulationStage mainSimulationStage);

    public T getSimulation() {
        return simulation;
    }
}
